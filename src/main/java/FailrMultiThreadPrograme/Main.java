package FailrMultiThreadPrograme;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

public class Main {
    static int limit = 100_000_000;
    static AtomicInteger totalPrime = new AtomicInteger(0);
    static AtomicInteger number = new AtomicInteger(0);
    public static void checkPrime(int x) {
        if((x&1) == 0) {
            return;
        }
        for(int i = 3; i <= Math.sqrt(x); i++) {
            if(x%i == 0) {
                return;
            }
        }
        totalPrime.incrementAndGet();
    }

    public static void calculatePrime(int st, int end) {
        Instant start = Instant.now();
        for(int i = st; i <= end; i++) {
            checkPrime(i);
        }
        Instant finish = Instant.now();
        System.out.println(" duration of " + Thread.currentThread().getName() + " " + Duration.between(start, finish).toMillis());
    }

    public static void calculatePrime() {
        Instant start = Instant.now();
        int local = 0;
        while (local <= limit) {
            int current = number.incrementAndGet();
            if(current > limit) break;
            checkPrime(current);
            local++;
        }
        Instant finish = Instant.now();
        System.out.println(" duration of " + Thread.currentThread().getName() + "-" + Duration.between(start, finish).toMillis());
    }

    static class CustomThreadFactory implements ThreadFactory {
        String name;
        AtomicInteger sequence = new AtomicInteger(0);

        public CustomThreadFactory(String name) {
            this.name = name;
        }
        @Override
        public Thread newThread(Runnable r) {
            return Thread.ofVirtual().name(name+ "-"+ sequence.incrementAndGet()).factory().newThread(r);
        }
    }
    public static void main(String[] args) {
        int curr = 10;
        int split = limit/curr;
        ExecutorService[] executors = new ExecutorService[curr];
        var factory = new CustomThreadFactory("UserThread");
        for(int i = 0; i < curr; i++) {
            executors[i] = newSingleThreadExecutor(factory);
        }
        int st = 0;
        List<CompletableFuture<Void>> task = new ArrayList<>();
        for(int i = 0; i < curr; i++) {
           // int finalSt = st;
            task.add(CompletableFuture.runAsync(() -> calculatePrime(), executors[i]));
           // st += split + 1;
        }
        task.forEach(CompletableFuture::join);
        System.out.println(totalPrime.get());
    }
}
