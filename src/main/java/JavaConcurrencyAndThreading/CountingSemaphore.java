package JavaConcurrencyAndThreading;

public class CountingSemaphore {
    private int usedPermits = 0;
    private int maxCount;
    public CountingSemaphore(int count, int usedPermits) {
        this.maxCount = count;
        this.usedPermits = usedPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (usedPermits == maxCount) wait();
        usedPermits++;
        notify();

    }

    public synchronized void release() throws InterruptedException {
        while (usedPermits == 0) wait();
        usedPermits--;
        notify();
    }
}
