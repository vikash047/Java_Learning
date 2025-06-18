package JMT.MultiThreadinForSE.Stack;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Demostration {
    public static void main( String args[] ) throws Exception {

        CASBasedStack stackOfInts = new CASBasedStack();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        int numThreads = 20;
        CyclicBarrier barrier = new CyclicBarrier(numThreads);

        Integer testValue = new Integer(51);
        AtomicInteger totalCount = new AtomicInteger(0);

        try {
            for (int i = 0; i < numThreads; i++) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            stackOfInts.push(totalCount.incrementAndGet());
                            //totalCount.incrementAndGet();
                        }

                        try {
                            barrier.await();
                            //System.out.println("total value is " + totalCount.get());
                        } catch (InterruptedException | BrokenBarrierException ex) {
                            System.out.println("ignoring exception");
                            //ignore both exceptions
                        }
                        for (int i = 0; i < 10; i++) {
                            System.out.println(stackOfInts.pop());
                            //System.out.println(" size " + totalCount.decrementAndGet());
                        }
                    }
                });
            }
        } finally {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);
        }
    }
}
