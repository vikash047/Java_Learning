package Interview.IO.RateLimiter.Models;

public class RequestInfo {
    private final Long time;
    private final Client client;

    public RequestInfo(Long time, Client client) {
        this.time = time;
        this.client = client;
    }

    public Long getTime() {
        return time;
    }

    public ClientId getClientId() {
        return client.getClientId();
    }

    public int allowedPerSecond() {
        return client.getNoOfRequest();
    }
}
