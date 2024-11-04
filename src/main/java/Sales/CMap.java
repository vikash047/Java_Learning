package Sales;


import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* HashTable/HashMap a libray

void put(k key, V value)
V get(K key);

//boolean containsKey(K key)

void delete(K key)

array [] -> linked list hold (key, value) -> ()
16 fixed

resize

0 -> [1, 2, 3]

0 -> 1
1 -> 2
2 -> 3

*/
public class CMap<K, V> {
    private class Node<K, V> {
        K key;
        V value;
        Node next;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    private int defaultSize = 16;
    private int currentSize;
    private int numberOfObjects = 0;
    private Node[] arr;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private boolean isResizeInProcess = false;

    public CMap() {
        this.currentSize = defaultSize;
        arr = new Node[defaultSize];
    }

    private void resize() {
        writeLock.lock();
        isResizeInProcess = true;
        if(!isResizeNeeded()) {
            writeLock.unlock();
            return;
        }
        try {
            int newSize = 2*currentSize;
            Node[] newArr = new Node[newSize];
            //numberOfObjects = 0;
            for(var list : arr) {
                if(list != null) {
                    Node temp = list;
                    while (temp != null) {
                        int index = Objects.hash(temp.key)%newSize;
                        if(newArr[index] != null) {
                            var head = newArr[index];
                            var next = temp.next;
                            temp.next = head;
                            newArr[index] = temp;
                            temp = next;
                        } else {
                            var next = temp.next;
                            newArr[index] = temp;
                            temp.next = null;
                            temp = next;
                            //numberOfObjects++;
                        }
                    }
                }
            }
            arr = newArr;
            currentSize = newSize;
        } finally {
            writeLock.unlock();
        }

    }

    private boolean isResizeNeeded() {
        return numberOfObjects >= currentSize*0.70;
    }

    private Node isKeyExist(Node temp, K key) {
        while (temp != null) {
            if(temp.key.equals(key)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    void put(K key, V value) {
        if(isResizeNeeded()) {
            resize();
        }
        writeLock.lock();
        try {
            int index = Objects.hash(key)%currentSize;
            Node node = new Node(key, value);

            if(arr[index] == null) {
                arr[index] = node;
                numberOfObjects++;
            } else {
                var temp = arr[index];
                var keyNode = isKeyExist(temp, key);
                if(keyNode == null) {
                    node.next = arr[index];
                    arr[index] = node;
                } else {
                    keyNode.value = value;
                }
            }
        } finally {
            writeLock.unlock();
        }

    }
    V get(K key) {
        int index = Objects.hash(key)%currentSize;
        if(arr[index] == null) {
            return null;
        }
        var temp = arr[index];
        var keyNode = isKeyExist(temp, key);
        if(keyNode != null) {
            return (V)keyNode.value;
        }
        return null;
    }

    private Node delete(Node node, K key) {
        Node prev = null;
        Node head = node;
        boolean f = false;
        while (node != null) {
            if(node.key.equals(key)) {
                f = true;
                if(prev == null) {
                    head = node.next;
                } else {
                    prev.next = node.next;
                }
            }
            prev = node;
            node = node.next;
        }
        return f == true ? head : null;
    }
    boolean delete(K key) {
        int index = Objects.hash(key)%currentSize;
        if(arr[index] == null) {
            return false;
        }
        var temp = arr[index];
        var head = delete(temp, key);
        if(head != null) {
           arr[index] = head;
           return true;
        }
        return false;
    }


}
