package JMT.MultiThreadinForSE.ThreadConstruct;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
Design and implement a thread-safe class that allows registeration of callback methods
that are executed after a user specified time interval in seconds has elapsed.
 */
/*
TODO: if a class have synchronized method two then one thread already executing A method then another thread can not use the B method.
    but same thread can execute the B method.
 */
public class DeferredCallback {
    PriorityQueue<Callback> pq = new PriorityQueue<>(Comparator.comparing(callback -> callback.executeAt));
    ReentrantLock lock = new ReentrantLock();
    Condition callBackArrived  = lock.newCondition();
    public void start() throws InterruptedException {
        long sleepFor = 0;
        while (true) {
            lock.lock();
            try {
                while (pq.isEmpty()) {
                    callBackArrived.await();
                }
                while (!pq.isEmpty()) {
                    sleepFor = findSleepDuration();
                    if(sleepFor <= 0) {
                        break;
                    }
                    callBackArrived.await(sleepFor, TimeUnit.MICROSECONDS);
                }
                var cb = pq.poll();
                System.out.println("Executed at " + System.currentTimeMillis()/1000 + " required At " + cb.executeAt/1000 + " message: " + cb.message);
            } finally {
                lock.unlock();
            }

        }
    }

    private long findSleepDuration() {
        return System.currentTimeMillis() - pq.peek().executeAt;
    }

    public void registerCallback(Callback callback) {
        lock.lock();
        try {
            pq.add(callback);
            callBackArrived.signal();
        } finally {
            lock.unlock();
        }
    }
    public static class Callback {
        long executeAt;
        String message;
        public Callback(long executeAt, String message) {
            this.executeAt = System.currentTimeMillis() + executeAt*1000;
            this.message = message;
        }
    }
}
