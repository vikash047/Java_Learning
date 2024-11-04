package Media.Net.Models;

import java.util.ArrayList;
import java.util.List;

public class User implements UserActions{
    private int id;
    List<Event> eventList = new ArrayList<>();

    @Override
    public void accept(Event event) {
        eventList.add(event);
    }

    @Override
    public void reject(Event event) {
        eventList.remove(event);
    }

    @Override
    public List<Event> getCalendar(int startTime, int endTime) {
        List<Event> calendar = new ArrayList<>();
        for(var e : eventList) {
            if(e.startTime >= startTime && e.endTime <= endTime) {
                calendar.add(e);
            }
        }
        return calendar;
    }

    @Override
    public List<int[]> getFreeSlot(int startTime, int endTime) {
        int start = startTime;
        // 0  15
        // [2, 4], [3, 8], [5, 10], [12, 14]
        List<int[]> free = new ArrayList<>();
        for(var e : eventList) {
            if(e.startTime >= endTime) break;
            if(start < e.startTime) {
                free.add(new int[] {start, e.startTime});
            }
            start = Math.max(start, e.endTime);
        }
        if(start < endTime) {
            free.add(new int[] {start, endTime});
        }
        return free;
    }
}
