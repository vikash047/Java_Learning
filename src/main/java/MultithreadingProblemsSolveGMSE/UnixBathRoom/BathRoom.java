package MultithreadingProblemsSolveGMSE.UnixBathRoom;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BathRoom implements EnterInRoom{

    private final int totalSeat;
    private final ReentrantLock lock;

    private UsedBy current;

    private int wcnt, mcnt;

    private Condition empty;

    private final Semaphore maxSeat;
    public BathRoom(int totalSeat) {
        this.totalSeat = totalSeat;
        this.lock = new ReentrantLock();
        this.empty = lock.newCondition();
        wcnt = 0;
        mcnt = 0;
        this.current = UsedBy.NONE;
        this.maxSeat = new Semaphore(totalSeat);
    }


    private void performTask() {

    }
    @Override
    public void entryMan() {
        lock.lock();
        try {
            while (current.equals(UsedBy.WOMEN)) {
                empty.await();
            }
            maxSeat.acquire();
            mcnt++;
            current = UsedBy.MEN;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        performTask();
        maxSeat.release();
        lock.lock();
        try {
            mcnt--;
            if(mcnt == 0) {
                current = UsedBy.NONE;
                empty.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void entryWomen() {
        lock.lock();
        try {
            while (current.equals(UsedBy.MEN)) {
                empty.await();
            }
            maxSeat.acquire();
            wcnt++;
            current = UsedBy.WOMEN;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        performTask();
        maxSeat.release();
        lock.lock();
        try {
            wcnt--;
            if(wcnt == 0) {
                current = UsedBy.NONE;
                empty.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    enum UsedBy {
        MEN,
        WOMEN,
        NONE,
    }
}
