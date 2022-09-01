package GrokingJavaConcurrency.BlockingQueue;

public class CountingSemaphore {
    private int maxPremit;
    private int usedPremit;

    public CountingSemaphore(int maxPremit, int usedPremit) {
        this.maxPremit = maxPremit;
        this.usedPremit = usedPremit;
    }

    public synchronized void aquire() throws InterruptedException {
        while (maxPremit == usedPremit) {
            wait();
        }
        usedPremit++;
        //notifyAll();
        notify();
    }

    public synchronized void release() throws InterruptedException {
        while (usedPremit == 0) {
            notify();
            wait();
        }
        usedPremit--;
        //notifyAll();
        notify();
    }
}
