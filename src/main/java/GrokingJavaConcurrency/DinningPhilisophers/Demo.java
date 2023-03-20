package GrokingJavaConcurrency.DinningPhilisophers;

import java.util.HashSet;
import java.util.Set;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        final DiningPhilosophers dp = new DiningPhilosophers();
        Set<Thread> threads = new HashSet<>();
        for(int i = 0; i < 5; i++) {
            int finalI = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dp.lifeCycle(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setName("Thread_" + (i + 1));
            threads.add(t);
        }
        for(Thread t : threads) {
            t.start();
            Thread.sleep(500);
        }
        for(Thread t : threads) {
            t.join();
        }
    }
}
