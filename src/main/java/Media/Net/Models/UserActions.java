package Media.Net.Models;

import java.util.List;

public interface UserActions {
    void accept(Event event);
    void reject(Event event);

    List<Event> getCalendar(int startTime, int endTime);

    List<int[]> getFreeSlot(int startTime, int endTime);
}
