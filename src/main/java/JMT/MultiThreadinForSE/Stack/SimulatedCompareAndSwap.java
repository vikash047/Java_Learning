package JMT.MultiThreadinForSE.Stack;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimulatedCompareAndSwap<T> {
    private AtomicBoolean atomicBoolean;
    private volatile T value;

    public SimulatedCompareAndSwap(T value) {
        atomicBoolean = new AtomicBoolean(false);
        this.value = value;
    }

    public boolean comapreAndSwap(T expected, T newValue) {
        if (!atomicBoolean.compareAndSet(false, true)) {
            //System.out.println("returned false");
            return false;
        }
        if(expected == value) {
            this.value = newValue;
        } else {
            atomicBoolean.compareAndSet(true, false);
            return false;
        }
        atomicBoolean.compareAndSet(true, false);
        return true;
    }

    public T getValue() {
        return value;
    }
}
