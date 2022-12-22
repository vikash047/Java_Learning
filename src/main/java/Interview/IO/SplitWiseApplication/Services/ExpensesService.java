package Interview.IO.SplitWiseApplication.Services;

import Interview.IO.SplitWiseApplication.Models.*;
import Interview.IO.SplitWiseApplication.Models.Currency;

import java.util.*;

public class ExpensesService {
    private final Map<String, List<Expense>> groupExpense;

    public ExpensesService() {
        groupExpense = new HashMap<>();
    }

    public PaymentGraph getPaymentGraph(BalanceMap balance) {
        final Comparator<Map.Entry<String, Amount>> comparator = Comparator.comparingDouble(userBal -> userBal.getValue().getValue());
        final PriorityQueue<Map.Entry<String, Amount>>
                positive = new PriorityQueue<>(comparator.reversed()),
                negative = new PriorityQueue<>(comparator);
        for(var bal : balance.getBalanceMap().entrySet()) {
            if(bal.getValue().getValue() > 0) {
                positive.offer(bal);
            } else {
                negative.offer(bal);
            }
        }
        final var graph = new HashMap<String, BalanceMap>();
        while(!positive.isEmpty()) {
            var entryP = positive.poll();
            var entryN = negative.poll();
            long ap = entryP.getValue().getValue();
            long an = -entryN.getValue().getValue();
            graph.putIfAbsent(entryP.getKey(), new BalanceMap())
                    .getBalanceMap().put(entryN.getKey(), new Amount(Currency.USD, Math.min(ap, an)));
            Long rem = ap - an;
            if(rem > 0) {
                positive.offer(new AbstractMap.SimpleEntry<>(entryP.getKey(), new Amount(Currency.USD, rem)));
            } else if(rem < 0){
                negative.offer(new AbstractMap.SimpleEntry<>(entryN.getKey(), new Amount(Currency.USD, rem)));
            }
        }
        return new PaymentGraph(graph);
    }

    public void addExpense(Expense expense) {
        groupExpense.putIfAbsent(expense.getGroupId(), new ArrayList<>());
        groupExpense.get(expense.getGroupId()).add(expense);
    }

    public List<Expense> getGroupExpense(String groupId) {
        return groupExpense.getOrDefault(groupId, new ArrayList<>());
    }
}
