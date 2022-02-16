package JavaConcurrencyAndThreading;

public class Executor{
    public void asyncronousexecution(Callback cb) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                cb.done();
            }
        });
        t.start();
    }
}

