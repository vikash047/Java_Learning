package Interview.IO.EventBus.Models;

public class EventIndex {
    private int index;

    public EventIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public EventIndex increment() {
        return new EventIndex(this.index + 1);
    }
}
