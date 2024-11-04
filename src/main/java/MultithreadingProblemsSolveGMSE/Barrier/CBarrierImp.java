package MultithreadingProblemsSolveGMSE.Barrier;

public class CBarrierImp implements CBarrier{
    private int total;
    private int cnt;
    private int released;
    public CBarrierImp(int total) {
        this.total = total;
        this.cnt = 0;
        this.released = 0;
    }
    @Override
    public synchronized void await() throws InterruptedException {
        while (cnt == total) wait();
        cnt++;
        if(cnt == total) {
            notifyAll();
            released = cnt;
        } else {
            while (cnt < total)wait();
        }
        released--;
        if(released == 0) {
            cnt = 0;
            notifyAll();
        }
    }
}
