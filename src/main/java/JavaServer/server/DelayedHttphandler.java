package JavaServer.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DelayedHttphandler implements HttpHandler {
    private final boolean withLock;
    private final List<SimpleDelayedWork> workList;
    private final int workCount = 50;
    private final AtomicLong atomicLong;
    public DelayedHttphandler(final boolean withLock) {
        this.withLock = withLock;
        workList = new ArrayList<>();
        for(int i = 0; i < workCount; i++) {
            workList.add(new SimpleDelayedWork());
        }
        atomicLong = new AtomicLong();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuffer buffer = new StringBuffer();
        if(withLock) {
            buffer.append(workList.get((int) (atomicLong.incrementAndGet()%workCount)).doJob());
        } else {
            try {
                Thread.sleep(200);
                 buffer.append("Ping " + atomicLong.incrementAndGet());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        exchange.sendResponseHeaders(200, buffer.length());
        OutputStream os = exchange.getResponseBody();
        os.write(buffer.toString().getBytes());
        os.close();
    }
}
