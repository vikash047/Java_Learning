package Interview.IO.ThreadSafeConnectionPool;

public interface ConnectionPool<Connection> {
    Connection getConnection() throws Exception;
    void releaseConnection(Connection connection);
}
