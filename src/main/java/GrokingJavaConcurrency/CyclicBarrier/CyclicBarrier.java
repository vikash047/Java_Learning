package GrokingJavaConcurrency.CyclicBarrier;

public class CyclicBarrier {
    private int count = 0;
    private int released = 0;
    private final int totalThreads;

    public CyclicBarrier(int totalThreads) {
        this.totalThreads = totalThreads;
    }

    public synchronized void await() throws InterruptedException {
        count++;
        if(count == totalThreads) {
            notifyAll();
            released = totalThreads;
        } else {
            while(count < totalThreads) {
                wait();
            }
        }
        released--;
        if(released == 0) {
            count = 0;
        }
    }
}
