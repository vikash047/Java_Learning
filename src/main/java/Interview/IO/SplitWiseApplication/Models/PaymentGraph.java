package Interview.IO.SplitWiseApplication.Models;

import java.util.Map;

public class PaymentGraph {
    private final Map<String, BalanceMap> groupToBalance;

    public PaymentGraph(Map<String, BalanceMap> groupToBalance) {
        this.groupToBalance = groupToBalance;
    }

    public Map<String, BalanceMap> getGroupToBalance() {
        return groupToBalance;
    }
}
