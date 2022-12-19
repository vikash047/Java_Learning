package JavaConcurrencyAndThreading;

public class ReaderWritter {
    private int readerCount = 0;
    private boolean writeLock = false;

    public synchronized void acquireWriteLock() throws InterruptedException {
        while(writeLock || readerCount !=  0) {
            wait();
        }
        writeLock = true;
    }

    public synchronized void releaseWriteLLock() {
        writeLock = false;
        notify();
    }

    public synchronized void acquireReadLock() throws InterruptedException {
        while (writeLock) {
            wait();
        }
        readerCount++;
    }

    public synchronized void releaseReadLock() {
        readerCount--;
        notify();
    }

    public static void Demo() throws InterruptedException {
        final ReaderWritter readerWritter = new ReaderWritter();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Attempting to aquire write lock in t1");
                try {
                    readerWritter.acquireWriteLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Acquired the write lock");
                for (;;){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Attempting to acquire the read lock in t2");
                    readerWritter.acquireReadLock();
                    System.out.println("Acquired the read lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Acquiring the read lock t3");
                    readerWritter.acquireReadLock();
                    System.out.println("Acquired the read lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Acquiring the read lock in t4");
                    readerWritter.acquireReadLock();
                    System.out.println("Acquired the read lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i  < 4; i++) {
                    readerWritter.releaseReadLock();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        Thread.sleep(5000);
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

    }
}
