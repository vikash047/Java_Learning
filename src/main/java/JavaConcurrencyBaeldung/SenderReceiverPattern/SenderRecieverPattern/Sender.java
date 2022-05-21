package JavaConcurrencyBaeldung.SenderReceiverPattern.SenderRecieverPattern;

import java.util.concurrent.ThreadLocalRandom;

public class Sender implements Runnable {
    private Data data;

    public Sender(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        String packet[] = {
                "vikash send the data",
                "rani send the data",
                "round robbin excellent",
                "End"
        };
        for(String str : packet) {
            data.send(str);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
