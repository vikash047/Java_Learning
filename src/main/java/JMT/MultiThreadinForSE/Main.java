package JMT.MultiThreadinForSE;

import java.util.concurrent.*;

public class Main {

    Callable<Integer> cb = new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
            return 0;
        }
    };
    Runnable rn = new Runnable() {
        @Override
        public void run() {

        }
    };
    CompletableFuture<Integer> cf = new CompletableFuture<>();
    CompletionStage<Integer> cs = cf;
    CountDownLatch countDownLatch = new CountDownLatch(5);
    CyclicBarrier barrier = new CyclicBarrier(5);
    public Main() {
        countDownLatch.countDown();
    }
}
