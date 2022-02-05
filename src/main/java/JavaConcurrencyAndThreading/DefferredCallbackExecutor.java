package JavaConcurrencyAndThreading;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class CallBack {
    long executeAt;
    String message;
    public CallBack(long executeAt, String message) {
        this.executeAt = System.currentTimeMillis() + executeAt*100l;
        this.message = message;
    }
}


public class DefferredCallbackExecutor {
    private static Random random = new Random(System.currentTimeMillis());

    private PriorityQueue<CallBack> pq = new PriorityQueue<>((o1, o2) -> (int) (o1.executeAt - o2.executeAt));
    private ReentrantLock lock  = new ReentrantLock();
    private Condition newCalllBackArried  = lock.newCondition();

    private long sleepDuration() {
        long current = System.currentTimeMillis();
        return pq.peek().executeAt -  current;
    }

    public void start() throws InterruptedException {
        long sleepFor = 0;
        while (true) {
            lock.lock();
            while (pq.size() == 0) {
                newCalllBackArried.await();
            }

            while (pq.size() != 0) {
                sleepFor = sleepDuration();
                if(sleepFor <= 0) break;
                newCalllBackArried.await(sleepFor, TimeUnit.MILLISECONDS);
            }

            CallBack cb = pq.poll();
            System.out.println("Executed at " + System.currentTimeMillis() + " Message is " + cb.message
                    + " configure time " + cb.executeAt);
            lock.unlock();
        }
    }

    public void registerCallBack(CallBack cb) {
        lock.lock();
        pq.add(cb);
        newCalllBackArried.signal();
        lock.unlock();
    }

    public static void runTestTenCallBack() throws InterruptedException {
        Set<Thread> allThread = new HashSet<>();
        final DefferredCallbackExecutor defferredCallbackExecutor = new DefferredCallbackExecutor();
        Thread service = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    defferredCallbackExecutor.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });
        service.start();
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    CallBack cb = new CallBack(1, "Hello this is " + Thread.currentThread().getName());
                    defferredCallbackExecutor.registerCallBack(cb);
                }
            });
            t.setName("Thread " + (i + 1));
            t.start();
            allThread.add(t);
            Thread.sleep(1000);
        }
        for(Thread t : allThread) t.join();
    }
}
