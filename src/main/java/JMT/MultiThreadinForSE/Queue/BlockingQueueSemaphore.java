package JMT.MultiThreadinForSE.Queue;

import java.util.concurrent.Semaphore;

public class BlockingQueueSemaphore<T> {
    T[] array;
    int size = 0;
    int cap = 0;
    int head = 0;
    int tail = 0;
    CountingSemaphore lock = new CountingSemaphore(1, 1);
    Semaphore consumer;
    Semaphore producer;
    public BlockingQueueSemaphore(int cap) {
        array = (T[])new Object[cap];
        this.cap = cap;
        producer = new Semaphore(cap);
        consumer = new Semaphore(0);
    }

    public void enqueue(T item) throws InterruptedException {
        producer.acquire();
        lock.aquire();
        if(tail == cap) {
            tail = 0;
        }
        array[tail] = item;
        size++;
        tail++;
        lock.release();
        consumer.release();
    }

    public T dequeue() throws InterruptedException {
        T item = null;
        consumer.acquire();
        lock.aquire();
        if(head == cap) {
            head = 0;
        }
        item = array[head];
        array[head] = null;
        head++;
        lock.release();
        producer.release();
        return item;
    }
}
