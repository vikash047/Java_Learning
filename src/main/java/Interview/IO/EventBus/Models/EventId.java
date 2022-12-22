package Interview.IO.EventBus.Models;

public class EventId {
    private final String Id;

    public EventId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }
}
