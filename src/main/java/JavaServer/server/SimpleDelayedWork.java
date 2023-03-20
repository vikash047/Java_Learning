package JavaServer.server;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleDelayedWork {
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final ReentrantLock reentrantLock = new ReentrantLock();
    public String doJob() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reentrantLock.lock();
            Thread.sleep(200);
            stringBuilder.append("Response " + atomicInteger.incrementAndGet());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}
