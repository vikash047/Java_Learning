package Interview.IO.DistrubutedCache;

import Interview.IO.DistrubutedCache.Models.AccessDetails;
import Interview.IO.DistrubutedCache.Models.Record;
import Interview.IO.DistrubutedCache.Models.Timer;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

public class Cache<KEY, VALUE> {

    private final DataSource<KEY, VALUE> dataSource;
    private final Duration defaultExpiry;
    private final Map<KEY, CompletionStage<Record<KEY,VALUE>>> cache;
    private final ConcurrentSkipListMap<Long, ConcurrentSkipListSet<KEY>> expiryQueue;
    private final FlushAlgorithm flushAlgorithm;
    private final EvictionAlgorithm evictionAlgorithm;
    private final Integer CAPACITY;
    private final Timer timer;
    private final ExecutorService[] executorServices;
    private final int threadPoolSize;

    public Cache(DataSource<KEY, VALUE> dataSource, Duration defaultExpiry, FlushAlgorithm flushAlgorithm, EvictionAlgorithm evictionAlgorithm, Integer capacity, Timer timer, int threadPoolSize) {
        this.dataSource = dataSource;
        this.defaultExpiry = defaultExpiry;
        this.flushAlgorithm = flushAlgorithm;
        this.evictionAlgorithm = evictionAlgorithm;
        CAPACITY = capacity;
        this.timer = timer;
        this.threadPoolSize = threadPoolSize;
        this.cache = new ConcurrentHashMap<>();
        expiryQueue = new ConcurrentSkipListMap<>();
        executorServices = new ExecutorService[this.threadPoolSize];
        for(int i = 0; i < this.threadPoolSize; i++) {
            executorServices[i] = Executors.newSingleThreadExecutor();
        }
    }

    private <U> CompletionStage<U> runInExec(KEY key, CompletionStage<U> task) {
        return CompletableFuture.supplyAsync(() -> task, executorServices[key.hashCode() % threadPoolSize])
                .thenCompose(Function.identity());
    }

    public CompletionStage<VALUE> get(KEY key) {
        return runInExec(key,getFromCache(key));
    }

    private CompletionStage<VALUE> getFromCache(KEY key) {
        CompletionStage<Record<KEY, VALUE>> result;
        if(!cache.containsKey(key)) {
            result = addToCache(key, defaultExpiry, loadFromDB(dataSource, key));
        } else {
            result = cache.get(key).thenCompose(record -> {
                if(hasExpired(record.getExpiryTime())) {
                    expiryQueue.get(record.getExpiryTime()).remove(key);
                    evictionAlgorithm.removeRecord(record);
                    return addToCache(key, defaultExpiry, loadFromDB(dataSource, key));
                } else {
                    return CompletableFuture.completedFuture(record);
                }
            });
        }
        return result.thenApply(record -> {
            evictionAlgorithm.removeRecord(record);
            final AccessDetails accessDetails = record.getAccessDetails().update(Timer.getCurrentTime());
            record.setAccessDetails(accessDetails);
            evictionAlgorithm.addRecordDetails(record);
            return record.getValue();
        });
    }

    public CompletionStage<Void> set(KEY key, VALUE value, Duration expiryTimeInNano) {
        return runInExec(key, setInCache(key, value, expiryTimeInNano));
    }

    private CompletionStage<Void> setInCache(KEY key, VALUE value, Duration expiryTimeInNano) {
        CompletionStage<Void> result = CompletableFuture.completedFuture(null);
        if(cache.containsKey(key)) {
            result = cache.remove(key).thenAccept(oldRecord -> {
                evictionAlgorithm.removeRecord(oldRecord);
                expiryQueue.get(oldRecord.getExpiryTime());
            });
        }
        return result.thenCompose(__ -> addToCache(key, expiryTimeInNano, CompletableFuture.completedFuture(value)))
                .thenCompose(record -> {
                    final CompletionStage<Void> writeOperation = persist(record);
                    return flushAlgorithm == FlushAlgorithm.WRITE_THROUGH_CACHE ? writeOperation : CompletableFuture.completedFuture(null);
                });
    }

    private CompletionStage<Void> persist(Record<KEY,VALUE> record) {
        return dataSource.persist(record.getKey(), record.getValue());
    }

    private CompletionStage<Record<KEY, VALUE>> addToCache(KEY key, Duration expiryTime, CompletionStage<VALUE> valueFuture) {
        manageEntries();
        CompletionStage<Record<KEY, VALUE>> futureResult = valueFuture.thenApply(value -> {
            final Long time = Timer.getCurrentTime();
            final Record<KEY, VALUE> record = new Record<>(key, value, time, time + expiryTime.toNanos());
            expiryQueue.putIfAbsent(record.getExpiryTime(), new ConcurrentSkipListSet<>());
            expiryQueue.get(record.getExpiryTime()).add(key);
            evictionAlgorithm.addRecordDetails(record);
            return record;
        });
        cache.put(key, futureResult);
        return futureResult;
    }

    private synchronized void manageEntries() {
        if (cache.size() >= CAPACITY) {
            while (!expiryQueue.isEmpty() && hasExpired(expiryQueue.firstKey())) {
                for(KEY key : expiryQueue.pollFirstEntry().getValue()) {
                    cache.remove(key).thenAccept(oldRecord -> {
                        evictionAlgorithm.removeRecord(oldRecord);
                    });
                }
            }
        }
        if(cache.size() >= CAPACITY) {
            List<KEY> keys = evictionAlgorithm.evictRecord();
            keys.stream().forEach(x -> cache.remove(x).thenAccept(oldRecord -> expiryQueue.get(oldRecord.getLastExpiryTime()).remove(x))
                    .exceptionally(t -> {
                        System.out.println(t);
                        return null;
                    }));
        }
    }

    private boolean hasExpired(Long firstKey) {
        return Timer.getCurrentTime() - firstKey > 0;
    }

    private CompletionStage<VALUE> loadFromDB(final DataSource<KEY, VALUE> dataSource, KEY key) {
        return dataSource.load(key).whenComplete((value, throwable) -> {
            if (throwable == null) {
            }
        });
    }

}
