package JavaConcurrencyAndThreading;

public class SyncEx extends Executor {
    @Override
    public void asyncronousexecution(Callback cb) throws InterruptedException {
        Object signal = new Object();
        final Boolean[] isDone = new Boolean[1];
        isDone[0] = false;
        super.asyncronousexecution(() -> {
            cb.done();
            System.out.println("Done execution ...");
            synchronized (signal) {
                signal.notify();
                System.out.println("Done notification");
                isDone[0] = true;
                System.out.println("Made variable to true..");
            }
        });
        synchronized (signal) {
            while (!isDone[0]) {
                signal.wait();
                System.out.println("Wakeup again ...");
            }
        }
    }
}
