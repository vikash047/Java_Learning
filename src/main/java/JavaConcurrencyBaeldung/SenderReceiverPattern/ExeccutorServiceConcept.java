package JavaConcurrencyBaeldung.SenderReceiverPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExeccutorServiceConcept {
    class CustomFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r,"CustomFactory");
            return thread;
        }
    }
    class CustomRejectedHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.printf("total pool size " + executor.getActiveCount());
            System.out.println("total task " + executor.getTaskCount());
            System.out.println("Runnable rejected " + r.toString());
        }
    }
    public void Signature() throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 1,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1), new CustomFactory(), new CustomRejectedHandler());
        executorService.setRejectedExecutionHandler(new CustomRejectedHandler());
        Runnable runnable = () -> {
            try {
                System.out.println("Runnable run " + Thread.currentThread().getId());
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };
        Callable<String> callable = () -> {
            System.out.println("Callable run " + Thread.currentThread().getId());
            TimeUnit.MILLISECONDS.sleep(300);
            return "Task's execution done";
        };
        List<Callable<String>> str = new ArrayList<>();
        str.add(callable);
        str.add(callable);
        str.add(callable);
        executorService.execute(runnable);
        Future<String> fr = executorService.submit(callable);
        List<Future<String>> lstfr = executorService.invokeAll(str);
        //List<Runnable> notCompleted = executorService.shutdownNow();
        executorService.shutdown();
        try{
            if(executorService.awaitTermination(1, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void ScheduledService() throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> {
            System.out.println("Callable run " + Thread.currentThread().getId());
            TimeUnit.MILLISECONDS.sleep(300);
            return "Task's execution done";
        };

        Runnable runnable = () -> {
            try {
                System.out.println("Runnable run " + Thread.currentThread().getId());
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Future<String> fr = scheduledExecutorService.schedule(callable, 1, TimeUnit.SECONDS);
        System.out.println(fr.get());
        Future<String> intervalFr  = (Future<String>) scheduledExecutorService.scheduleAtFixedRate(runnable, 100, 100,
                TimeUnit.MILLISECONDS);
    }
}
