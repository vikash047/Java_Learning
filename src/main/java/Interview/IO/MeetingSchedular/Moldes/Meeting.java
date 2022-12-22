package Interview.IO.MeetingSchedular.Moldes;

import java.util.List;

public class Meeting {
    private String id;
    private final String title;
    private final List<UserId> userIdList;

    private final Long startTime;

    private final Long endTime;

    private final String roomId;

    public Meeting(String title, List<UserId> userIdList, Long startTime, Long endTime, String roomId) {
        this.title = title;
        this.userIdList = userIdList;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<UserId> getUserIdList() {
        return userIdList;
    }

    private void setId(String id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public Meeting addId(String id) {
        var m =  new Meeting(this.title, this.userIdList, this.startTime, this.endTime, this.roomId);
        m.setId(id);
        return m;
    }
}
