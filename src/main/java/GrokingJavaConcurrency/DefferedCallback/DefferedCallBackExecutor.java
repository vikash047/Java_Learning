package GrokingJavaConcurrency.DefferedCallback;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DefferedCallBackExecutor {
    PriorityQueue<CallBack> pq = new PriorityQueue<>(new Comparator<CallBack>() {
        @Override
        public int compare(CallBack o1, CallBack o2) {
            return (int) (o1.executeAt - o2.executeAt);
        }
    });

    ReentrantLock lock = new ReentrantLock();
    Condition callBackArrived = lock.newCondition();

    private long findSleepDuration() {
        long current = System.currentTimeMillis();
        return pq.peek().executeAt - current;
    }

    public void start() throws InterruptedException {
        long sleepFor = 0;
        while(true) {
            lock.lock();
            while (pq.size() == 0) {
                callBackArrived.await();
            }

            while (pq.size() != 0) {
                sleepFor = findSleepDuration();
                if(sleepFor <= 0) {
                    break;
                }
                callBackArrived.await(sleepFor, TimeUnit.MILLISECONDS);
            }
            CallBack cb = pq.poll();
            System.out.println("Executed at " + System.currentTimeMillis()/1000 + " required at " + cb.executeAt/1000
            + " message : " + cb.message);
            lock.unlock();
        }
    }

    public void registerCallback(CallBack cb) {
        lock.lock();
        pq.offer(cb);
        callBackArrived.signal();
        lock.unlock();
    }
}
