package MultithreadingProblemsSolveGMSE.TokenBucket;

import java.util.ArrayDeque;
import java.util.Queue;

public class TokenBucketImpl implements Token{

    private final int MAX_TOKEN;
    private int possibleTokens;

    private Queue<Thread> requests;

    public TokenBucketImpl(int maxToken) {
        MAX_TOKEN = maxToken;
        this.requests = new ArrayDeque<>();
    }

    private void generateTokens() {
        while (true) {
            synchronized (this) {
                if(possibleTokens < MAX_TOKEN) {
                    possibleTokens++;
                }
                notify();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void getToken() throws InterruptedException {
        Thread t = Thread.currentThread();
        t.interrupt();
        synchronized (this) {
            while (possibleTokens <= 0) {
                requests.offer(t);
                wait();
            }
            possibleTokens--;
        }
    }


}
