package Interview.IO.MeetingSchedular.Moldes;

public class Room {
    private final String id;
    private final String name;

    public Room(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
