package Interview.IO.MeetingSchedular.Services;

import Interview.IO.MeetingSchedular.Moldes.*;

import java.util.List;

public class RoomService {
    private final Room room;
    private final MeetingService meetingService;
    public RoomService(Room room, MeetingService meetingService) {
        this.room = room;
        this.meetingService = meetingService;
    }

    public boolean book(Meeting meeting) {
        return meetingService.canMeetingSchedule(meeting.getStartTime(), meeting.getEndTime(), room.getId());
    }

    public List<String> bookedHistory() {
        return meetingService.getAllMeetings();
    }

}
