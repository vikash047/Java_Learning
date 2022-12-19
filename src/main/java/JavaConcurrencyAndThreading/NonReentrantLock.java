package JavaConcurrencyAndThreading;

public class NonReentrantLock {
    private boolean isLocked;

    public NonReentrantLock() {
        isLocked = false;
    }

    public synchronized void lock(int  i) throws InterruptedException {
        while (isLocked) {
            System.out.printf("Hello in while loop" + i);
            wait();
        }
        isLocked = true;
    }

    public synchronized  void unLock() {
        isLocked = false;
        notify();
    }
}
