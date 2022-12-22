package Interview.IO.RateLimiter.Models;

public class Client {
    private final ClientId clientId;
    private final String name;
    private final int noOfRequest;

    public Client(ClientId clientId, String name, int noOfRequest) {
        this.clientId = clientId;
        this.name = name;
        this.noOfRequest = noOfRequest;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public int getNoOfRequest() {
        return noOfRequest;
    }
}
