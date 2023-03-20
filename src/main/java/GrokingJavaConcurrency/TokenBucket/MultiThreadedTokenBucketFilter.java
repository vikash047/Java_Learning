package GrokingJavaConcurrency.TokenBucket;

public class MultiThreadedTokenBucketFilter {
    private final int MAX_TOKENS;
    private final int ONE_SECOND = 1000;
    private int usedToekns;
    public MultiThreadedTokenBucketFilter(int MAX_TOKENS) {
        this.MAX_TOKENS = MAX_TOKENS;
        this.usedToekns = 0;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                demon();
            }
        });
        t.start();
    }
    private void demon() {
        while (true) {
            synchronized (this) {
                if(usedToekns < MAX_TOKENS) {
                    usedToekns++;
                }
                this.notify();
            }
            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void getToken() throws InterruptedException {
        synchronized (this) {
            while (usedToekns == 0) {
                wait();
            }
            usedToekns--;
        }
        System.out.println("Grating token " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis()/1000);
    }
}
