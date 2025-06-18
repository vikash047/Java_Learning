package JMT.MultiThreadingConceptsForSE.JavaConcurrencyReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorFramework {
    public static void main(String[] args) throws InterruptedException {
        DumExecutor executor = new DumExecutor();
        MyTask task = new MyTask();
        executor.execute(task);
        /*MyExecutor executor1 = new MyExecutor();
        executor1.example();
        MyScheduledExecutor myScheduledExecutor = new MyScheduledExecutor();
        myScheduledExecutor.example();*/
        MyThreadPoolExecutor executor2 = new MyThreadPoolExecutor();
        executor2.example();
    }

    static class DumExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
            Thread t = new Thread(command);
            t.start();
        }
    }
    static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println("run the my task");
        }
    }

    static class MyExecutor {
        private final ExecutorService service = Executors.newFixedThreadPool(2);
        public void example() {
            service.submit(() -> System.out.println("runnable in executor service"));
            service.submit(() -> "Return from callable in the executor service");
            Callable<String> call = () -> "Return for the list callable in the exector service";
            List<Callable<String>> lst = new ArrayList<>();
            lst.add(call);
            lst.add(call);
            lst.add(call);
            try {
                List<Future<String>> result =  service.invokeAll(lst);
                service.shutdown();
                if(service.awaitTermination(2, TimeUnit.SECONDS)) {
                    service.shutdownNow();
                }
                if(service.isShutdown()) {
                    result.stream().forEach(x -> {
                        if(x.isDone()) {
                            try {
                                System.out.println(x.get());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    static class MyScheduledExecutor {
        private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        public void example() throws InterruptedException {
            Callable<String> call = () -> "Return callable from scheduled service";
            Runnable run = () -> System.out.println("Run in the scheduled runnable");
            List<Callable<String>> callables = new ArrayList<>();
            callables.add(call);
            callables.add(call);
            callables.add(call);
            //var result =  executorService. (callables, 10000, TimeUnit.MILLISECONDS);

            var res = executorService.schedule(call, 10, TimeUnit.MILLISECONDS);
            try {
                System.out.println(res.get());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            executorService.scheduleAtFixedRate(run, 20, 100, TimeUnit.MILLISECONDS);
            Thread.sleep(200);
            executorService.shutdown();
            if(executorService.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
            /*result.stream().forEach(x -> {
                if(x.isDone()) {
                    try {
                        System.out.println(x.get());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            });*/
        }
    }
    static class MyThreadPoolExecutor {
        private final ThreadPoolExecutor executor;
        class CustomThreadFactory implements ThreadFactory {

            @Override
            public Thread newThread(Runnable r) {
                Thread t = Thread.ofVirtual().factory().newThread(r);
                t.setDaemon(true);
                t.setName("Custom Thread Pool");
                return t;
            }
        }
        class RejectedHandler implements RejectedExecutionHandler {

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(" current active pool " + executor.getActiveCount());
                System.out.println(" total task is " + executor.getTaskCount());
                System.out.println(" rejected task is " + r.toString());
            }
        }
        public MyThreadPoolExecutor() {
            executor = new ThreadPoolExecutor(2, 2, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(2),
                    new CustomThreadFactory(), new RejectedHandler());
            executor.prestartAllCoreThreads();
        }

        public void example() throws InterruptedException {
            Callable<String> call = () -> "Return current thread " + Thread.currentThread().getName() + " id " + Thread.currentThread().threadId();
            Runnable run = () -> System.out.println("current thread name is " + Thread.currentThread().getName() + " id " + Thread.currentThread().threadId());
            executor.execute(run);
            Future<String> res = executor.submit(call);
            if(res.isDone()) {
                try {
                    System.out.println(res.get());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            List<Callable<String>> callables = new ArrayList<>();
            callables.add(call);
            callables.add(call);
            callables.add(call);
            var allres = executor.invokeAll(callables);
            executor.shutdown();
            if(executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
            allres.stream().forEach(x -> {
                try {
                    if(x.isDone())
                        System.out.println(x.get());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
