package JavaConcurrencyAndThreading;

import java.util.concurrent.CountDownLatch;

public class OrderPrintingUsingCountDownLatch {
    private CountDownLatch latch1;
    private CountDownLatch latch2;
    public OrderPrintingUsingCountDownLatch() {
        latch1 = new CountDownLatch(1);
        latch2 = new CountDownLatch(1);
    }

    public void printFirst() throws InterruptedException {
        System.out.println("Print First");
        latch1.countDown();
    }

    public void printSecond() throws InterruptedException {
        latch1.await();
        System.out.println("Second Print");
        latch2.countDown();
    }

    public void printThird() throws InterruptedException {
        latch2.await();
        System.out.println("Third Print");
    }
}
