package QuestionInjava;

import java.util.Objects;

public class GoogleAksedMapImpl<U> implements GoogleAskedMap<U> {
    class Node {
        U item;
        int level;
        Node next, prev;

        public Node(U item) {
            this.item = item;
            next = prev = null;
            level = 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return item.equals(node.item);
        }

        @Override
        public int hashCode() {
            return Objects.hash(item);
        }
    }
    private Node[] arr;
    private int size = 16;
    private int pow = 4;
    private int level, used;
    public GoogleAksedMapImpl() {
        arr = (Node[]) new Object[size];
        level = 0;
        used = 0;
    }

    private void resize() {
        if(used >= size*.08 || level > pow) {
            pow++;
            used = 0;
            int newSize = (int)Math.pow(2, pow);
            Node[] temp = (Node[]) new Object[newSize];
            for(int i = 0; i < size; i++) {
                if(arr[i] != null) {
                    Node root = arr[i];
                    while(root != null) {
                        int index = Objects.hash(root)%newSize;
                        root.prev = null;
                        root.level = 1;
                        if(temp[index] != null) {
                            root.next = temp[index];
                            root.level = temp[index].level + 1;
                            temp[index].prev = root;
                            temp[index] = root;
                            level = Math.max(level, root.level);
                        } else {
                            temp[index] = root;
                            root.level = 1;
                            level = Math.max(level, 1);
                            used++;
                        }
                        root = root.next;
                    }
                }
            }
            arr = temp;
            size = newSize;
        }
    }
    @Override
    public void add(U item) {
        resize();
        Node temp = new Node(item);
        int index = Objects.hash(temp)%size;
        if(arr[index] != null) {
            temp.next = arr[index];
            arr[index].prev = temp;
            temp.level = arr[index].level + 1;
            arr[index] = temp;
            level = Math.max(level, arr[index].level);
        } else {
            arr[index] = temp;
            level = Math.max(level, 1);
            used++;
        }
    }

    @Override
    public boolean remove(U item) {
        Node temp = new Node(item);
        temp.level = 0;
        int index = Objects.hash(temp)%size;
        if(arr[index] == null) {
            return false;
        }
        Node root = arr[index];
        Node head = root;
        while (root != null) {
            if(Objects.equals(temp, root)) {
                temp = root;
                if(root.prev != null) {
                    root.prev.next = root.next;
                }
                if(root.next != null) {
                    root.next.prev = root.prev;
                }
            }
        }
        if(temp.level == 0) return false;
        if(temp.level == head.level) {
            arr[index] = temp.next;
            if (temp.next == null) used--;
        }
        while (root.prev != null) {
            root.prev.level = root.prev.level - 1;
            root = root.prev;
        }
        return true;
    }

    @Override
    public boolean isExist(U item) {
        Node temp = new Node(item);
        int index = Objects.hash(temp)%size;
        if(arr[index] == null) return false;
        Node root = arr[index];
        while (root != null) {
            if(Objects.equals(temp, root)) {
                return true;
            }
        }
        return false;
    }
}
