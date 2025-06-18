package JMT.MultiThreadinForSE.Stack;

import java.util.concurrent.atomic.AtomicInteger;

public class CASBasedStack {
    private SimulatedCompareAndSwap<StackNode<Integer>> top = new SimulatedCompareAndSwap<>(null);
    private AtomicInteger size = new AtomicInteger(0);
    public void push(Integer item) {
        StackNode<Integer> oldHead;
        StackNode<Integer> newHead;
        do {
            oldHead = top.getValue();
            newHead = new StackNode<>(item);
            newHead.setNext(oldHead);
        } while (!top.comapreAndSwap(oldHead, newHead));
        size.incrementAndGet();
        //System.out.println("inserted value " + item);
    }

    public Integer pop() {
        StackNode<Integer> returnValue;
        StackNode<Integer> nextNode;
        do {
            returnValue = top.getValue();
            if(returnValue == null) {
                System.out.println(" size is " + size.decrementAndGet() + " returned value null");
                return null;
            }
            nextNode = returnValue.getNext();
        } while (!top.comapreAndSwap(returnValue, nextNode));
        System.out.println("size of the stack" + size.decrementAndGet());
        return returnValue.getItem();
    }
}
