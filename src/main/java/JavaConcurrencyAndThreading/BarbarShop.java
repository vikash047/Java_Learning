package JavaConcurrencyAndThreading;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarbarShop {
    private int totalChair;
    private ReentrantLock lock = new ReentrantLock();
    private int waitingCustomer;
    private int hariCutGiven;
    private Semaphore waitForCustomerToEnter = new Semaphore(0);
    private Semaphore waitForBarbaraToReady = new Semaphore(0);
    private Semaphore barbaraReadyToCutHair = new Semaphore(0);
    private Semaphore waitCustomerToleave = new Semaphore(0);

    public BarbarShop(int _totalChairs) {
        this.totalChair = _totalChairs;
        this.waitingCustomer = 0;
        this.hariCutGiven = 0;
    }
    void customerWalkIn() throws InterruptedException {
        lock.lock();
        if(waitingCustomer == totalChair) {
            System.out.println("Customer walks out, all chairs occupied.");
            lock.unlock();
            return;
        }
        waitingCustomer++;
        lock.unlock();
        waitForCustomerToEnter.release();
        waitForBarbaraToReady.acquire();
        barbaraReadyToCutHair.acquire();
        waitCustomerToleave.release();
        lock.lock();
        waitingCustomer--;
        lock.unlock();
    }

    void barbar() throws InterruptedException {
        while (true) {
            waitForCustomerToEnter.acquire();
            waitForBarbaraToReady.release();
            hariCutGiven++;
            System.out.println("Cutting hair to the customer  " + hariCutGiven);
            Thread.sleep(500);
            barbaraReadyToCutHair.release();
            waitCustomerToleave.acquire();
        }
    }

    public static void demo() throws InterruptedException {
        final BarbarShop barbarShop = new BarbarShop(3);
        Thread b = new Thread(() -> {
            try {
                barbarShop.barbar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        b.start();
        Set<Thread> set = new HashSet<>();
        for(int i = 0; i < 20; i++) {
            Thread t = new Thread(()->{
                try {
                    barbarShop.customerWalkIn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            set.add(t);
        }
        for(Thread t : set) t.start();
        for (Thread t : set) t.join();
        Thread.sleep(1000);
        set.clear();
        //Set<Thread> set = new HashSet<>();
        for(int i = 0; i < 20; i++) {
            Thread t = new Thread(()->{
                try {
                    barbarShop.customerWalkIn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            set.add(t);
        }
        for(Thread t : set) t.start();
        for (Thread t : set) t.join();
        b.join();
    }
}
