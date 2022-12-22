package Interview.IO.RateLimiter;

import Interview.IO.RateLimiter.Models.Client;
import Interview.IO.RateLimiter.Models.ClientId;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import Interview.IO.RateLimiter.Models.RequestInfo;
import Interview.IO.RateLimiter.Services.*;

public class RateLimiter {

    private final Map<ClientId, Client> clientIdClientMap;
    private final Limiter limiter;
    public RateLimiter() {
        clientIdClientMap = new HashMap<>();
        limiter = new Limiter();
    }

    public boolean isAllow(ClientId id) {
        return limiter.isAllowed(new RequestInfo(System.currentTimeMillis(), clientIdClientMap.get(id)));
    }

    public CompletionStage<Void> registerClient(Client client) {
        if(!clientIdClientMap.containsKey(client.getClientId())) {
            clientIdClientMap.put(client.getClientId(), client);
        }
        return CompletableFuture.completedFuture(null).thenAccept(x -> {});
    }

}
