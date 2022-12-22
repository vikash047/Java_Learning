package Interview.IO.EventBus.Retry;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.function.Function;

public class PeriodicRetry<T, U> extends RetryAlgorithm<T, U> {
    @Inject
    protected PeriodicRetry(@Named("periodic-retry-attempts") final int maxAttempts,
                            @Named("periodic-retry-waitInMillis") final long waitInMillis) {
        super(maxAttempts, (__) -> waitInMillis);
    }
}
