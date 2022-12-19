package JavaConcurrencyAndThreading;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class DinningPhiloshper {
    private static Random random = new Random(System.currentTimeMillis());
    private Semaphore[] forks = new Semaphore[5];
    private Semaphore maxDinner = new Semaphore(4);
    public DinningPhiloshper() {
        forks[0] = new Semaphore(1);
        forks[1] = new Semaphore(1);
        forks[2] = new Semaphore(1);
        forks[3] = new Semaphore(1);
        forks[4] = new Semaphore(1);
    }

    public void lifeCycleofPh(int id) throws InterruptedException {
        while (true) {
            contemplate();
            eat(id);
        }
    }

    private void eat(int id) throws InterruptedException {
        /*if(id == 3) {
            leftHanded(id);
        } else {
            righHanded(id);
        }*/
        usingSemaphore(id);
    }

    private void contemplate() throws InterruptedException {
        Thread.sleep(random.nextInt(500));
    }

    private void leftHanded(int id) throws InterruptedException {
        forks[(id + 1)%5].acquire();
        forks[id].acquire();
        System.out.println("Philosopher " + id + " is eating");
        forks[(id + 1)%5].release();
        forks[id].release();
    }

    private void righHanded(int id) throws InterruptedException {
        forks[id].acquire();
        forks[(id + 1) % 5].acquire();
        System.out.println("Philosopher " + id + " is eating");
        forks[id].release();
        forks[(id + 1) % 5].release();
    }

    private void usingSemaphore(int id) throws InterruptedException {
        maxDinner.acquire();
        forks[id].acquire();
        forks[(id + 1) % 5].acquire();
        System.out.println("Philosopher " + id + " is eating");
        forks[id].release();
        forks[(id + 1) % 5].release();
        maxDinner.release();
    }

    static void start(DinningPhiloshper dp, int id) throws InterruptedException {
        dp.lifeCycleofPh(id);
    }

    public static void demo() throws InterruptedException {
        final DinningPhiloshper dp = new DinningPhiloshper();
        Thread p1 = new Thread(() -> {
            try {
                DinningPhiloshper.start(dp, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread p2 = new Thread(() -> {
            try {
                DinningPhiloshper.start(dp, 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread p3 = new Thread(() -> {
            try {
                DinningPhiloshper.start(dp, 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread p4 = new Thread(() -> {
            try {
                DinningPhiloshper.start(dp, 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread p5 = new Thread(() -> {
            try {
                DinningPhiloshper.start(dp, 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p1.join();
        p2.join();
        p3.join();
        p4.join();
        p5.join();
    }
}
