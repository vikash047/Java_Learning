package Interview.IO.VendingMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendingMachineImpl implements VendingMachine {

    private Inventory<Coin> cashInventory = new Inventory<>();
    private Inventory<Item> itemInventory = new Inventory<>();

    private long totalSales;
    private Item currentItem;
    private long currentBalance;

    public VendingMachineImpl() {
        init();
    }

    private void init() {
        for(Coin c : Coin.values()) {
            cashInventory.put(c, 5);
        }
        for(Item i : Item.values()) {
            itemInventory.put(i, 5);
        }
    }
    @Override
    public double selectItemAndGetPrice(Item item) throws SoldOutException {
        if(itemInventory.hasItem(item)) {
            this.currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold out");
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getDeno();
        cashInventory.add(coin);
    }

    @Override
    public List<Coin> refund() {
        return null;
    }

    private List<Coin> getChange(long amount) {
        List<Coin> coins = Collections.EMPTY_LIST;
        if(amount > 0) {
            coins =  new ArrayList<>();
            long balance = amount;
            while (balance > 0) {
                if(balance >= Coin.QUATER.getDeno() && cashInventory.hasItem(Coin.QUATER)) {

                }
            }
        }
        return coins;
    }

    @Override
    public Bucket<Item, List<Coin>> collectItemAndChange() {
        return null;
    }

    @Override
    public void reset() {

    }
}
