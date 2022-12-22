package JavaConcurrencyBaeldung.SenderReceiverPattern;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreadsExamples {
    final AtomicInteger atomicInteger = new AtomicInteger();
    Runnable runnable = () -> {
        try {
            Thread t = Thread.currentThread();
            t.sleep(Duration.ofSeconds(1).toMillis());
            System.out.println(t.getThreadGroup() + " " + t.getPriority());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Work Done -- " + atomicInteger.incrementAndGet());
    };

    public static void main(String[] args) throws InterruptedException {
        VirtualThreadsExamples virtualThreadsExamples = new VirtualThreadsExamples();
       Instant start = Instant.now();
        ThreadFactory factory = Thread.ofVirtual().factory();
       try (var executor = Executors.newThreadPerTaskExecutor(factory)) {
           for (var i = 0; i < 10_000; i++) {
               executor.submit(virtualThreadsExamples.runnable);
           }
       }
        Instant finish = Instant.now();
        long time = Duration.between(start, finish).toMillis();
        System.out.println("work done in time ---> " + time);
        /*
        Thread t = Thread.startVirtualThread(() -> {});
        do not create pool for the virtual thread whenever you need thread create new one always. because creation is not expensive.
        virtual thread sometimes needs resources to limited thread then use the semaphore.
        virtual thread can not be make setDeman(false) and priority can not be changed.
        you can not use  suspend, resume, stop used.

        Thread.Builder t = Thread.ofVirtual().name("JVM-Thread");
        Thread t1 = t.start(virtualThreadsExamples.runnable);
        Thread t2 = t.start(virtualThreadsExamples.runnable);
        t1.join();
        t2.join();*/

    }
}
