package Interview.IO.VendingMachine;

public enum Item {
    COKE("COKE", 25), PEPSI("pepsi", 30), SODA("soda", 30);

    private String name;
    private int price;

    private Item(String name, int p) {
        this.name = name;
        this.price = p;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
