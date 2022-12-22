package Interview.IO.TaskSchedular;

import java.time.Duration;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

public class TaskManager implements AutoCloseable {
    private final int noOfWorker;
    private final TaskExecutor taskExecutor;

    private final ConcurrentSkipListMap<Long, List<Task>> schedulePriorityQueue;
    private final ConcurrentSkipListMap<Long, List<Map.Entry<Duration, Task>>> fixedIntervalPriorityQueue;

    private final LinkedBlockingDeque<Map.Entry<Duration, Task>> immediateQueue;

    private List<CompletionStage<String>> tasks;

    private volatile Boolean cancel;
    private volatile Boolean stopTaskScheduling;

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    public TaskManager(int noOfWorker) {
        this.noOfWorker = noOfWorker;
        this.taskExecutor = new TaskExecutor(this.noOfWorker);
        schedulePriorityQueue = new ConcurrentSkipListMap<>();
        fixedIntervalPriorityQueue = new ConcurrentSkipListMap<>();
        immediateQueue = new LinkedBlockingDeque<>();
        cancel = false;
        stopTaskScheduling = false;
        this.tasks = new ArrayList<>();
    }

    public void schedule(Task task, long epoch, TimeUnit timeUnit) throws IllegalAccessException {
        if(stopTaskScheduling) {
            throw new IllegalAccessException();
        }
        schedulePriorityQueue.putIfAbsent(timeUnit.toMillis(epoch), new CopyOnWriteArrayList<>()).add(task);
    }

    public void scheduleAtFixedInterval(Task task, long epochInSeconds) throws IllegalAccessException {
        if(stopTaskScheduling) {
            throw new IllegalAccessException();
        }
        if(epochInSeconds <= 0) {
            throw new IllegalArgumentException();
        }
        immediateQueue.add(new AbstractMap.SimpleEntry<>(Duration.ofSeconds(epochInSeconds), task));
    }

    public void start() {
        tasks.add(CompletableFuture.completedFuture(this.cancel).thenApplyAsync(this::pollImmediateQueue, executorService).thenCompose(Function.identity()));
        tasks.add(CompletableFuture.completedFuture(this.cancel).thenApplyAsync(this::pollScheduledTask, executorService).thenCompose(Function.identity()));
        tasks.add(CompletableFuture.completedFuture(this.cancel).thenApplyAsync(this::pollScheduledFixedIntervalTask, executorService).thenCompose(Function.identity()));
    }

    private CompletionStage<String> pollScheduledTask(Boolean cancellation) {
        while(!this.cancel) {
            Map.Entry<Long, List<Task>> entry = schedulePriorityQueue.firstEntry();
            if(entry == null) continue;
            System.out.println("task time is " + entry.getKey() + " current time is " + System.currentTimeMillis());
            if(entry.getKey() <= System.currentTimeMillis()) {
                schedulePriorityQueue.pollFirstEntry();
                for(var t : entry.getValue())
                    taskExecutor.execute(t);
            }
        }
        return CompletableFuture.completedFuture("Stopped the Poll Scheduled Task");
    }

    private CompletionStage<String> pollScheduledFixedIntervalTask(Boolean cancellation) {
        while (!this.cancel) {
            Map.Entry<Long, List<Map.Entry<Duration, Task>>> entry = fixedIntervalPriorityQueue.firstEntry();
            if(entry == null) continue;
            if(entry.getKey() <= System.currentTimeMillis() && !stopTaskScheduling) {
                fixedIntervalPriorityQueue.pollFirstEntry();
                for(var t : entry.getValue()) {
                    taskExecutor.execute(t.getValue())
                            .whenComplete((x, throwable) -> {
                                    fixedIntervalPriorityQueue.putIfAbsent(System.currentTimeMillis() + t.getKey().toMillis(), new CopyOnWriteArrayList<>())
                                        .add(t);
                            });
                }

            }
        }
        return CompletableFuture.completedFuture("Stopped the poll fixed interval queue");
    }

    private CompletionStage<String> pollImmediateQueue(Boolean cancellation) {
        while (!this.cancel) {
            Map.Entry<Duration, Task> entry = immediateQueue.poll();
            if(entry == null) continue;
            taskExecutor.execute(entry.getValue())
                    .whenComplete((x, t) -> {
                        if(t == null) {
                            fixedIntervalPriorityQueue.putIfAbsent((System.currentTimeMillis() + entry.getKey().toMillis()), new CopyOnWriteArrayList<>())
                                    .add(entry);
                        }
                    });
        }
        return CompletableFuture.completedFuture("Stopped the poll immediate queue");
    }


    @Override
    public void close() throws Exception {
        this.stopTaskScheduling = true;
        this.cancel = true;
        while (!schedulePriorityQueue.isEmpty() || !immediateQueue.isEmpty() || !fixedIntervalPriorityQueue.isEmpty());
        for(var task : tasks) {
            task.thenAccept(x -> System.out.println(x.toString())).toCompletableFuture().get();
        }
    }
}

