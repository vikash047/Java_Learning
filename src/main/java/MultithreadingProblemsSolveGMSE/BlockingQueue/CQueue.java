package MultithreadingProblemsSolveGMSE.BlockingQueue;

public interface CQueue<T> {
    void offer(T item);
    T getTop();
}
