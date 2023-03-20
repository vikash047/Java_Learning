package GrokingJavaConcurrency.BlockingQueue;

public class BlockingQueueFirst<T> {
    protected final T[] array;
    protected final int capacity;
    protected int size;
    protected int head;
    protected int tail;


    public BlockingQueueFirst(int capacity ) {

        this.array = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while(size == capacity) {
            wait();
        }
        tail = tail%capacity;
        array[tail] = item;
        size++;
        tail++;
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        T item = null;
        while(size == 0) {
            wait();
        }
        head = head%capacity;
        item = array[head];
        array[head] = null;
        head++;
        size--;
        return item;
    }
}
