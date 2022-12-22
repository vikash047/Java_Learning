package Interview.IO.DistrubutedCache.Models;

import java.time.Duration;

public class Record<KEY, VALUE> {
    private KEY key;
    private VALUE value;
    private final long insertionTime;
    private long expiryTime;

    public void setAccessDetails(AccessDetails accessDetails) {
        this.accessDetails = accessDetails;
    }

    private AccessDetails accessDetails;
    private long lastExpiryTime;

    public Record(KEY key, VALUE value, long insertionTime, long expiryTime) {
        this.key = key;
        this.value = value;
        this.insertionTime = insertionTime;
        this.accessDetails = new AccessDetails(insertionTime);
        this.expiryTime = expiryTime;
    }

    public KEY getKey() {
        return key;
    }

    public VALUE getValue() {
        return value;
    }

    public long getInsertionTime() {
        return insertionTime;
    }

    public AccessDetails getAccessDetails() {
        return accessDetails;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.lastExpiryTime = this.expiryTime;
        this.expiryTime =  expiryTime;
    }


    @Override
    public String toString() {
        return "Record{" +
                "key=" + key +
                ", value=" + value +
                ", insertionTime=" + insertionTime +
                ", accessDetails=" + accessDetails.toString() +
                '}';
    }

    public long getLastExpiryTime() {
        return lastExpiryTime;
    }
}
