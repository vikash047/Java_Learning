package JMT.MultiThreadinForSE.ThreadConstruct;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Molecule {
    private final ReentrantLock lock;
    private final Condition oxygenCondition;
    private final Condition hyderogen;
    private int oc, hc;
    private int mole;

    public Molecule() {
        lock = new ReentrantLock();
        oxygenCondition = lock.newCondition();
        hyderogen = lock.newCondition();
        oc = hc  = mole = 0;
    }

    public void hydrogenArrive() {
        lock.lock();
        try {
            while (hc == 2) {
                hyderogen.await();
            }
            hc++;
            if(hc == 2 && oc == 1) {
                mole++;
                System.out.println(" build molecule H2O " + mole);
                hc = 0;
                oc = 0;
                oxygenCondition.signal();
                hyderogen.signal();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
    public void oxygenArrive() {
        lock.lock();
        try {
            while (oc == 1) {
                oxygenCondition.await();
            }
            oc++;
            if(oc == 1 && hc == 2) {
                mole++;
                System.out.println("build molecule H2O " + mole);
                oc = 0;
                hc = 0;
                hyderogen.signal();
                oxygenCondition.signal();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Molecule molecule = new Molecule();
        Thread t1 = new Thread(() -> {
           for(int i = 0; i < 100; i++) {
               molecule.hydrogenArrive();
           }
        });
        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 100; i++) {
                molecule.hydrogenArrive();
            }
        });
        Thread t3 = new Thread(() -> {
            for(int i = 0; i < 100; i++) {
                molecule.oxygenArrive();
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }
}
