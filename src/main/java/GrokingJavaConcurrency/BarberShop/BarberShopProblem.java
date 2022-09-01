package GrokingJavaConcurrency.BarberShop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopProblem {
    private final int chairs;
    private int waitingCustomers;
    private int hairCutGiven;
    private ReentrantLock lock = new ReentrantLock();
    private Semaphore waitForCustomerToEnter  = new Semaphore(0);
    private Semaphore waitForBarberToCutHair = new Semaphore(0);
    private Semaphore waitForCustomerToLeave = new Semaphore(0);
    private Semaphore waitForBarberToReady = new Semaphore(0);
    public BarberShopProblem(int chairs) {
        this.chairs = chairs;
        this.waitingCustomers = 0;
        this.hairCutGiven = 0;
    }

    public void customerWalksIn() throws InterruptedException {
        lock.lock();
        if(waitingCustomers == chairs) {
            System.out.println("Customer walks out, all chairs occupied.");
            lock.unlock();
            return;
        }
        waitingCustomers++;
        lock.unlock();
        // wait for the turn barber start working or turn
        waitForCustomerToEnter.release();
        waitForBarberToReady.acquire();
        waitForBarberToCutHair.acquire();
        waitForCustomerToLeave.release();
        lock.lock();
        waitingCustomers--;
        lock.unlock();
    }

    public void barber() throws InterruptedException {
        while (true) {
            waitForCustomerToEnter.acquire();
            waitForBarberToReady.release();
            hairCutGiven++;
            System.out.println("Barber cutting hair ... " + hairCutGiven);
            Thread.sleep(500);
            waitForBarberToCutHair.release();
            waitForCustomerToLeave.acquire();
        }
    }
}
