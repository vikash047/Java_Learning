package Interview.IO.TaskSchedular;

import java.util.concurrent.*;

public class TaskExecutor {

    public class ThreadFactory implements java.util.concurrent.ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            var t = Thread.ofVirtual().factory().newThread(r);
            t.setName("CustomThread - 1");
            return t;
        }
    }
    private final ExecutorService executor;
    private final int noOfWorkerThread;
    private final Semaphore semaphore;
    public TaskExecutor(int noOfWorkerThread) {
       // ThreadFactory factory = Thread.ofVirtual().name("Executor Thread").factory();
        executor = Executors.newThreadPerTaskExecutor(new ThreadFactory());
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
