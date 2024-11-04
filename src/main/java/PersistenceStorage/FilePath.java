package PersistenceStorage;

import java.util.concurrent.atomic.AtomicInteger;

public class FilePath {
    private final AtomicInteger atomicInteger = new AtomicInteger(1);
    private static final String name = "disk_customStore_";
    public String getPath() {
        return name + atomicInteger.incrementAndGet() + ".bin";
    }

    public String getPath(String name) {
        return name + atomicInteger.incrementAndGet() + ".bin";
    }
}
