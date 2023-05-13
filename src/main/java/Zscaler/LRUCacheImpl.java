package Zscaler;

import java.util.Map;

public class LRUCacheImpl implements LRUCache{

    class Node {
        String key;
        String value;
        Node next, prev;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }
    private int capacity;
    private int size;
    private Node head = new Node("0", "0");
    private Node tail = new Node("0", "0");

    public LRUCacheImpl(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        head.next = tail;
        tail.prev = head;
    }

    private Map<String, Node> map;

    /*
       head -> 1 -> 2 -> 3 -> tail;
     */
    private void deleteNode(Node node) {
        node.prev = node.next;
        node.next.prev = node.prev;
    }
    /*

      head -> 1 -> 2 -> tail;
      3
      head -> 1 -> 2 -> 3 -> tail
     */
    private void insert(Node node) {
        tail.prev.next = node;
        node.prev = tail.prev;
        node.next = tail;
        tail.prev = node;
    }
    @Override
    public String get(String key) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            deleteNode(node);
            insert(node);
            return node.value;
        }
        return null;
    }

    @Override
    public void put(String key, String value) {

    }
}
