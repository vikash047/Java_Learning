package Interview.IO.MeetingSchedular.Services;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class MeetingService {
    private final ConcurrentSkipListMap<Long, Map.Entry<Long, String>> meeting;

    public MeetingService() {
        meeting = new ConcurrentSkipListMap<>();
    }

    public synchronized boolean canMeetingSchedule(Long start, Long end, String id) {
        var startEntry =  meeting.ceilingEntry(start);
        if (startEntry != null) {
            if (startEntry.getKey() < end) {
                return false;
            }
        }
        startEntry = meeting.floorEntry(start);
        if(startEntry != null && startEntry.getValue().getKey() > start) {
           return false;
        }
        meeting.put(start, new AbstractMap.SimpleEntry<>(end, id));
        return true;
    }

    public List<String> getAllMeetings() {
        return new ArrayList<>(meeting.values().stream().map(x -> x.getValue()).collect(Collectors.toList()));
    }
}
