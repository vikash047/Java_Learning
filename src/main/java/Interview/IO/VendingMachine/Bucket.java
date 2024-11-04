package Interview.IO.VendingMachine;

public class Bucket<U, V> {
    private U first;
    private V second;

    public Bucket(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}
