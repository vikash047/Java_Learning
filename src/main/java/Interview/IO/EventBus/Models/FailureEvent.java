package Interview.IO.EventBus.Models;

import java.util.UUID;

public class FailureEvent extends Event{
    private final Throwable throwable;
    private final TimeStamp timestamp;
    public FailureEvent(Event event, Throwable throwable, TimeStamp timestamp) {
        super(new EventId(UUID.randomUUID().toString()), event.getEventName(), event.getAttributes(), event.getTimeStamp());
        this.throwable = throwable;
        this.timestamp = timestamp;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public TimeStamp getTimestamp() {
        return timestamp;
    }
}
