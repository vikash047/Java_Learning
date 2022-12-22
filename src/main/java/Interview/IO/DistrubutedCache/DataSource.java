package Interview.IO.DistrubutedCache;

import java.util.concurrent.CompletionStage;

public class DataSource<KEY, VALUE> {
    public CompletionStage<VALUE> load(KEY key) {
        return  null;
    }
    public CompletionStage<Void> persist(KEY key, VALUE value) {
        return null;
    }
}
