package Interview.IO.RateLimiter;

import Interview.IO.RateLimiter.Models.Client;
import Interview.IO.RateLimiter.Models.ClientId;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterTest {

    @Test
    void isAllow() throws ExecutionException, InterruptedException {
        RateLimiter rateLimiter = new RateLimiter();
        rateLimiter.registerClient(new Client(new ClientId("1"), "client", 100)).toCompletableFuture().get();
        int cntf = 0;
        int cnts = 0;
        Instant start = Instant.now();
        for(int i = 0; i < 200; i++) {
            if(rateLimiter.isAllow(new ClientId("1"))) {
                cnts++;
            } else {
                cntf++;
            }
            Thread.sleep(4);
        }
        Instant finish = Instant.now();
        System.out.println("success " + cnts + " failure " + cntf + " time " + Duration.between(start, finish).toMillis());

    }
}