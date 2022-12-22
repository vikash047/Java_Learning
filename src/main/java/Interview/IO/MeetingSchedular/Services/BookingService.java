package Interview.IO.MeetingSchedular.Services;

import Interview.IO.MeetingSchedular.Moldes.*;
import Interview.IO.MeetingSchedular.Util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingService {
    private final Map<String, Meeting> meetingMap;
    private final Map<String, RoomService> roomServiceMap;
    private final NotificationService notificationService;
    public BookingService(Map<String, RoomService> roomServiceMap, NotificationService notificationService) {
        this.roomServiceMap = roomServiceMap;
        this.notificationService = notificationService;
        meetingMap = new HashMap<>();
    }

    public Meeting book(Meeting meeting) {
        if(roomServiceMap.containsKey(meeting.getRoomId())) {
            RoomService service = roomServiceMap.get(meeting.getRoomId());
            if(service.book(meeting)) {
                notificationService.notify(meeting.getUserIdList());
                meeting = meeting.addId(IdGenerator.getId());
                meetingMap.put(meeting.getId(), meeting);
            }
        }
        return meeting;
    }
    public List<Meeting> bookedMeeting(String roomId) {
        if(roomServiceMap.containsKey(roomId)) {
            List<String> ids =  roomServiceMap.get(roomId).bookedHistory();
            List<Meeting> result = new ArrayList<>();
            return ids.stream().map(x -> meetingMap.get(x)).collect(Collectors.toList());
        }
        return null;
    }

    public List<Meeting> bookedMeeting() {
        return new ArrayList<>(meetingMap.values());
    }

}
