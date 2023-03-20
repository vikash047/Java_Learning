package Interview.IO.ThreadSafeConnectionPool;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        List<Connection> connectionList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Connection c = new Connection(Integer.toString(i));
            connectionList.add(c);
        }
        ConnectionPoolImpl<Connection> connectionPool = new ConnectionPoolImpl(connectionList);
        Thread t = Thread.ofVirtual().name("first").start(() -> {
            List<Connection> connectionList1 = new ArrayList<>();
            int count = 0;
            for(int i = 0; i < 10; i++) {
                try {
                    connectionList1.add(connectionPool.getConnection());
                } catch (Exception e) {
                    count++;
                }
            }
            System.out.println(count);
            connectionList1.forEach(x -> connectionPool.releaseConnection(x));
        });
        /*Thread t1 = Thread.ofVirtual().name("second").start(() -> {
            List<Connection> connectionList1 = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                try {
                    connectionList1.add(connectionPool.getConnection());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            connectionList1.forEach(x -> connectionPool.releaseConnection(x));
        });*/
        t.join();
       // t1.join();
    }
}
