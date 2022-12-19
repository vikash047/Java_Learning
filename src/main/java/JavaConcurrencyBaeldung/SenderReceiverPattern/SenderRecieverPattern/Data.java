package JavaConcurrencyBaeldung.SenderReceiverPattern.SenderRecieverPattern;

public class Data {
    private String data;

    private Boolean transfer = true;

    public synchronized String receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
            }
        }
        transfer = true;
        String packetReturned = data;
        notifyAll();
        return packetReturned;
    }

    public synchronized void send(String packet) {
        // spurious wake up.
        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
            }
        }
        transfer = false;
        this.data = packet;
        notifyAll();
    }

}
