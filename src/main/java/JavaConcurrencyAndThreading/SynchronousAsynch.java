package JavaConcurrencyAndThreading;

public class SynchronousAsynch {
    public static void main(String[] args) throws InterruptedException {
        SyncEx ex = new SyncEx();
        ex.asyncronousexecution(() -> {
            System.out.println("I am done");
        });
        System.out.println("Main thread exiting...");
    }
}
