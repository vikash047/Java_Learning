package JavaConcurrencyBaeldung.SenderReceiverPattern.CountDownLatch;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WaitingWorker implements Runnable{
    private List<String> output;
    private CountDownLatch readThread;
    private CountDownLatch blockingThread;
    private CountDownLatch allFinish;

    public WaitingWorker(List<String> output, CountDownLatch readThread, CountDownLatch blockingThread, CountDownLatch allFinish) {
        this.output = output;
        this.readThread = readThread;
        this.blockingThread = blockingThread;
        this.allFinish = allFinish;
    }

    @Override
    public void run() {
        readThread.countDown();
        try {
            blockingThread.await();
            output.add("Work done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            allFinish.countDown();
        }
    }
}
