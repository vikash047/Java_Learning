package Interview.IO.RateLimiter.Models;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientQueue {
    private final Map<ClientId, Deque<RequestInfo>> clientIdDequeMap;

    public ClientQueue() {
        clientIdDequeMap = new HashMap<>();
    }

    public boolean isRequestAllowed(RequestInfo requestInfo) {
        clientIdDequeMap.putIfAbsent(requestInfo.getClientId(), new LinkedBlockingDeque<>());
        var q = clientIdDequeMap.get(requestInfo.getClientId());
        var time = System.currentTimeMillis();
        if(q.size() == 0) {
            q.addLast(requestInfo);
            return true;
        }
        if(time - 1000 < q.getFirst().getTime() && q.size() >= requestInfo.allowedPerSecond()) {
            return false;
        } else if(q.size() < requestInfo.allowedPerSecond()) {
            q.addLast(requestInfo);
            return true;
        } else if(time - 1000 > q.getFirst().getTime()) {
            q.pollFirst();
            q.addLast(requestInfo);
            return true;
        }
        return false;
    }
    public void add(ClientId cLientId) {
        clientIdDequeMap.put(cLientId, new LinkedBlockingDeque<>());
    }




}
