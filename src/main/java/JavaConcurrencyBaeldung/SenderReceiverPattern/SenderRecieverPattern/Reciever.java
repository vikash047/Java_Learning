package JavaConcurrencyBaeldung.SenderReceiverPattern.SenderRecieverPattern;

import java.util.concurrent.ThreadLocalRandom;

public class Reciever implements Runnable {
    private Data load;

    public Reciever(Data load) {
        this.load = load;
    }

    @Override
    public void run() {
        for (String msg = load.receive(); !"End".equals(msg); msg = load.receive()) {
            System.out.println("Received message " + msg);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
