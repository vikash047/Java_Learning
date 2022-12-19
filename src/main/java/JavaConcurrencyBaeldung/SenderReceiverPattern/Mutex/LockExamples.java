package JavaConcurrencyBaeldung.SenderReceiverPattern.Mutex;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.*;

public class LockExamples {
    private int cnt = 0;
    private ReentrantLock reentrantLock = new ReentrantLock();

    public void solve() {
        reentrantLock.lock();
        try {
            cnt++;
        } finally {
            reentrantLock.unlock();
        }
    }

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock write = readWriteLock.writeLock();

    public void solveReadWriteLock() {
        write.lock();
        try {
            cnt++;
        } finally {
            write.unlock();
        }
    }

    private Lock read = readWriteLock.readLock();

    public int read() {
        read.lock();
        try {
            return cnt;
        } finally {
            read.unlock();
        }
    }

    Map<String, String> mp = new HashMap<>();
    private StampedLock stampedLock = new StampedLock();

    public void put(String key, String value) {
        long stamp = stampedLock.writeLock();
        try {
            mp.put(key, value);
        } finally {
            if (stampedLock.validate(stamp))
                stampedLock.unlock(stamp);
        }
    }

    public String read(String key) {
        //long stamp = stampedLock.readLock();
        long stamp = stampedLock.tryOptimisticRead();
        try {
            return mp.get(key);
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    private Stack<String> stack = new Stack<>();
    private ReentrantLock stLock = new ReentrantLock(true);
    private Condition full = stLock.newCondition();
    private Condition empty = stLock.newCondition();
    private static final int Capacity = 5;

    public void pushToStack(String item) {
        try {
            stLock.lock();
            while (stack.size() == Capacity) {
                full.await();
            }
            stack.push(item);
            empty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stLock.unlock();
        }
    }

    public String popFromStack() {
        try {
            stLock.lock();
            while (stack.size() == 0) {
                empty.await();
            }
            return stack.pop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            full.signalAll();
            stLock.unlock();
        }
        return null;
    }
}
