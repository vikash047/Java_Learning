package Interview.IO.EventBus.Retry;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class RetryExponentialBackOff<T, U> extends RetryAlgorithm<T, U>{
    @Inject
    protected RetryExponentialBackOff(@Named("exponential-retry-attempts") int maxAttempts) {
        super(maxAttempts, (attempts) -> Double.doubleToLongBits(Math.pow(2, attempts - 1)*1000));
    }
}
