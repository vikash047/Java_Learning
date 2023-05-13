package CodingQuestions;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CustomMap {
    private static final int NUM_SEGMENTS = 16;
    private final Segment[] segments = new Segment[NUM_SEGMENTS];

    public CustomMap() {
        for (int i = 0; i < NUM_SEGMENTS; i++) {
            segments[i] = new Segment();
        }
    }

    public void put(int key, int value, long ttl) {
        int segmentIndex = getSegmentIndex(key);
        segments[segmentIndex].put(key, value, ttl);
    }

    public int get(int key) {
        int segmentIndex = getSegmentIndex(key);
        return segments[segmentIndex].get(key);
    }

    public double calculateAvg() {
        int count = 0;
        int sum = 0;

        for (Segment segment : segments) {
            segment.readLock();
            try {
                count += segment.getTotal();
                sum += segment.getSum();
            } finally {
                segment.readUnlock();
            }
        }

        if (count == 0) {
            return 0;
        }

        return (double) sum / count;
    }

    private int getSegmentIndex(int key) {
        return Math.abs(key) % NUM_SEGMENTS;
    }

    private static class Segment {
        private final Map<Integer, Integer> map = new HashMap<>();
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final ReentrantLock expirationLock = new ReentrantLock();
        private final Condition expirationSignal = expirationLock.newCondition();
        private final Condition notEmpty = lock.writeLock().newCondition();
        private final PriorityQueue<ExpiryEntry> expiryQueue = new PriorityQueue<>();

        private int total;
        private int sum;

        private Thread thread;

        private Segment() {
            total = 0;
            sum = 0;
            expireTheEntries();
        }

        public void expireTheEntries() {
            thread = new Thread(() -> {
                expirationLock.lock();
                while (true) {
                    long now = System.currentTimeMillis();
                    while (!expiryQueue.isEmpty() && expiryQueue.peek().expiryTime <= now) {
                        var poll = expiryQueue.poll();
                        removeIfExpired(poll.key);
                    }
                    long time = !expiryQueue.isEmpty() ? expiryQueue.peek().expiryTime - now : 1000;
                    try {
                        expirationSignal.await(time, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }

        public void put(int key, int value, long ttl) {
            lock.writeLock().lock();
            try {
                map.put(key, value);
                total++;
                sum += value;
                addToExpiryQueue(key, ttl);
                notEmpty.signal();
            } finally {
                lock.writeLock().unlock();
            }
        }

        public int get(int key) {
            lock.readLock().lock();
            try {
                return map.getOrDefault(key, 0);
            } finally {
                lock.readLock().unlock();
            }
        }

        public Map<Integer, Integer> getMap() {
            lock.readLock().lock();
            try {
                return new HashMap<>(map);
            } finally {
                lock.readLock().unlock();
            }
        }

        public void removeIfExpired(int key) {
            lock.writeLock().lock();
            try {
                if (map.containsKey(key)) {
                    var v = map.remove(key);
                    if(v != null) {
                        total--;
                        sum -= v;
                    }
                }
                notEmpty.signal();
            } finally {
                lock.writeLock().unlock();
            }
        }

        private void addToExpiryQueue(int key, long ttl) {
            expirationLock.lock();
            try {
                expiryQueue.add(new ExpiryEntry(key, System.currentTimeMillis() + ttl));
                expirationSignal.signal();
            } finally {
                expirationLock.unlock();
            }
        }

        public void readLock() {
            lock.readLock().lock();
        }

        public void readUnlock() {
            lock.readLock().unlock();
        }

        public int getSum() {
            return sum;
        }
        public int getTotal() {
            return total;
        }
    }
}
