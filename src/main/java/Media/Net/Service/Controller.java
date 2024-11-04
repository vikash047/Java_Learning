package Media.Net.Service;

import Media.Net.Models.Event;
import Media.Net.Models.IdGenerator;
import Media.Net.Models.User;

import java.util.*;

public class Controller{
    Map<Integer, Event> eventMap = new HashMap();
    Map<Integer, User> userMap = new HashMap<>();

    void createEvent(Event event) {
        int id = IdGenerator.id();
        event.setId(id);
        eventMap.put(event.getId(), event);
        for(var user : event.invitee) {
            user.accept(event);
        }
        event.owner.accept(event);
    }
    boolean updateEvent(Event event) {
        if(eventMap.containsKey(event.getId())) {
            eventMap.put(event.getId(), event);
            return true;
        }
        return false;
    }
    List<Event> getEvents(int startTime, int endTime, int userId) {
        if(userMap.containsKey(userId)) {
            return userMap.get(userId).getCalendar(startTime, endTime);
        }
        return null;
    }

    void accept(int id, Event event) {
        var user = userMap.get(id);
        event.invitee.remove(user);
        event.accepted.add(user);
        user.accept(event);
    }

    void reject(int id, Event event) {
        var user = userMap.get(id);
        event.invitee.remove(user);
        event.rejected.add(user);
        user.reject(event);
    }

    List<int[]> getFreeSlot(int startTime, int endTime, List<Integer> userIds) {
        Map<Integer, List<int[]>> freeSlots = new HashMap<>();
        for(var userId : userIds) {
            if(userMap.containsKey(userId)) {
                freeSlots.put(userId,userMap.get(userId).getFreeSlot(startTime, endTime));
            } else {
                return null;
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(freeSlots.get(o1[0]).get(o1[1])[0], freeSlots.get(o2[0]).get(o2[1])[0]);
            }
        });
        for(var e : freeSlots.entrySet()) {
            pq.offer(new int[] {e.getKey(), 0});
        }
        List<int[]> slots = new ArrayList<>();
        // 0 14
        // [2, 5], [6, 8], [12, 14]
        // [3, 7], [8, 10]
        int start = startTime;
        while(!pq.isEmpty()) {
            var e = pq.poll();
            var s = freeSlots.get(e[0]).get(e[1]);
            if(start < s[0]) {
                slots.add(new int[] {start, s[0]});
            }
            start = Math.max(start, s[1]);
            if(freeSlots.get(s[0]).size() < s[1]) {
                pq.offer(new int[]{s[0], s[1]++});
            }
        }
        return slots;
    }
}
