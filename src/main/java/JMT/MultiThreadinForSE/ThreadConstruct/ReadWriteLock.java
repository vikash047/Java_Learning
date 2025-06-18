package JMT.MultiThreadinForSE.ThreadConstruct;

/*
Imagine you have an application where you have multiple readers and multiple writers.
You are asked to design a lock which lets multiple readers read at the same time, but only one writer write at a time.
 */
public class ReadWriteLock {
    private int reader = 0;
    private boolean writerIn = false;
    public synchronized void acquireRead() throws InterruptedException {
        while (writerIn) {
            wait();
        }
        reader++;
    }
    public synchronized void releaseRead(){
        reader--;
        notify();
    }
    public synchronized void acquireWrite() throws InterruptedException {
        while (writerIn || reader != 0) {
            wait();
        }
        writerIn = true;
    }
    public synchronized void releaseWrite() {
        writerIn = false;
        notify();
    }
}
