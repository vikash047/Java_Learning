package MultithreadingProblemsSolveGMSE.TokenBucket;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucket {
    private final int capacity;
    private final int tokensPerSecond;
    private int tokens;
    private Semaphore tokenSemaphore;
    private Queue<Condition> requestQueue;
    private Lock lock;
    private Condition tokenAvailableCondition;
    private Condition empty;

    public TokenBucket(int capacity, int tokensPerSecond) {
        this.capacity = capacity;
        this.tokensPerSecond = tokensPerSecond;
        this.tokens = 0;
        this.tokenSemaphore = new Semaphore(0);
        this.requestQueue = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.tokenAvailableCondition = lock.newCondition();
        this.empty = lock.newCondition();
        startTokenProducer();
        getTokenFIFO();
    }

    public void getToken() throws InterruptedException {
        lock.lock();
        try {
            Condition threadCondition = lock.newCondition(); // Create a new condition for the thread
            requestQueue.offer(threadCondition); // Enqueue the condition signal
            empty.signal(); // Signal token availability
            while (requestQueue.peek() != threadCondition) {
                threadCondition.await(); // Wait until it's the thread's turn
            }
            tokenSemaphore.acquire(); // Wait for a token to be available
        } finally {
            lock.unlock();
        }
    }

    private void startTokenProducer() {
        Thread tokenProducer = new Thread(() -> {
            try {
                while (true) {
                    lock.lock();
                    try {
                        if (tokens < capacity) {
                            tokens++;
                            tokenSemaphore.release(); // Release a token
                            tokenAvailableCondition.signal();
                        }
                    } finally {
                        lock.unlock();
                    }
                    Thread.sleep(1000 / tokensPerSecond);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        tokenProducer.setDaemon(true);
        tokenProducer.start();
    }

    private void getTokenFIFO() {
        Thread t = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (requestQueue.isEmpty()) {
                        empty.await();
                    }
                    while(tokens <= 0) {
                        try {
                            tokenAvailableCondition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Condition condition = requestQueue.poll();
                    tokens--;
                    condition.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
}


