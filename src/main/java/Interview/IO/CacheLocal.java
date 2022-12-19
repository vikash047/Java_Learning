package Interview.IO;

import java.util.HashMap;
import java.util.Map;

public class CacheLocal {
    class Data {
        String key;
        String value;
        public Data(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    class Node<T> {
        T data;
        Node next, prev;
        public Node(T data) {
            this.data = data;
            this.next = this.prev = null;
        }
    }

    class DList<T> {
        Node<T> head, tail;
        Node<T> addNode(Node<T> node) {
            if(head == null) {
                head = node;
                tail = head;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = tail.next;
            }
            return tail;
        }
        Node<T> removeLast() {
            if(tail == null) return null;
            Node<T> tmp = tail;
            Node<T> node = tail.prev;
            node.next = null;
            tail = node;
            return tmp;
        }
        void moveFirst(Node<T> node) {
            if(node == null) return;
            Node<T> nodePrev = node.prev;
            Node<T> nodeNext = node.next;
            nodePrev.next = nodeNext;
            nodeNext.prev = nodePrev;
            node.next = head;
            head.prev = node;
            head = node;
        }

        void remove(Node<T> node) {
            if(node == null) return;
            Node<T> nodePrev = node.prev;
            Node<T> nodeNext = node.next;
            nodePrev.next = nodeNext;
            nodeNext.prev = nodePrev;
        }
    }
    DList<Data> lst;
    Map<String, Node<Data>> index;
    int capacity;
    public CacheLocal(int capacity) {
        this.capacity = capacity;
        lst = new DList<>();
        index = new HashMap<>();
    }

    public void put(String key, String value) {
        if(index.containsKey(key)) {
            Node<Data> d = index.get(key);
            d.data = new Data(key, value);
            lst.moveFirst(d);
        } else {
            if (capacity <= index.size()) {
                lst.removeLast();
                index.remove(key);
                System.out.println("Removed last one");
            }
            Node<Data> d = lst.addNode(new Node<>(new Data(key, value)));
            index.put(key, d);
        }
    }

    public String get(String key) {
        if(index.containsKey(key)) {
            return index.get(key).data.value;
        }
        return null;
    }

    public void delete(String key) {
        if(!index.containsKey(key)) {
            return;
        }
        Node d = index.get(key);
        lst.remove(d);
        index.remove(key);
    }

    public static void main(String[] args) {
        CacheLocal local = new CacheLocal(5);
        for(int i = 0; i < 10; i++) {
            local.put(Integer.toString(i), "value is " + Integer.toString(i));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(local.get(Integer.toString(i)));
        }
        local.delete("7");
        System.out.println( "get 7" + local.get("7"));
    }
}
