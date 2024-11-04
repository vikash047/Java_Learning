package MultithreadingProblemsSolveGMSE.BlockingQueue;

public class CountingSemaphore {
    private int maxCnt;
    private int permits;

    public CountingSemaphore(int maxCnt, int permits) {
        this.maxCnt = maxCnt;
        this.permits = permits;
    }

    public void aquire() throws InterruptedException {
        synchronized (this) {
            while (this.permits <= 0) {
                wait();
            }
            this.permits--;
        }
    }

    public void release() throws InterruptedException {
        synchronized (this) {
            while (this.permits >= maxCnt) {
                wait();
            }
            this.permits++;
        }
    }
}
