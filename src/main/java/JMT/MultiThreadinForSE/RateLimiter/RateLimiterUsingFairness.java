package JMT.MultiThreadinForSE.RateLimiter;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimiterUsingFairness {
    public static class ConditionMetaData {
        Condition condition;
        Integer id;
        public ConditionMetaData(Condition condition, Integer id) {
            this.condition = condition;
            this.id = id;
        }
    }
    int maxTokens;
    int currentTokens;
    ReentrantLock lock = new ReentrantLock();
    Queue<Condition> waitingThread;
    Set<Integer> waitingIds;

    public RateLimiterUsingFairness(int maxTokens) {
        this.maxTokens = maxTokens;
        waitingThread = new LinkedList<>();
        waitingIds = new HashSet<>();
        var t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    addToken();
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
    private void addToken() {
        lock.lock();
        try {
            if(currentTokens < maxTokens) {
                currentTokens++;
            }
            if(!waitingThread.isEmpty()) {
                waitingThread.poll().signal();
            }
        } finally {
            lock.unlock();
        }
    }
    private void getToken() throws InterruptedException {
        lock.lock();
        try {
            if(currentTokens == 0) {
                while (currentTokens == 0) {
                    var condition = lock.newCondition();
                    waitingThread.offer(condition);
                    condition.await();
                }
            }
            currentTokens--;
        } finally {
            lock.unlock();
        }
    }
}
