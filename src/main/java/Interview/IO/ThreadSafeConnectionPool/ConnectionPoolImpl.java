package Interview.IO.ThreadSafeConnectionPool;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

public class ConnectionPoolImpl<Connection> implements ConnectionPool<Connection>{

    private final Semaphore connectionAvailable;
    private final Deque<Connection> connections;
    public ConnectionPoolImpl(List<Connection> connectionList) {
        this.connectionAvailable = new Semaphore(connectionList.size());
        this.connections = new LinkedBlockingDeque<>(connectionList);
    }

    @Override
    public Connection getConnection() throws Exception {
        try {
            if(connectionAvailable.tryAcquire()) {
                return connections.poll();
            } else {
                throw new Exception("No connection available");
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void releaseConnection(Connection connection) {
        connections.addLast(connection);
        connectionAvailable.release();
    }
}
