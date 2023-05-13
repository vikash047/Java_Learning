package JavaConcurrencyBaeldung.SenderReceiverPattern.Mutex;

import java.util.List;
import java.util.concurrent.*;

public class TerminateAllThreadBeforeShutdown {
    public void awaitTerminationAfterShutdown(ExecutorService pool) {
        pool.shutdown();
        try {
            if(pool.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void usingCountDownLatch() throws InterruptedException {
        // if you have N thread so create count with N and pass countDownlatch instance in the all the thread and decrement
        //  it when work if finish and await on it. make sure all thread completes and kill the application.
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executorService  = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3));
        executorService.submit(() -> {
           try {
               latch.countDown();
           } catch (Exception ex) {

           }
        });
        latch.await();
    }

    public void invokeAllusingFuture(List<Callable<Integer>> callables){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try {
            List<Future<Integer>> lst = executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //await termination after shutdown process code refer to termination code at line 8;
    }

    public void executorCompletionService(List<Callable<Integer>> callables) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        BlockingQueue<Future<Integer>> pq = new LinkedBlockingDeque<>();
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService, pq);
        for(Callable<Integer> c : callables) executorCompletionService.submit(c);
        Future<Integer> res = executorCompletionService.take();
        // await termination on the executor service using line number 8 code.
    }


}
