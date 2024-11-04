package Media.Net.Models;

import java.util.ArrayList;
import java.util.List;

public class Event implements Comparable<Event> {

    public int id;
    public int startTime;
    public int endTime;

    public User owner;

    public List<User> invitee = new ArrayList<>();

    public List<User> accepted = new ArrayList<>();
    public List<User> rejected = new ArrayList<>();

    Location location;

    public Event(int id, int startTime, int endTime, User owner) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getInvitee() {
        return invitee;
    }

    public void setInvitee(List<User> invitee) {
        this.invitee = invitee;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int compareTo(Event o) {
        return 0;
    }

    public Event clone(Event event) {
        Event newEvent = new Event(event.id, event.startTime, event.endTime, event.owner);
        return newEvent;
    }
}
