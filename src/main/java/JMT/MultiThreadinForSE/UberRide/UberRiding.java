package JMT.MultiThreadinForSE.UberRide;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberRiding {
    private int demo = 0;
    private int rep = 0;
    CyclicBarrier barrier = new CyclicBarrier(4);
    ReentrantLock lock = new ReentrantLock();
    private final Semaphore demoWaiting = new Semaphore(0);
    private final  Semaphore repWaiting = new Semaphore(0);

    public void seatDemo() throws InterruptedException, BrokenBarrierException {
        boolean leader = false;
        lock.lock();
        demo++;
        if(demo == 4) {
            demoWaiting.release(3);
            demo -= 4;
            leader = true;
        } else if(demo == 2 && rep >= 2) {
            demoWaiting.release(1);
            repWaiting.release(2);
            leader = true;
            demo -= 2;
            rep -= 2;
        } else {
            lock.unlock();
            demoWaiting.acquire();
        }
        seated();
        barrier.await();
        if(leader) {
            drive();
            lock.unlock();
        }
    }
    public void seatRep() throws InterruptedException, BrokenBarrierException {
        boolean leader = false;
        lock.lock();
        rep++;
        if(rep == 4) {
            repWaiting.release(3);
            rep -= 4;
            leader = true;
        } else if(rep == 2 && demo >= 2) {
            repWaiting.release(1);
            demoWaiting.release(2);
            leader = true;
            demo -= 2;
            rep -= 2;
        } else {
            lock.unlock();
            repWaiting.acquire();
        }
        seated();
        barrier.await();
        if(leader) {
            drive();
            lock.unlock();
        }
    }

    private void seated() {
        System.out.println(Thread.currentThread().getName() + " seated");
    }
    private void drive() {
        System.out.println("Uber Ride on it's way... with rider leader" + Thread.currentThread().getName());
    }
}
