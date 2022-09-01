package GrokingJavaConcurrency.TokenBucket;

import JavaConcurrencyAndThreading.MultiThreadedMergeSort;

import java.util.HashSet;
import java.util.Set;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Set<Thread> threads = new HashSet<>();
        final MultiThreadedTokenBucketFilter tokenBucket = new MultiThreadedTokenBucketFilter(5);
        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tokenBucket.getToken();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("Thread_" + (i + 1));
            threads.add(thread);
        }
        for(Thread t : threads) {
            t.start();
        }
        for(Thread t : threads) {
            t.join();
        }
    }
}
