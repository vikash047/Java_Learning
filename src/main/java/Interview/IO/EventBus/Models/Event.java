package Interview.IO.EventBus.Models;

import java.util.Map;

public class Event {
    private final EventId id;
    private final String eventName;
    private final Map<String, String> attributes;
    private final  TimeStamp timeStamp;

    public Event(EventId id, String eventName, Map<String, String> attributes, TimeStamp timeStamp) {
        this.id = id;
        this.eventName = eventName;
        this.attributes = attributes;
        this.timeStamp = timeStamp;
    }

    public EventId getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }
}
