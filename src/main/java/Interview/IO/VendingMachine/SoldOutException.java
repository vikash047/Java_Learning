package Interview.IO.VendingMachine;

public class SoldOutException extends Throwable {
    public SoldOutException(String soldOut) {
        super(soldOut);
    }
}
