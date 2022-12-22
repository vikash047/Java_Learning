package Interview.IO.SplitWiseApplication.Models;

public class Expense {
    private final BalanceMap userExpense;
    private final String groupId;
    private final String title;
    private final String desc;
    private final String imageUrl;

    public Expense(BalanceMap userExpense, String groupId, String title, String desc, String imageUrl) {
        this.userExpense = userExpense;
        this.groupId = groupId;
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }

    public BalanceMap getUserExpense() {
        return userExpense;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "userExpense=" + userExpense +
                ", groupId='" + groupId + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
