package Interview.IO.DistrubutedCache;

import Interview.IO.DistrubutedCache.Models.AccessDetails;
import Interview.IO.DistrubutedCache.Models.Record;
import Interview.IO.DistrubutedCache.Models.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class LRU<KEY> implements EvictionAlgorithm<KEY> {
    private final ConcurrentSkipListMap<AccessDetails, ConcurrentSkipListSet<KEY>> priorityQueue;

    public LRU() {
        priorityQueue = new ConcurrentSkipListMap<>((first, second) -> {
            return (int) (first.getLastAccessTime() - second.getLastAccessTime());
        });
    }

    @Override
    public synchronized List<KEY> evictRecord() {
        List<KEY> result = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            ConcurrentSkipListSet<KEY> keys = priorityQueue.pollFirstEntry().getValue();
            if(keys.isEmpty()) continue;
            result.addAll(keys);
            break;
        }
        return result;
    }


    @Override
    public void addRecordDetails(Record record) {
        priorityQueue.putIfAbsent(record.getAccessDetails(), new ConcurrentSkipListSet<>());
        priorityQueue.get(record.getAccessDetails()).add((KEY) record.getKey());
    }

    @Override
    public void removeRecord(Record record) {
        priorityQueue.get(record.getAccessDetails()).remove(record.getKey());
    }
}
