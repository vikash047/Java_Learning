package JMT.MultiThreadinForSE.RateLimiter;

public class RateLimiter {
    int maxTokens;
    long lastTime = System.currentTimeMillis();
    int possibleTokens;
    public RateLimiter(int tokens) {
        this.maxTokens = tokens;
        this.possibleTokens = 1;
        var t = new Thread(() -> tokenGenerator());
        t.setDaemon(true);
        t.setName("TokenGenerator");
        t.start();
    }
    private void tokenGenerator()
    {
        while (true) {
            synchronized (this) {
                if (possibleTokens < maxTokens) {
                    possibleTokens++;
                }
                notify();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }

    public void getToken() throws InterruptedException {
        synchronized (this) {
            while (possibleTokens == 0) {
                this.wait();
            }
            possibleTokens--;
        }
    }
}
