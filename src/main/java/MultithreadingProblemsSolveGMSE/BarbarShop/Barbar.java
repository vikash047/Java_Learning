package MultithreadingProblemsSolveGMSE.BarbarShop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Barbar {
    private int chairs;
    private int cnt;

    private int waitingCnt;
    private ReentrantLock lock;
    private Semaphore customerWaiting;
    private Semaphore barbarReady;
    private Semaphore givenHairCut;
    private Semaphore cutomerLeft;
    public Barbar(int chairs) {
        this.chairs = chairs;
        this.lock = new ReentrantLock();
        this.cnt = 0;
        this.waitingCnt = 0;
        this.customerWaiting = new Semaphore(0);
        this.barbarReady = new Semaphore(0);
        this.givenHairCut = new Semaphore(0);
        this.cutomerLeft = new Semaphore(0);
    }

    public void customerEnter() throws InterruptedException {
        lock.lock();
        if(waitingCnt == chairs) {
            System.out.println("No chair available free");
            lock.unlock();
            return;
        }
        waitingCnt++;
        lock.unlock();
        customerWaiting.release();
        barbarReady.acquire();
        givenHairCut.acquire();
        cutomerLeft.release();
        lock.lock();
        waitingCnt--;
        lock.unlock();
    }

    public void hairCut() throws InterruptedException {
        while (true) {
            customerWaiting.acquire();
            barbarReady.release();
            System.out.println("give the hair cut to the customer");
            cnt++;
            Thread.sleep(500);
            givenHairCut.release();
            cutomerLeft.acquire();
        }
    }
}
