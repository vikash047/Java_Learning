package JMT.MultiThreadinForSE.Queue;

public class CountingSemaphore {
    private int usedPermits;
    private int maxCount;

    public CountingSemaphore(int maxCount) {
        this.maxCount = maxCount;
    }
    public CountingSemaphore(int maxCount, int initialPermits) {
        this.maxCount = maxCount;
        this.usedPermits = maxCount - initialPermits;
    }

    public synchronized void aquire() throws InterruptedException {
        if(usedPermits == maxCount) {
            wait();
        }
        notify();
        usedPermits++;
    }

    public synchronized void release() throws InterruptedException {
        if(usedPermits == 0) {
            wait();
        }
        notify();
        usedPermits--;
    }
}
