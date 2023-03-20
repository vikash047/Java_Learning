package Interview.IO.ThreadSafeConnectionPool;

public class Connection {
    private final String name;

    public Connection(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
