package GrokingJavaConcurrency.UberRiderProblem;

import java.util.HashSet;
import java.util.Set;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        final UberSeatingProblem uberSeatingProblem = new UberSeatingProblem();
        Set<Thread> threads = new HashSet<>();
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        uberSeatingProblem.seatRep();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setName("Thread_" + (i + 1));
            threads.add(t);
            Thread.sleep(50);
        }

        for(int i = 0; i < 14; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        uberSeatingProblem.seatDemo();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setName("Thread_" + (i + 1));
            threads.add(t);
            Thread.sleep(50);
        }
        for(Thread t : threads) {
            t.start();
        }
        for(Thread t : threads) {
            t.join();
        }
    }
}
