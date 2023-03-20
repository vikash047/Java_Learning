package JavaServer;

import JavaServer.server.DelayedHttphandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class MainServer {
    private static void runServer(final boolean virtual, final boolean withLock) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/example", new DelayedHttphandler(withLock));
        if(virtual) {
            server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        } else {
            server.setExecutor(Executors.newFixedThreadPool(200));
        }
        server.start();
    }

    public static void main(String[] args) throws IOException {
        runServer(true, false);
    }
}
