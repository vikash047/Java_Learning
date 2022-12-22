package JavaConcurrencyBaeldung.SenderReceiverPattern.Mutex;

import JavaConcurrencyBaeldung.SenderReceiverPattern.CompleteAbleFutureInJava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFutureInjava {
    public Future<String> calculateAsync() {
        CompletableFuture<String> future = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
           try {
               Thread.sleep(400);
               future.complete("Hello");
           } catch (InterruptedException ex) {
               ex.printStackTrace();
           }
        });
        return  future;
    }

    public void TestExample() throws ExecutionException, InterruptedException {
        Future<String> fr = calculateAsync();
        // Blocking on the future
        String res = fr.get();
        // if we know the result already we can use static one. which future call did not do blocking.
        fr = CompletableFuture.completedFuture("Hello");

        // but we have used the submit method as runnable we can use the supplier which non arg function and return as type.
        // for that we can use runAsync and supplyAsync method of completable future.
        fr = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture.runAsync(() -> {
            System.out.println("Hello");
        });
        // pass method and only do not return to future chain use thenAccept
        CompletableFuture<Void> voidCompletableFuture = new CompletableFuture<>();
        CompletableFuture<Void> vfr = voidCompletableFuture.thenAccept((s) -> System.out.println("Hello" + s));
        // if you do not want to pass the parameter as well no return type use thenRun
        vfr = voidCompletableFuture.thenRun(() -> {
            System.out.println("Hello");
        });
        //Compose is strength of comptable futures
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
        // pass two future use both result in flat map
        stringCompletableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> "World"), (s1, s2) -> s1 + s2);
        // then only use result of two futures
        CompletableFuture<Object> handle = CompletableFuture.supplyAsync(() -> "Hello").thenAcceptBoth(CompletableFuture.supplyAsync(() -> "World"),
                (s1, s2) -> System.out.println(s1 + s2)).handle((s, t) -> {
            return s != null ? "Hello staranger" : t;
        });


    }

}
