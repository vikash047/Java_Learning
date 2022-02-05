package JavaConcurrencyAndThreading;

import java.util.concurrent.Semaphore;

public class UnisexBathroom {
    static String WOMEN  = "women";
    static String MEN  = "men";
    static String NONE  = "none";
    private String inUseBy = NONE;
    private int currentUsing = 0;
    // limiting the access to the bathroom 3
    private Semaphore sm = new Semaphore(3);
    void useBathroom(String name) throws InterruptedException {
        System.out.println(name + " using bathroom ");
        Thread.sleep(1000);
        System.out.println(name + " done using bathroom ");
    }

    void maleUsingBathroom(String name) throws InterruptedException {
        synchronized (this) {
            while (inUseBy.equals(WOMEN)) {
                this.wait();
            }
            sm.acquire();
            currentUsing++;
            inUseBy =  MEN;
        }
        useBathroom(name);
        sm.release();
        synchronized (this) {
            currentUsing--;
            if(currentUsing == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    void femaleUsingBathroom(String name) throws InterruptedException {
        synchronized (this) {
            while (inUseBy.equals(MEN)) {
                this.wait();
            }
            sm.acquire();
            currentUsing++;
            inUseBy =  WOMEN;
        }
        useBathroom(name);
        sm.release();
        synchronized (this) {
            currentUsing--;
            if(currentUsing == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    public static void Demo() {
        Thread f = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
