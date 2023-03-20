package InterestingProblems;


import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

import static java.lang.System.*;

/*
problem is implement a map with operation put(key, value, ttl), get(key), delete(key), getAverage() till now all the valid keys. 
 */
public class JavaMapWithTTL<String> implements MapEntry<String> {
    private final ConcurrentMap<String, Map.Entry<Integer, Long>> map;
    private final ConcurrentSkipListMap<Long, ConcurrentSkipListSet<String>> expiry;
    private final ExecutorService executorService;

    private final ReentrantLock lock;

    private final Condition calculateAvg;
    private volatile Long currentSum;

    private volatile Boolean canCalAvg;
    public JavaMapWithTTL() {
        map = new ConcurrentHashMap<>();
        expiry = new ConcurrentSkipListMap<>();
        currentSum = 0l;
        executorService = Executors.newVirtualThreadPerTaskExecutor();
        CompletableFuture.runAsync(() -> init(), executorService).exceptionally(thr -> {
            out.println("Exception returned " + thr);
            return null;
        });
        lock = new ReentrantLock();
        calculateAvg = lock.newCondition();
        canCalAvg = true;
    }

    @Override
    public void put(String string, Integer value, long epochttl) {
        long time = currentTimeMillis() + epochttl;
        map.put(string, new AbstractMap.SimpleEntry<>(value, time));
        expiry.putIfAbsent(time, new ConcurrentSkipListSet<>());
        expiry.get(time).add(string);
        if(!map.containsKey(string)) {
            currentSum += value;
        }
    }

    @Override
    public Integer get(String string) {
        if(map.containsKey(string)) {
            return map.get(string).getKey();
        }
        return null;
    }

    @Override
    public boolean delete(String string) {
        if(map.containsKey(string)) {
            var entry = map.get(string);
            expiry.get(entry.getValue()).remove(string);
            if(expiry.get(entry.getValue()).size() == 0) {
                expiry.remove(entry.getValue());
            }
            map.remove(string);
            currentSum -= entry.getKey();
            return true;
        }
        return false;
    }

    @Override
    public Double getAverage() throws InterruptedException {
        while (!canCalAvg) {
            calculateAvg.await();
        }
        return currentSum/(double)map.size();
    }

    private void init() {
        while (true) {
            if(expiry.firstEntry().getKey() <= currentTimeMillis()) {
                try {
                    lock.lock();
                    canCalAvg = false;
                    for(var keys : expiry.pollFirstEntry().getValue()) {
                        var entry = map.get(keys);
                        currentSum -= entry.getKey();
                        map.remove(keys);
                    }
                    canCalAvg = true;
                } finally {
                    lock.unlock();
                    calculateAvg.signalAll();
                }
            }
        }
    }
}
