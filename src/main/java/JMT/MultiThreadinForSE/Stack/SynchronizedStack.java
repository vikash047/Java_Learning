package JMT.MultiThreadinForSE.Stack;

public class SynchronizedStack {
    private StackNode<Integer> head;
    private StackNode<Integer> tail;
    private int size;
    public synchronized void push(Integer item) {
        if(head == null) {
            head = new StackNode<>(item);
            tail = head;
        } else {
            tail.setNext(new StackNode<>(item));
            tail = tail.getNext();
        }
        size++;
        System.out.println("size " + size);
    }
    public synchronized Integer pop() {
        if(head == null) {
            return null;
        }
        Integer value = head.getItem();
        head = head.getNext();
        System.out.println("size decreasing " + size);
        size--;
        return value;
    }
}
