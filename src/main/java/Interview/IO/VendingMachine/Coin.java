package Interview.IO.VendingMachine;

public enum Coin {
    PENNY(1), NICKLE(5), DIME(10), QUATER(25);
    private int deno;

    private Coin(int d) {
        this.deno = d;
    }

    public int getDeno() {
        return deno;
    }
}
