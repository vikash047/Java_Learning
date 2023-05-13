package FairMatic.Models;

public class Products {
    private int product_id;
    private String product_name;
    private int aisle_id;
    private int department_id;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getAisle_id() {
        return aisle_id;
    }

    public void setAisle_id(int aisle_id) {
        this.aisle_id = aisle_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }
}
