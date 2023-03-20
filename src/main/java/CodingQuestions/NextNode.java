package CodingQuestions;

import java.util.*;

public class NextNode {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public Node connect(Node root) {
        final var cmp = new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                return 0;
            }
        };
        int [][] a = new int[2][4];
        Arrays.sort(a, cmp);
        if(root == null) {
            return root;
        }
        StringBuilder s = new StringBuilder("as");
        Deque<int[]> pq = new LinkedList<>();
        List<Integer> lst = new ArrayList<>();
        lst.add(0, 2);
        Node curr = root;
        Node next = curr.left != null ? curr.left : curr.right;
        Node st = next;
        while (next != null) {
            if(curr.left != null && next == curr.left && curr.right != null) {
                next.next = curr.right;
            } else {
                curr = curr.next;
                if(curr != null) {
                    next.next = curr.left == null ? curr.right : curr.left;
                }
            }
            next = next.next;
            if(next == null) {
                while (st != null && st.left == null && st.right == null) {
                    st = st.next;
                }
                if(st != null) {
                    curr = st;
                    next = st.left == null ? st.right : st.left;
                    st = next;
                }
            }
        }
        return root;
    }
}
