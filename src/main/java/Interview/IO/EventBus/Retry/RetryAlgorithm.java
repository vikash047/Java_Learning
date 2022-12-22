package Interview.IO.EventBus.Retry;

import Interview.IO.EventBus.Exceptions.RetryLimitExceedsxception;
import Interview.IO.EventBus.Exceptions.RetryableException;

import java.util.function.Function;

public abstract class RetryAlgorithm<PARAMETERS, RESULT> {
    private final int maxAttempts;
    private final Function<Integer, Long> retryTimeCalculator;

    protected RetryAlgorithm(int maxAttempts, Function<Integer, Long> retryTimeCalculator) {
        this.maxAttempts = maxAttempts;
        this.retryTimeCalculator = retryTimeCalculator;
    }
    public RESULT attempt(Function<PARAMETERS, RESULT> task, PARAMETERS parameters, int attempt) {
        try {
            return task.apply(parameters);
        } catch (Exception e) {
            if(e.getCause() instanceof RetryableException) {
                if(attempt == maxAttempts) {
                    throw new RetryLimitExceedsxception();
                }
                RESULT result = attempt(task, parameters, attempt + 1);
                try {
                    Thread.sleep(retryTimeCalculator.apply(attempt));
                    return result;
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                throw new RuntimeException();
            }
        }
    }
}
