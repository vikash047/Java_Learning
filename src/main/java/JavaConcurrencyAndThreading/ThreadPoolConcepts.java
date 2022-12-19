package JavaConcurrencyAndThreading;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class ThreadPoolConcepts {
    public static void main(String[] args) throws Exception {
     //  Executor executor = (Executor) Executors.newScheduledThreadPool(10); // scheduled thread pool execute the task after a dellay interval
//        Executor ex1 = (Executor) Executors.newCachedThreadPool(); // create thread pool when needed as well use the olld created threads.
//        Executor ex2 = (Executor) Executors.newFixedThreadPool(10); // fixed number of thread.
//        Executor ex3 = (Executor) Executors.newSingleThreadExecutor(); // only one thread get data from the queue and execute them.
//        Executor ex4 = (Executor) Executors.newWorkStealingPool(); // fork and join means execute the subtask and collet th all result.
        // Timer is single thread to excute submitted task.
        //if a tak never terminate then waiting task never get chance to execute.
        // if one task longer time then another task would get delayed and if another one task is periodical  then it runs for alll his turns.
        Timer timer = new Timer();
        TimerTask badTask = new TimerTask() {
            @Override
            public void run() {
                while (true);
            }
        };
        TimerTask goodTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(" Hello I am a well behaved task..");
            }
        };
       // timer.schedule(badTask, 100);
       // timer.schedule(goodTask, 500);
       // Thread.sleep(3000)
        System.out.println(" sum : " + findSum(200));
        threadPool.shutdown();
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return 5;
            }
        });
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future dupllicateFuture = threadPool.submit(futureTask);
        if(dupllicateFuture.isDone() != futureTask.isDone()) {
            System.out.println("Task is finished");
        }
        System.out.println(futureTask.get());
        threadPool.shutdown();
        // completion service combination of the blocking queue and executor service.
        // ExecutorCompletionService accept the executorService and blocking queue as argument.
        completionServiceExample();
        // CountDownLatch used for the blocked the resource or thread a specific number first get execute then proceed
        // further to run the program all those task await on the countDownLatch
        CountDownLatch countDownLatch  = new CountDownLatch(4);
        //countDownLatch.await();
        //countDownLatch.countDown();
        CyclicBarier();
    }
    static int findSum(final int n) throws ExecutionException, InterruptedException {
        Callable<Integer> result = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int res = 0;
                for(int i = 0; i <= n; i++) res += i;
                return res;
             }
        };
        Future<Integer> f = threadPool.submit(result);
        return f.get();
    }

    static void completionServiceExample() throws ExecutionException, InterruptedException {
        class TrivialTask implements Runnable {
            private int n;
            public TrivialTask(int _n) {
                this.n = _n;
            }
            @Override
            public void run() {
                try {
                    Thread.sleep(random.nextInt(101));
                    System.out.println(n*n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Integer> service = new ExecutorCompletionService<>(executorService);
        for(int i = 0; i < 10;  i++) {
            service.submit(new TrivialTask(i), new Integer(i));
        }
        int count = 10;
        while(count != 0)  {
            Future<Integer> f = service.poll();
            if(f != null) {
                System.out.println("Thread " + f.get() + " got done");
                count--;
            }
        }
        executorService.shutdown();
    }
    static void CyclicBarier() {
        class Task implements Runnable  {
            private CyclicBarrier cyclicBarrier;
            public Task(CyclicBarrier cyclicBarrier) {
                this.cyclicBarrier = cyclicBarrier;
            }
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is waiting on barrier");
                try {
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "has crossed the barrier");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("All parties have arrived at the barrier, lets continue execution.");
            }
        });
        Thread t1 = new Thread(new Task(cyclicBarrier), "Thread 1");
        Thread t2 = new Thread(new Task(cyclicBarrier), "Thread 2");
        Thread t3 = new Thread(new Task(cyclicBarrier), "Thread 3");
        //Thread t4 = new Thread(new Task(cyclicBarrier), "Thread 4");
        t1.start();
        t2.start();
        t3.start();
    }
    static Random random = new Random(System.currentTimeMillis());
    static ExecutorService threadPool = Executors.newFixedThreadPool(2);
}
