package Interview.IO.RateLimiter;

import Interview.IO.RateLimiter.Models.Client;
import Interview.IO.RateLimiter.Models.ClientId;
import Interview.IO.RateLimiter.Models.RequestInfo;
import Interview.IO.RateLimiter.Services.Limiter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RateLimiter that)) return false;
        return Objects.equals(clientIdClientMap, that.clientIdClientMap) && Objects.equals(limiter, that.limiter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientIdClientMap, limiter);
    }
}
