package JavaConcurrencyBaeldung.SenderReceiverPattern.Mutex;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class CyclicVsCountDownLatch {
    public static final ThreadLocal<Integer> local = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue(){
            return new Integer(5);
        }
    };
    // cyclic barrier reset to original value once reach at zero
    // countDownLatch do not reset to original value once at zero.
    public void countDownTest() {
        CountDownLatch countDownLatch = new CountDownLatch(7);
        ExecutorService ex = Executors.newFixedThreadPool(20);
        for(int i = 0; i < 20; i++) {
            ex.submit(() -> {
                long preV = countDownLatch.getCount();
                countDownLatch.countDown();
                if(countDownLatch.getCount() != preV) {
                    System.out.println("count updated");
                }
            });
        }
    }

    public void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);
        ExecutorService ex = Executors.newFixedThreadPool(20);
        for(int i = 0; i < 20; i++) {
            ex.submit(() -> {
                try {
                    if(cyclicBarrier.getNumberWaiting() <= 0) {
                        System.out.println("Waiting for reset");
                    }
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // to intruppet a task after some time
    public class Timeout extends TimerTask {
        Thread t;
        Timer time;

        public Timeout(Thread t, Timer time) {
            this.t = t;
            this.time = time;
        }

        @Override
        public void run() {
            if(t != null && t.isAlive()) {
                t.interrupt();
                time.cancel();
            }
        }
    }

    public void killTaskAfterSomeTime() {
        Thread t = new Thread();
        t.start();
        Timer tm = new Timer();
        Timeout ts = new Timeout(t, tm);
        tm.schedule(ts, 3000);
    }
}
