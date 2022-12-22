package Interview.IO.SplitWiseApplication.Services;

import Interview.IO.SplitWiseApplication.Models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupService {

    private static final Amount NIL = new Amount(Currency.USD, 0l);
    private final ExpensesService expensesService;
    private final Map<String, CloseGroup> groupIdToGroup;

    public GroupService(ExpensesService expensesService) {
        this.expensesService = expensesService;
        groupIdToGroup = new HashMap<>();
    }

    public PaymentGraph getPaymentGraph(final String groupId, final String userId) throws IllegalAccessException {
        return expensesService.getPaymentGraph(getBalance(groupId, userId));
    }

    private BalanceMap getBalance(String groupId, String userId) throws IllegalAccessException {
        if(!groupIdToGroup.get(groupId).getUsers().contains(userId)) {
            throw new IllegalAccessException();
        }
        return sumExpenses(expensesService.getGroupExpense(groupId));
    }

    private BalanceMap sumExpenses(List<Expense> groupExpense) {
        Map<String, Amount> result = new HashMap<>();
        for(Expense expense : groupExpense) {
            for(var bal : expense.getUserExpense().getBalanceMap().entrySet()) {
                final var user = bal.getKey();
                final var amount = bal.getValue();
                result.put(user, result.getOrDefault(user, NIL).addAmount(amount.getValue()));
            }
        }
        return new BalanceMap(result);
    }
}
