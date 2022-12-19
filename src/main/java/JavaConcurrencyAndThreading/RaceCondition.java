package JavaConcurrencyAndThreading;

import java.util.Random;

public class RaceCondition {
    private int randInt;
    private Random random = new Random(System.currentTimeMillis());

    private void printer() {
        int i = (int) 1e6;
        while(i != 0) {
            synchronized (this) {
                if(randInt%5 == 0) {
                    if(randInt%5 != 0) {
                        System.out.println(randInt);
                    }
                }
            }
            i--;
        }
    }

    private void modifier() {
        int i = (int) 1e6;
        while(i != 0) {
            synchronized (this) {
                randInt = random.nextInt(1000);
                i--;
            }
        }
    }

    public static void runTest() throws InterruptedException {
        final RaceCondition rc = new RaceCondition();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                rc.printer();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                rc.modifier();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
