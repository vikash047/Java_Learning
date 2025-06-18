package QuestionInjava;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Recently, I appeared for the second round interview for Software Development Apprenticeship position at Google where I was asked a question that I had never seen in my life.

Given a binary tree, return the list of connected components sizes after removing m edges from the binary tree.

Binary Tree :-

                1
            2        3
        4       5  6     7
edges_to_be_removed = [[2, 4], [3, 7]], m = 2;
Connected components after m edges removed = 4, 1-2-5-3-6, 7
Expected output = [1, 5, 1]

I was completely blank throughout the interview and I was thinking of Disjoint Set all the time as I have done the problems on connected components in Graphs only, but it is not correct, Also I have seen this kind of problem for the first time.

Can anyone please provide the solution?
 */
public class QuestionInGoogle {
    private static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }
    List<Integer> result = new ArrayList<>();
    public int helper(TreeNode root, Map<Integer, List<Integer>> nodeToRemove) {
        if(root == null) {
            return 0;
        }
        int val = root.val;
        if (nodeToRemove.containsKey(val)) {
            boolean left = false, right = false;
            for (var node : nodeToRemove.get(val)) {
                if (root.left != null && root.left.val == node) {
                    result.add(helper(root.left, nodeToRemove));
                    left = true;
                }
                if (root.right != null && root.right.val == node) {
                    result.add(helper(root.right, nodeToRemove));
                    right = true;
                }
            }
            int count = 1;
            if(!left) {
                count += helper(root.left, nodeToRemove);
            }
            if(!right) {
                count += helper(root.right, nodeToRemove);
            }
            return count;
        } else {
            return 1 + helper(root.left, nodeToRemove) + helper(root.right, nodeToRemove);
        }
    }
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(7);
        Map<Integer, List<Integer>> remove = new HashMap<>();
        remove.put(2, new ArrayList<>());
        remove.get(2).add(4);
        remove.put(3, new ArrayList<>());
        remove.get(3).add(7);
        QuestionInGoogle google = new QuestionInGoogle();
        google.result = new ArrayList<>();
        int val = google.helper(node, remove);
        google.result.add(val);
        for(var c : google.result) {
            System.out.print(c + ",");
        }
        System.out.println();
    }
}
