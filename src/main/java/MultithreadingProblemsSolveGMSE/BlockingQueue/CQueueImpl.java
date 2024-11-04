package MultithreadingProblemsSolveGMSE.BlockingQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CQueueImpl<T> implements CQueue<T>{

    private final ReentrantLock lock;
    private final Condition full;
    private final Condition empty;

    private T[] queue;
    private int tail;
    private  int head;
    private int cnt;

    private int cap;

    public CQueueImpl(int capacity) {
        lock = new ReentrantLock();
        full = lock.newCondition();
        empty = lock.newCondition();
        this.cap = capacity;
        this.queue = (T[]) new Object[capacity];
        this.tail = 0;
        this.head = 0;
        this.cnt = 0;
    }

    @Override
    public void offer(T item) {
        lock.lock();
        try {
            while(cnt == cap) {
                full.await();
            }
            queue[tail] = item;
            tail %= cap;
            cnt++;
            empty.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T getTop() {
        lock.lock();
        T item = null;
        try {
            while (cnt == 0) {
                empty.await();
            }
            item = queue[head];
            queue[head] = null;
            head %= cap;
            cnt--;
            full.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return item;
    }
}
