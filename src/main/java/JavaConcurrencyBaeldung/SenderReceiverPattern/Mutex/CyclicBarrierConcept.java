package JavaConcurrencyBaeldung.SenderReceiverPattern.Mutex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierConcept {
    // cyclic barrier is synchronization construct used between threads  to reach at comman barrier.
    // used in fixed number of thread. all are waiting to reach at common point of execution.
    // cyclic bc it can be reused again after using.
    // await() call on it called tripping the barrier.
    // Cyclic barried constructor can accept the Runnable as well which would called at last thread calls the
    // await or trip.

    private int numberOfPartial;
    private int numberOfWorker;
    private List<List<Integer>> result = Collections.synchronizedList(new ArrayList<>());
    private Random random = new Random();
    private CyclicBarrier cyclicBarrier;
    class ThreadNumberCruncher implements  Runnable {

        @Override
        public void run() {
            List<Integer> local =  new ArrayList<>();
            for(int i = 0; i < numberOfPartial; i++) {
                Integer num = random.nextInt(10);
                System.out.println(Thread.currentThread().getName() + " number produced " + num);
                local.add(num);
            }
            result.add(local);
            try {
                System.out.println("Waiting for the other thread to  reach at common point"
                + Thread.currentThread().getName());
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    class Aggregator implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();

            System.out.println(
                    thisThreadName + ": Computing sum of " + numberOfWorker
                            + " workers, having " + numberOfPartial + " results each.");
            int sum = 0;

            for (List<Integer> threadResult : result) {
                System.out.print("Adding ");
                for (Integer partialResult : threadResult) {
                    System.out.print(partialResult+" ");
                    sum += partialResult;
                }
                System.out.println();
            }
            System.out.println(thisThreadName + ": Final result = " + sum);
        }
    }

    public void DemoTest(int worker, int partial) {
        this.numberOfPartial = partial;
        this.numberOfWorker = worker;
        cyclicBarrier = new CyclicBarrier(worker, new Aggregator());
        for(int i = 0; i < worker; i++) {
            Thread t = new Thread(new ThreadNumberCruncher());
            t.setName("Thread number " + i);
            t.start();
        }
    }



}
