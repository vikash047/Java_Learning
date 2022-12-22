package Interview.IO.TaskSchedular;

import java.util.concurrent.*;

public class TaskExecutor {
    private final ExecutorService executor;
    private final int noOfWorkerThread;
    private final Semaphore semaphore;
    public TaskExecutor(int noOfWorkerThread) {
        ThreadFactory factory = Thread.ofVirtual().name("Executor Thread").factory();
        executor = Executors.newThreadPerTaskExecutor(factory);
        this.noOfWorkerThread = noOfWorkerThread;
        semaphore = new Semaphore(noOfWorkerThread);
    }

    public CompletionStage<Void> execute(Task task) {
        try {
            semaphore.acquire();
            final CompletableFuture<Void> fn = CompletableFuture.runAsync(() -> task.apply(), executor);
            return fn;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }
}
