package GrokingJavaConcurrency.UberRiderProblem;

import GrokingJavaConcurrency.CyclicBarrier.CyclicBarrier;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberSeatingProblem {
    private int rep = 0;
    private int demo = 0;
    private CyclicBarrier barrier = new CyclicBarrier(4);
    private ReentrantLock lock = new ReentrantLock();
    private final Semaphore demWating = new Semaphore(0);
    private final Semaphore repWating = new Semaphore(0);

    public void seatDemo() throws InterruptedException {
        boolean rideLeader = false;
        lock.lock();
        demo++;
        if(demo == 4) {
            demWating.release(3);
            demo -= 4;
            rideLeader = true;
        } else if(demo == 2 && rep >= 2) {
            demWating.release(1);
            repWating.release(2);
            rideLeader = true;
            demo -= 2;
            rep -= 2;
        } else {
            lock.unlock();
            demWating.acquire();
        }
        seated();
        barrier.await();
        if(rideLeader) {
            drive();
            lock.unlock();
        }
    }

    public void seatRep() throws InterruptedException {
        boolean rideLeader = false;
        lock.lock();
        rep++;
        if(rep == 4) {
            repWating.release(3);
            rideLeader = true;
            rep -= 4;
        } else if(rep == 2 && demo >= 2) {
            repWating.release(1);
            demWating.release(2);
            rep -= 2;
            demo -= 2;
            rideLeader = true;
        } else {
            lock.unlock();
            repWating.acquire();
        }
        seated();
        barrier.await();
        if(rideLeader) {
            drive();
            lock.unlock();
        }
    }

    public void seated() {
        System.out.println(Thread.currentThread().getName() + " seated");
        System.out.flush();
    }

    public void drive() {
        System.out.println("Uber ride on its wayyy ... with rider " + Thread.currentThread().getName());
        System.out.flush();
    }

}
