package GrokingJavaConcurrency.ReadWriteLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteLock {
    private int reader = 0;
    private volatile Boolean isWriter = false;
    private final ReentrantLock lock  = new ReentrantLock();
    private final Condition writerWaiting = lock.newCondition();
    private final Condition readerWaiting = lock.newCondition();
    public void acquireReadLock() throws InterruptedException {
        lock.lock();
        try {
            if(isWriter) {
                readerWaiting.await();
            }
            reader++;
        } finally {
            lock.unlock();
        }
    }
    public void releaseReadLock() {
        lock.lock();
        try {
            reader--;
            if(reader == 0) {
                writerWaiting.signal();
            } else {
                readerWaiting.signal();
            }
        } finally {
            lock.unlock();
        }
    }
    public void acquireWriteLock() throws InterruptedException {
        lock.lock();
        try {
            while (isWriter || reader != 0) {
                writerWaiting.await();
            }
            isWriter = true;
        } finally {
            lock.unlock();
        }
    }
    public void releaseWriteLock() {
        lock.lock();
        try {
            isWriter = false;
            readerWaiting.signal();
        } finally {
            lock.unlock();
        }
    }
}
