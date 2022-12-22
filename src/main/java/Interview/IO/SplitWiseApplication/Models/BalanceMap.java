package Interview.IO.SplitWiseApplication.Models;

import java.util.HashMap;
import java.util.Map;

public class BalanceMap {
    private final Map<String, Amount> balanceMap;

    public BalanceMap() {
        balanceMap = new HashMap<>();
    }

    public BalanceMap(Map<String, Amount> balanceMap) {
        this.balanceMap = balanceMap;
    }

    public Map<String, Amount> getBalanceMap() {
        return balanceMap;
    }

    @Override
    public String toString() {
        return "BalanceMap{" +
                "balanceMap=" + balanceMap +
                '}';
    }
}