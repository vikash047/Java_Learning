package Interview.IO.DistrubutedCache;

import Interview.IO.DistrubutedCache.Models.Record;

import java.util.List;

public interface EvictionAlgorithm<KEY> {
    List<KEY> evictRecord();
    void addRecordDetails(Record record);
    void removeRecord(Record record);
}
