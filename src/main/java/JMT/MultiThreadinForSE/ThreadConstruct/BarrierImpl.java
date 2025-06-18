package JMT.MultiThreadinForSE.ThreadConstruct;

public class BarrierImpl {
    private int count;
    private int released;
    private final int totalAllowed;
    public BarrierImpl(int totalAllowed) {
        this.totalAllowed = totalAllowed;
    }

    public synchronized void await() throws InterruptedException {
       while (count == totalAllowed) wait();
       count++;
       if(count == totalAllowed) {
           notifyAll();
           released = totalAllowed;
       } else {
           while (count < totalAllowed) {
               wait();
           }
       }
       released--;
       if(released == 0) {
           count = 0;
           notifyAll();
       }
    }
}
