package Interview.IO.EventBus.Util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

public class KeyedExecutor<KEY> {
    private final Executor[] executorServices;

    public KeyedExecutor(int threads) {
        this.executorServices = new Executor[threads];
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
