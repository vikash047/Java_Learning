package FairMatic.Models;

public class Orders {
    private int order_id;
    private int user_id;
    private String eval_set;
    private int order_number;
    private int order_dow;
    private int order_hour_of_day;
    private double days_since_prior_order;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEval_set() {
        return eval_set;
    }

    public void setEval_set(String eval_set) {
        this.eval_set = eval_set;
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public int getOrder_dow() {
        return order_dow;
    }

    public void setOrder_dow(int order_dow) {
        this.order_dow = order_dow;
    }

    public int getOrder_hour_of_day() {
        return order_hour_of_day;
    }

    public void setOrder_hour_of_day(int order_hour_of_day) {
        this.order_hour_of_day = order_hour_of_day;
    }

    public double getDays_since_prior_order() {
        return days_since_prior_order;
    }

    public void setDays_since_prior_order(double days_since_prior_order) {
        this.days_since_prior_order = days_since_prior_order;
    }
}
