package Interview.IO.RateLimiter.Services;

import Interview.IO.RateLimiter.Models.Client;
import Interview.IO.RateLimiter.Models.ClientQueue;
import Interview.IO.RateLimiter.Models.RequestInfo;

public class Limiter {
    private final ClientQueue clientQueue;

    public Limiter() {
        this.clientQueue = new ClientQueue();
    }

    public void addClient(Client client) {
        clientQueue.add(client.getClientId());
    }

    public boolean isAllowed(RequestInfo requestInfo) {
        return clientQueue.isRequestAllowed(requestInfo);
    }
}
