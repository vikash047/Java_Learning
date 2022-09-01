package GrokingJavaConcurrency.BlockingQueue;

public class BlockingQueueWithoutMutex<T> extends BlockingQueueFirst<T> {
    private final CountingSemaphore semLock = new CountingSemaphore(1, 0);
    private final CountingSemaphore semFull;
    private final CountingSemaphore semEmpty;
    public BlockingQueueWithoutMutex(int capacity) {
        super(capacity);
        semFull = new CountingSemaphore(capacity, 0);
        semEmpty = new CountingSemaphore(capacity, capacity);
    }

    @Override
    public void enqueue(T item) throws InterruptedException {
        semFull.aquire();
        semLock.aquire();
        if(tail == capacity) {
            tail = 0;
        }
        array[tail] = item;
        tail++;
        size++;
        semLock.release();
        semEmpty.release();
    }

    @Override
    public T dequeue() throws InterruptedException {
        T item = null;
        semEmpty.aquire();
        semLock.aquire();
        if(head == capacity) {
            head = 0;
        }
        item = array[head];
        array[head] = null;
        head++;
        size--;
        semLock.release();
        semFull.release();
        return item;
    }
}
