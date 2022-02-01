package JavaConcurrencyAndThreading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MissedSignaled {
    final ReentrantLock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    // fix use the  semaphore for the missed signal
    Semaphore sm = new Semaphore(1);

    Thread signaller =  new Thread(new Runnable() {
        @Override
        public void run() {
            //lock.lock();
            //condition.signal();
            sm.release();
            System.out.println(" sent signal");
            //lock.unlock();
            System.out.println("done");
        }
    });

    Thread waiter = new Thread(new Runnable() {
        @Override
        public void run() {
            //lock.lock();
            try {
                //condition.await();
                sm.acquire();
                System.out.println(" Received signal");
            } catch (InterruptedException ie) {}
        }
    });

    public static void runTest() throws InterruptedException {
        MissedSignaled ms = new MissedSignaled();
        ms.signaller.start();
        ms.signaller.join();
        ms.waiter.start();
        ms.waiter.join();
        System.out.println(" Program done");
    }
}
