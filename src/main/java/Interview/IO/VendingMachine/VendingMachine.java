package Interview.IO.VendingMachine;

import java.util.List;

public interface VendingMachine {

    double selectItemAndGetPrice(Item item) throws SoldOutException;
    void insertCoin(Coin coin);
    List<Coin> refund();

    Bucket<Item, List<Coin>> collectItemAndChange();
    void reset();

}
