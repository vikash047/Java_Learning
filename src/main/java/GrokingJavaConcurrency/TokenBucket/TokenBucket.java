package GrokingJavaConcurrency.TokenBucket;

public class TokenBucket {
    private final int MAX_TOKENS;
    private long lastRequestTime = System.currentTimeMillis();
    private long usedTokens;
    public TokenBucket(int maxTokens) {
        this.MAX_TOKENS = maxTokens;
        usedTokens = 0;
    }
    public synchronized void getToken() throws InterruptedException {
        usedTokens = (System.currentTimeMillis() - lastRequestTime)/1000;
        if(usedTokens > MAX_TOKENS) {
            usedTokens = MAX_TOKENS;
        }
        if(usedTokens == 0) {
            Thread.sleep(1000 - (System.currentTimeMillis() - lastRequestTime));
        } else {
            usedTokens--;
        }
        lastRequestTime = System.currentTimeMillis();
        System.out.println("Grating the token " + Thread.currentThread().getName() + " token at " + lastRequestTime/1000);
    }
}
