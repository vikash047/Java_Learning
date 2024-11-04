package MultithreadingProblemsSolveGMSE.UberRideProblem;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class RiderImpl implements Rider{

    private final ReentrantLock lock;
    private int dcnt;
    private int rcnt;

    private final Semaphore ds = new Semaphore(0);
    private final Semaphore rs = new Semaphore(0);

    private final CyclicBarrier barrier = new CyclicBarrier(4);
    public RiderImpl() {
        lock = new ReentrantLock();
        this.dcnt = 0;
        this.rcnt = 0;
    }


    public void drive() {

    }

    public void seat() {

    }
    @Override
    public void callDemo() {
        lock.lock();
        boolean leader = false;
        try {
            dcnt++;
            if(dcnt == 4) {
                leader = true;
                ds.release(3);
                dcnt -= 4;
            } else if(dcnt == 2 && rcnt >= 2) {
                ds.release(1);
                rs.release(2);
                dcnt -= 2;
                rcnt -= 2;
                leader = true;
            } else {
                lock.unlock();
                ds.acquire();
            }
            seat();
            barrier.await();
            if(leader) {
                drive();
                lock.unlock();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void callRep() {
        lock.lock();
        boolean leader = false;
        try {
            rcnt++;
            if(rcnt == 4) {
                leader = true;
                rs.release(3);
                rcnt -= 4;
            } else if(rcnt == 2 && dcnt >= 2) {
                rs.release(1);
                ds.release(2);
                dcnt -= 2;
                rcnt -= 2;
                leader = true;
            } else {
                lock.unlock();
                rs.acquire();
            }
            seat();
            barrier.await();
            if(leader) {
                drive();
                lock.unlock();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
