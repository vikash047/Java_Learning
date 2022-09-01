package GrokingJavaConcurrency.UnixBathRoomProblem;

import java.util.concurrent.Semaphore;

public class UnixBathRoom {
    private final String men = "MEN";
    private final String wm = "WOMEN";
    private final String empty = "Empty";
    private String usedBy = empty;
    private Semaphore maxUsed = new Semaphore(3);
    private int empIn = 0;

    private void useBathRoom(String name) throws InterruptedException {
        System.out.println("\n" + name + " using bathroom. current employee in th bathroom = " + empIn);
        Thread.sleep(1000);
        System.out.println("\n" + name + " dong using the bathroom ");
    }

    public void menUseBathRoom(String name) throws InterruptedException {
        synchronized (this) {
            while (usedBy.equals(wm)) {
                this.wait();
            }
            maxUsed.acquire();
            empIn++;
            usedBy = men;
        }
        useBathRoom(name);
        maxUsed.release();
        synchronized (this) {
            empIn--;
            if(empIn == 0) usedBy = empty;
            this.notifyAll();
        }
    }

    public void womenUseBathRoom(String name) throws InterruptedException {
        synchronized (this) {
            while (usedBy.equals(men)) {
                this.wait();
            }
            maxUsed.acquire();
            empIn++;
            usedBy = wm;
        }
        useBathRoom(name);
        maxUsed.release();
        synchronized (this) {
            empIn--;
            if(empIn == 0) usedBy = empty;
            this.notifyAll();
        }
    }
}
