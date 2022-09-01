package GrokingJavaConcurrency.BlockingQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithMutex<T> extends BlockingQueueFirst<T>{
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();
    public BlockingQueueWithMutex(int capacity) {
        super(capacity);
    }

    @Override
    public void enqueue(T item) throws InterruptedException {
        lock.lock();
        while (size == capacity) {
            full.await();
        }
        if(tail == capacity) {
            tail = 0;
        }
        array[tail] = item;
        tail++;
        size++;
        empty.signal();
        lock.unlock();
    }

    @Override
    public synchronized T dequeue() throws InterruptedException {
        T item = null;
        lock.lock();
        while (size == 0) {
            empty.await();
        }
        if(head == capacity) {
            head = 0;
        }
        item = array[head];
        array[head] = null;
        head++;
        size--;
        full.signal();
        lock.unlock();
        return item;

    }
}
