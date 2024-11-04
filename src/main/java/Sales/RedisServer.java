package Sales;

import java.util.List;

public interface RedisServer {
    List<String> getRedisServers();
    List<String> getSlotRanged(String id);
}
