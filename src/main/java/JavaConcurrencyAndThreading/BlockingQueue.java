package JavaConcurrencyAndThreading;

import java.util.concurrent.Semaphore;

public class BlockingQueue<T> {
    private Object lock = new Object();
    private T[] array;
    private int size = 0;
    private int capacity = 0;
    private int  head = 0;
    private int tail = 0;
    private CountingSemaphore producer;
    private CountingSemaphore consumer;
    // protect the code between the two semaphore;
    private CountingSemaphore binary;

    public  BlockingQueue(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
        producer = new CountingSemaphore(capacity, 0);
        consumer = new CountingSemaphore(capacity, capacity);
        binary = new CountingSemaphore(1, 0);
    }

    /* This one implementation using the mutex and synchronized object as many as thread can  wait on the resources.
    public void enqueue(T item) throws InterruptedException {
        synchronized (lock) {
            while (size == capacity) {
                lock.wait();
            }
            if(tail ==  capacity) {
                tail = 0;
            }
            array[tail] = item;
            tail++;
            size++;
            lock.notifyAll();
        }
    }

    public T dequeue() throws InterruptedException {
        T item  = null;
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
            }
            if(head == capacity) {
                head = 0;
            }
            item = array[head];
            array[head] = null;
            head++;
            size--;
            lock.notifyAll();
            return item;
        }

    }*/

    // bounded buffer can be implemented using the shemaphore equal to buffer  size and  in java there is no limit no realse cal
    // release will  wait if no premit is given out. aquire will wait if all premit is given  out.

    public void enqueue(T item) throws InterruptedException {
        producer.acquire();
        //System.out.printf("Acquired  in producer");
        binary.acquire();
        if(tail == capacity) {
            tail = 0;
        }
        array[tail] = item;
        size++;
        tail++;
        binary.release();
        consumer.release();
    }

    public T dequeue() throws InterruptedException {
        T item = null;
        consumer.acquire();
        binary.acquire();
        if(head == capacity) {
            head = 0;
        }
        item = array[head];
        array[head] = null;
        head++;
        size--;
        binary.release();
        producer.release();
        return item;
    }

}
