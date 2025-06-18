package JMT.MultiThreadinForSE.Queue;

public class BlockingQueueSyncWord<T> {
    T[] array;
    int size = 0;
    int cap = 0;
    int head = 0;
    int tail = 0;
    public BlockingQueueSyncWord(int cap) {
        array = (T[])new Object[cap];
        this.cap = cap;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
       // busy wait check condition then proceeds.
        while (size == cap) {
            wait();
            // lock.unlock then after lock.lock() give up then try to acquire in the while loop constantly.
        }
        if(tail == cap) {
            tail = 0;
        }
        array[tail] = item;
        size++;
        tail++;
        // take an example queue size is one. now one consumer thread produce the item then call notify then another consumer
        // thread gets the chance and found queue is full and goes into wait state so it would become dead lock as dequeue thread
        // waiting won't get chance.
        notifyAll();
    }
    public synchronized T dequeue() throws InterruptedException {
        T item = null;
        while (size == 0) {
            wait();
        }
        if(head == cap) {
            head = 0;
        }
        item = array[head];
        array[head] = null;
        head++;
        size--;
        notifyAll();
        return item;
    }
}
