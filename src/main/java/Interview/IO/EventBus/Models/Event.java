package Interview.IO.EventBus.Models;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(eventName, event.eventName) && Objects.equals(attributes, event.attributes) && Objects.equals(timeStamp, event.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, attributes, timeStamp);
    }
}
