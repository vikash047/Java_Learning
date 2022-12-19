package JavaConcurrencyBaeldung.SenderReceiverPattern;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompleteAbleFutureInJava {
    public int factorial(int n) {
        int fact = 1;
        for(int i = 1; i < n; i++) {
            fact += fact*i;
        }
        return fact;
    }
    public void runFunction() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> factorial(10));
        while (!completableFuture.isDone()) {
            System.out.println("Task done");
        }
        System.out.println("value " + completableFuture.get());

    }
}
