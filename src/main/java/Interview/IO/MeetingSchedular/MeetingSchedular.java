package Interview.IO.MeetingSchedular;

import Interview.IO.MeetingSchedular.Services.*;
import Interview.IO.MeetingSchedular.Moldes.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingSchedular {
    private final BookingService bookingService;

    public MeetingSchedular(List<Room> roomList) {
        Map<String, RoomService> roomServiceMap = new HashMap<>();
        for(var r : roomList) {
            roomServiceMap.put(r.getId(), new RoomService(r, new MeetingService()));
        }
        bookingService = new BookingService(roomServiceMap, new NotificationService());
    }

    public BookingService getBookingService() {
        return bookingService;
    }
}
