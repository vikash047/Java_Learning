package JavaConcurrencyAndThreading;

public class DeadLoack {
    private int counter = 0;
    private Object lc1 = new Object();
    private Object lc2 = new Object();

    Runnable increment  = () -> {
        try {
            for(int i = 0; i < 100; i++) {
                incrementCounter();
            }
        } catch (InterruptedException ie) {}
    };

    Runnable decrement = () -> {
        try {
            for(int i =  0; i < 100;  i++) {
                decrementCounter();
            }
        } catch (InterruptedException ie) {}
    };

    private void incrementCounter() throws InterruptedException {
        synchronized (lc1) {
            System.out.println("in Acquired lock 1");
            Thread.sleep(10000000);
            synchronized (lc2) {
                System.out.println("in Acquired lock 2");
                counter++;
            }
        }
    }

    private void decrementCounter() throws InterruptedException {
        synchronized (lc2) {
            System.out.println("in Acquired lock 2");
            Thread.sleep(1000);
            synchronized (lc1) {
                System.out.println("in Acquired lock 1");
                counter--;
            }
        }
    }

    public void runTest() throws InterruptedException {
        Thread t1 = new Thread(increment);
        Thread t2 = new Thread(decrement);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Done : " + counter);
    }
 }
