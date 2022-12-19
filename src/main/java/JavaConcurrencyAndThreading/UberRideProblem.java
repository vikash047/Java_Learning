package JavaConcurrencyAndThreading;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberRideProblem {
    private int republicans = 0;
    private int democrats = 0;
    private Semaphore dw = new Semaphore(0);
    private Semaphore rw = new Semaphore(0);
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
    private ReentrantLock lock = new ReentrantLock();
    void drive() {
        System.out.println("Uber ride on it's wayyyy with ride leader " + Thread.currentThread().getName());
        System.out.flush();
    }

    void seatDemocrats() throws InterruptedException, BrokenBarrierException {
        boolean rideLleader = false;
        lock.lock();
        democrats++;
        if(democrats == 4) {
            dw.release(3);
            democrats -= 4;
            rideLleader = true;
        } else if(democrats == 2 && republicans >= 2) {
            dw.release(1);
            rw.release(2);
            rideLleader = true;
            democrats -= 2;
            republicans -= 2;
        } else {
            lock.unlock();
            dw.acquire();
        }
        seated();
        cyclicBarrier.await();
        if(rideLleader == true) {
            drive();
            lock.unlock();
        }
    }

    void seatRepublicans() throws InterruptedException, BrokenBarrierException {
        boolean rideLleader = false;
        lock.lock();
        republicans++;
        if(republicans == 4) {
            rw.release(3);
            republicans -= 4;
            rideLleader = true;
        } else if(republicans == 2 && democrats >= 2) {
            rw.release(1);
            dw.release(2);
            rideLleader = true;
            democrats -= 2;
            republicans -= 2;
        } else {
            lock.unlock();
            rw.acquire();
        }
        seated();
        cyclicBarrier.await();
        if(rideLleader == true) {
            drive();
            lock.unlock();
        }
    }

    private void seated() {
        System.out.println(Thread.currentThread().getName() + " seated");
        System.out.flush();
    }

    public static void Demo() throws InterruptedException {
        final UberRideProblem uberRideProblem = new UberRideProblem();
        Set<Thread> threads = new HashSet<>();
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        uberRideProblem.seatDemocrats();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setName("Democrats " + (i + 1));
            threads.add(t);
            Thread.sleep(500);
        }

        for(int i = 0; i < 14; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        uberRideProblem.seatRepublicans();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setName("Republicans " + (i + 1));
            threads.add(t);
            Thread.sleep(100);
        }
        for(Thread t : threads) t.start();
        for(Thread t : threads) t.join();
    }
}
