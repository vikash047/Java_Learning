package Interview.IO.EventBus.Models;

import java.sql.Timestamp;

public class TimeStamp implements Comparable<TimeStamp>{
    public Long getVal() {
        return val;
    }

    private final Long val;

    public TimeStamp(Long val) {
        this.val = val;
    }

    @Override
    public int compareTo(TimeStamp o) {
        return this.val.compareTo(o.getVal());
    }
}
