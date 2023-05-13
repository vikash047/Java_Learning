package FairMatic.Models;

public class OrderedProducts {
    private int order_id;
    private int product_id;
    private int add_to_cart_order;
    private int reordered;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAdd_to_cart_order() {
        return add_to_cart_order;
    }

    public void setAdd_to_cart_order(int add_to_cart_order) {
        this.add_to_cart_order = add_to_cart_order;
    }

    public int getReordered() {
        return reordered;
    }

    public void setReordered(int reordered) {
        this.reordered = reordered;
    }
}
