package CodingQuestions.Functions;

import java.util.*;

public class FunctionMatchImpl implements FunctionMatch{

    class Node {
        Map<String, Node> next;
        List<String> repFunctionNames;
        List<String> nonRepFunctionNames;

        public Node() {
            next = new HashMap<>();
            repFunctionNames = new ArrayList<>();
            nonRepFunctionNames = new ArrayList<>();
        }
    }
    class Trie {
        Node root;
        public Trie() {
            root = new Node();
        }

        public void insert(FunctionCustom functionCustom) {
            Node temp = root;
            for(var arg : functionCustom.argType) {
                temp.next.putIfAbsent(arg, new Node());
                temp = temp.next.get(arg);
            }
            if(functionCustom.isRep) {
                if(temp.next.containsKey("*")) {
                    temp.next.get("*").repFunctionNames.add(functionCustom.name);
                } else {
                    Node n = new Node();
                    n.repFunctionNames.add(functionCustom.name);
                    temp.next.put("*", n);
                }
            } else {
                temp.nonRepFunctionNames.add(functionCustom.name);
            }
        }

        public List<String> match(List<String> args) {
            Node temp = root;
            List<String> result = new ArrayList<>();
            boolean[] same = new boolean[args.size()];
            Arrays.fill(same, true);
            for(int i = args.size() - 2; i >= 0; i--) {
                same[i] = (same[i+1] && args.get(i).equals(args.get(i+1)));
            }
            for (int i = 0; i < args.size(); i++) {
                System.out.print(same[i] + ", ");
            }
            System.out.println();
            for(int i = 0; i < args.size(); i++) {
                String arg = args.get(i);
                if(!temp.next.containsKey(arg)) {
                    return result;
                }
                if(i > 0 && same[i-1] && temp.next.containsKey("*")) {
                    //System.out.println(i + " " + allSame(args, i-1, arg));
                    result.addAll(temp.next.get("*").repFunctionNames);
                }
                temp = temp.next.get(arg);
            }
            if(temp.repFunctionNames.size() > 0) {
                result.addAll(temp.repFunctionNames);
            }
            if(temp.nonRepFunctionNames.size() > 0) {
                result.addAll(temp.nonRepFunctionNames);
            }
            if(temp.next.containsKey("*")) {
                result.addAll(temp.next.get("*").repFunctionNames);
            }
            return result;
        }

        private boolean allSame(List<String> args, int index, String arg) {
            while(index < args.size()) {
                if(!args.get(index).equals(arg)) {
                    return false;
                }
                index++;
            }
            return true;
        }
    }
    Trie trie;

    public FunctionMatchImpl() {
        trie = new Trie();
    }

    @Override
    public void register(Set<FunctionCustom> functionSet) {
        functionSet.forEach(x -> trie.insert(x));
    }

    @Override
    public List<String> findMatches(List<String> argumentTypes) {
        return trie.match(argumentTypes);
    }
}
