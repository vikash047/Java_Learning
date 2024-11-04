package LLDPracctice.UberRideSharingProblem;

public class Request {
    private long start;
    private long end;

    private int seat;

    public Request(long start, long end, int seat, Driver driver) {
        this.start = start;
        this.end = end;
        this.seat = seat;
        this.driver = driver;
    }

    private Driver driver;

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public int getSeat() {
        return seat;
    }
}
