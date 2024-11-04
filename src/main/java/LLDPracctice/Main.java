package LLDPracctice;

public class Main {
    class A {
        public void print() {
            System.out.println("Hello");
        }
    }
    public static void main(String[] args) {
        var obj = new Main();
        var inner = obj.new A();
        inner.print();
    }
}
