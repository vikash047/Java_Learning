package MultithreadingProblemsSolveGMSE.TokenBucket;

public interface Token {
    void getToken() throws InterruptedException;
}
