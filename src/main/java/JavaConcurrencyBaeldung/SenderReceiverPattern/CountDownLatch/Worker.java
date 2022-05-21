package JavaConcurrencyBaeldung.SenderReceiverPattern.CountDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {
    private List<String> output = new ArrayList<>();
    private CountDownLatch countDownLatch;

    public Worker(List<String> output, CountDownLatch countDownLatch) {
        this.output = output;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        output.add("Work is done");
        countDownLatch.countDown();
    }
}
