package Interview.IO.VendingMachine;

import java.util.HashMap;
import java.util.Map;

public class Inventory <T> {
    private Map<T, Integer> inventory = new HashMap<>();

    public int getQuantity(T item) {
        return inventory.getOrDefault(item, 0);
    }
    public void add(T item) {
        inventory.put(item, inventory.getOrDefault(item, 0) + 1);
    }

    public void deduct(T item) {
        inventory.compute(item, (a, b) -> b != null ? b - 1 : null);
    }
    public  boolean hasItem(T item) {
        return inventory.containsKey(item) && inventory.get(item) > 0;
    }

    public void clear() {
        inventory.clear();
    }
    public void put(T item, int q) {
        inventory.put(item, q);
    }
}
