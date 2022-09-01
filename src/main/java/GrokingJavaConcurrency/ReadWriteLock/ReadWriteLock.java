package GrokingJavaConcurrency.ReadWriteLock;

public class ReadWriteLock {
    private int reader = 0;
    private Boolean isWriter = false;
    public synchronized void acquireReadLock() throws InterruptedException {
        if(isWriter) {
            wait();
        }
        reader++;
    }
    public synchronized void releaseReadLock() {
        reader--;
        notify();
    }
    public synchronized void acquireWriteLock() throws InterruptedException {
        while (isWriter || reader != 0) {
            wait();
        }
        isWriter = true;
    }
    public synchronized void releaseWriteLock() {
        isWriter = false;
        notify();
    }
}
