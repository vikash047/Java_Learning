package CodingQuestions.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FindStringInDoc {
    class Node {
        Set<Integer> docIds;
        Map<Character, Node> children;
    }

    class Trie {
        private final Node root;

        Trie() {
            root = new Node();
        }
        public void insert(String str, Integer docId) {
            Node temp = root;
            for(var e : str.toCharArray()) {
                temp.children.putIfAbsent(e, new Node());
                temp = temp.children.get(e);
            }
            temp.docIds.add(docId);
        }

        public List<Integer> search(String str) {
            Node temp = root;
            List<Integer> result = new ArrayList<>();
            for(var e : str.toCharArray()) {
                if(!temp.children.containsKey(e)) {
                    return result;
                }
                temp = temp.children.get(e);
            }
            return new ArrayList<>(temp.docIds);
        }
    }

    public void add(List<String> docs) {

    }
}
