package JMT.Semaphore;

import java.util.concurrent.Semaphore;

public class Problem1 {
    /*
     Use the simaphore and two thread access the counter value and increment it.
     T1 -> cnt = cnt + 1;
     T2 -> cnt = cnt + 1;
     */

    public class Counter {
        private int cnt = 0;
        private Semaphore sm;

        private Object object;

        public Counter() {
            sm = new Semaphore(1);
            object = new Object();
        }

        public void increment() throws InterruptedException {
            //sm.acquire();
            synchronized (object) {
                cnt = cnt + 1;
                System.out.println("count in thread - " + Thread.currentThread().getName() + " value is " + cnt);
            }

        }
    }
    public static void main(String[] args) throws InterruptedException {
        var builder = Thread.ofVirtual().name("Counter-Thread", 0);
        var pr = new Problem1();
        var cnt = pr.new Counter();
        var t1 = builder.start(() -> {
            try {
                cnt.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var t2 = builder.start(() -> {
            try {
                cnt.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread.sleep(1000);
        t1.join();
        t2.join();

    }
}
