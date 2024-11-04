package Interview.IO.EventBus.Util;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class KeyedExecutor<KEY> {
    private final ExecutorService[] executorServices;

    public KeyedExecutor(int threads) {
        this.executorServices = new ExecutorService[threads];
        for(int i = 0; i < threads; i++) {
            this.executorServices[i] = Executors.newSingleThreadExecutor();
        }
    }

    public CompletionStage<Void> getThreadFor(final KEY key, Runnable task) {
        return CompletableFuture.runAsync(task, executorServices[key.hashCode() % executorServices.length]);
    }

    public <U> CompletionStage<U> getThreadFor(final KEY key, Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, executorServices[key.hashCode() % executorServices.length]);
    }

    public <U> CompletionStage<U> getThreadFor(final KEY key, CompletionStage<U> task) {
        return CompletableFuture.supplyAsync(() -> task, executorServices[key.hashCode() % executorServices.length])
                .thenCompose(Function.identity());
    }
}
