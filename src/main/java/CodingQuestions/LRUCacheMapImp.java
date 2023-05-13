package CodingQuestions;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCacheMapImp implements LRUCacheMap, AutoCloseable{
    class Record {
        Integer value;
        long expiryTime;

        public Record(Integer value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }
    private final Map<Integer, Record> map;
    private final ConcurrentSkipListMap<Long, ConcurrentSkipListSet<Integer>> expiry;
    private int total, sum;

    private final ReentrantLock lock;
    private final Condition lc;

    private final Thread t;
    public LRUCacheMapImp() {
        map = new HashMap<>();
        this.expiry = new ConcurrentSkipListMap<>(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return (int)(o1 - o2);
            }
        });
        this.total = 0;
        this.sum = 0;
        this.lock = new ReentrantLock();
        this.lc = this.lock.newCondition();
        this.t = Thread.ofVirtual().name("LRU").start(() -> evictExpiredEntity());
    }

    private long calculateSleep(long expiry) {
        long current = System.currentTimeMillis();
        return expiry - current;
    }
    private void evictExpiredEntity() {
        while (true) {
            if(!expiry.isEmpty()) {
                var tp = expiry.firstKey();
                long time = calculateSleep(tp);
                if(time <= 0) {
                    lock.lock();
                    try {
                        var top = expiry.firstEntry().getValue();
                        top.forEach(x -> {
                            total--;
                            sum -= map.get(x).value;
                            map.remove(x);});
                    } finally {
                        lock.unlock();
                    }
                } else {
                    try {
                        this.lc.await(time, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    @Override
    public void put(int key, int value, long ttl) {
        if(ttl <= 0) {
            throw new RuntimeException("Not a valid ttl passed" + ttl + " ttl must greater than zero");
        }
        lock.lock();
        try {
            long time = System.currentTimeMillis();
            long exp = ttl + time;
            if(map.containsKey(key)) {
                var record = map.get(key);
                expiry.get(record.expiryTime).remove(key);
                map.remove(key);
            }
            var record = new Record(value, exp);
            map.put(key, record);
            expiry.putIfAbsent(exp, new ConcurrentSkipListSet<>());
            expiry.get(exp).add(key);
        } finally {
            this.lc.signal();
            lock.unlock();
        }
    }

    @Override
    public int get(int key) {
        lock.lock();
        try {
            if(map.containsKey(key)) {
                return map.get(key).value;
            }
        } finally {
            lock.unlock();
        }
        return -1;
    }

    @Override
    public double calculateAvg() {
        lock.lock();
        try {
            return sum/(double)(total == 0 ? 1 : total);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() throws Exception {
        t.interrupt();
        while(t.isAlive());
    }
}

