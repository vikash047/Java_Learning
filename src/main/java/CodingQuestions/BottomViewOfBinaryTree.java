package CodingQuestions;


/*
                      20
                    /    \
                  8       22
                /   \        \
              5      3       25
                    /   \
                  10    14

5 10 3 14 25
 20 -> 0
 8 -> -1
   3 -> 0
   10 -> -1
   14 -> 1
 5 -> -2

 22 -> 1
 25 -> 2

 Map<key, lastNodeValue>


 user -> rule keyword in the log
 notification to entire to all users -> send the email

 user -> configure rule -> send me notification

 rules -> kafka

 Notification -> channels -> groups

 kakfa -> topics-> groups ->


 Notification -> groups-> configure rules

 Event {
   groupId: "abc123",
   PreviewText: "",
   Subject: "",
   Body: "",
   MediaPart: [{}]
 }

 150k, 200k tps
 -> 10 container, 20 ->

 userId -> per partion -> per minute file -> one object


 */

import java.util.*;

class Node {
    int val;
    Node left, right;
}


public class BottomViewOfBinaryTree {
    public List<Integer> bottomView(Node root) {
        Map<Integer, Integer> view = new HashMap<>();
        Queue<Map.Entry<Node, Integer>> pq = new ArrayDeque<>();
        if(root == null) {
            return null;
        }
        pq.offer(Map.entry(root, 0));
        while(!pq.isEmpty()) {
            int sz = pq.size();
            for(int i = 0; i < sz; i++) {
                var tp = pq.poll();
                view.put(tp.getValue(), tp.getKey().val);
                if(tp.getKey().left != null) {
                    pq.offer(Map.entry(tp.getKey().left, tp.getValue() - 1));
                }
                if(tp.getKey().right != null) {
                    pq.offer(Map.entry(tp.getKey().right, tp.getValue() + 1));
                }
            }
        }
        List<Integer> result = new ArrayList<>(view.values());
        return result;
    }
}
