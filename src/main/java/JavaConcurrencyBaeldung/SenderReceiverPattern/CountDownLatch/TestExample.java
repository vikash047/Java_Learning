package JavaConcurrencyBaeldung.SenderReceiverPattern.CountDownLatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class TestExample {
    public void test() throws InterruptedException {
        List<String> output = Collections.synchronizedList(new ArrayList<>());
        /*CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new Worker(output, countDownLatch)))
                .limit(5)
                .collect(toList());
        workers.forEach(Thread::start);
        countDownLatch.await();*/
        CountDownLatch ready = new CountDownLatch(5);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(5);
        List<Thread>  workers = Stream.generate(() -> new Thread(new WaitingWorker(output, ready, start, finish)))
                .limit(5)
                .collect(toList());
        workers.forEach(Thread::start);
        ready.await();
        output.add("All are ready");
        start.countDown();
        finish.await();
        output.add("all are done");

        output.forEach(x -> System.out.println(x));
    }
}
