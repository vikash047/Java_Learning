package PhonePeToDO.Modles;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class UpdateLog {
    private final TreeMap<Long, LogData> logDataList;

    public UpdateLog() {
        this.logDataList = new TreeMap<>();
    }

    public List<LogData> getLogDataList() {
        return new ArrayList<>(logDataList.values());
    }

    public void addLog(LogData logData) {
        this.logDataList.putIfAbsent(logData.getUpdateTime(), logData);
    }

    public List<LogData> getLogDataList(Long startTime, Long endTime) {
        var start = this.logDataList.tailMap(startTime);
        List<LogData> result = new ArrayList<>();
        for(var kv : start.entrySet()) {
            if(kv.getKey() > endTime) {
                break;
            }
            result.add(kv.getValue());
        }
        return result;
    }
}
