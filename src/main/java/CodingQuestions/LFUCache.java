package CodingQuestions;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<KEY, VALUE> {
    public LFUCache(int capacity) {
        this.capacity = capacity;
        record = new HashMap<>();
        tail =  new FreNode(0);
        head = new FreNode(0);
        head.next = tail;
        tail.prev = head;
    }

    class Node {
        KEY key;
        VALUE value;
        Node next, prev;
        FreNode parent;

        public Node(KEY key, VALUE value) {
            this.value = value;
            this.key = key;
            next = prev = null;
            parent = null;
        }
    }

    class FreNode {
        int Freq;
        FreNode prev, next;
        Node head, tail;

        public FreNode(int freq) {
            Freq = freq;
            head = new Node(null, null);
            tail = new Node(null, null);
            head.next = tail;
            tail.prev = head;
            prev = next = null;
        }

        public void addNode(Node node) {
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            node.prev = head;
            node.parent = this;
        }

        public void unlinkNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
            node.parent = null;
        }

        public boolean isEmpty() {
            return head.next == tail;
        }
        public Node evict() {
            Node tmp = tail.prev;
            this.unlinkNode(tmp);
            return tmp;
        }
    }

    private final Map<KEY, Node> record;
    private final FreNode head, tail;
    private final int capacity;
    private int size;

    public void add(KEY key, VALUE value) {
        if(capacity == 0) {
            return;
        }
        if(record.containsKey(key)) {
            Node node = record.get(key);
            node.value = value;
            this.increaseFreq(node);
        } else {
            if(size >= capacity) {
                this.evict();
                size--;
            }
            Node node = new Node(key, value);
            this.increaseFreq(node);
            record.put(key, node);
            size++;
        }
    }

    public VALUE get(KEY key) {
        if(record.containsKey(key)) {
            Node node = record.get(key);
            this.increaseFreq(node);
            return node.value;
        }
        return null;
    }

    private void evict() {
        FreNode node = head.next;
        if(node != tail) {
            Node tmp = node.evict();
            record.remove(tmp.key);
            if(node.isEmpty()) {
                this.unlinkFreqNode(node);
            }
        }
    }

    private void unlinkFreqNode(FreNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
        node.prev = null;
    }

    private void increaseFreq(Node node) {
        FreNode freNode = (node.parent == null) ? head : node.parent;
        FreNode nextFreqNode = freNode.next;
        if(nextFreqNode.Freq != freNode.Freq + 1) {
            nextFreqNode = new FreNode(freNode.Freq + 1);

            nextFreqNode.next = freNode.next;
            freNode.next.prev = nextFreqNode;

            nextFreqNode.prev = freNode;
            freNode.next = nextFreqNode;
        }
        if(head != freNode) {
            freNode.unlinkNode(node);
            if(freNode.isEmpty()) {
                this.unlinkFreqNode(freNode);
            }
        }
        nextFreqNode.addNode(node);
    }
}
