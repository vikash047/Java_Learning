package Interview.IO.MeetingSchedular.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Interview.IO.MeetingSchedular.*;
import Interview.IO.MeetingSchedular.Moldes.*;
import Interview.IO.MeetingSchedular.Util.*;

import java.util.ArrayList;
import java.util.List;

class BookingServiceTest {

    List<UserId> userIdList = new ArrayList<>();

    @Test
    void book() {
        List<Room> roomList = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            roomList.add(new Room(IdGenerator.getId(), "roo is " + i));
        }
        MeetingSchedular meetingSchedular =  new MeetingSchedular(roomList);
        var m = meetingSchedular.getBookingService().book(create(9l, 11l, 0, roomList));
        Assertions.assertTrue(m.getId() != null);
        m = meetingSchedular.getBookingService().book(create(9l, 10l, 0, roomList));
        Assertions.assertTrue(m.getId() == null);
        m = meetingSchedular.getBookingService().book(create(8l, 12l, 0, roomList));
        Assertions.assertTrue(m.getId() == null);
        m = meetingSchedular.getBookingService().book(create(13l, 15l, 0, roomList));
        Assertions.assertTrue(m.getId() != null);
        m = meetingSchedular.getBookingService().book(create(15l, 16l, 0, roomList));
        Assertions.assertTrue(m.getId() != null);
        m = meetingSchedular.getBookingService().book(create(10l, 11l, 0, roomList));
        Assertions.assertTrue(m.getId() == null);
    }

    @Test
    void bookedMeeting() {
    }

    @Test
    void testBookedMeeting() {
    }

    private Meeting create(Long start, Long end, int rindex, List<Room> roomList) {
        return new Meeting("m", userIdList,start, end, roomList.get(rindex).getId());
    }
}