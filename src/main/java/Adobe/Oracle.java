package Adobe;

import java.util.*;

public class Oracle {

    private class Data {
        int w;
        String c;
        public Data(int w, String c) {
            this.w = w;
            this.c = c;
        }
    }
    private Map<Integer, List<List<String>>> memo;
    private Stack<Map.Entry<Integer, Integer>> st = new Stack<>();

    private TreeMap<Integer,List<List<String>>> helper(Data[] input, int index) {
        if(index >= input.length) {
            return null;
        }
        TreeMap<Integer, List<List<String>>> result = new TreeMap<>();
        var ret = helper(input, index + 1);
        int w = input[index].w;
        String c = input[index].c;
        if(ret != null) {
            for(var kv : ret.entrySet()) {
                result.putIfAbsent(kv.getKey() + w, new ArrayList<>());
                for(var lst : kv.getValue()) {
                    List<String> temp = new ArrayList<>(lst);
                    temp.add(c);
                    result.get(kv.getKey() + w).add(temp);
                }
            }
            result.putAll(new TreeMap<>(ret));
       }
        result.putIfAbsent(w, new ArrayList<>());
        result.get(w).add(Arrays.asList(c));
        return result;
    }
    public void highestWeight(int[] w, String[] c, int k) {
        memo = new TreeMap<>();
        int n = w.length;
        Data[] kv = new Data[n];
        for(int i = 0; i < n; i++) {
            kv[i] = new Data(w[i], c[i]);
        }
        Arrays.sort(kv, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o2.w - o1.w;
            }
        });
        var ret = helper(kv, 0);
        for(var kp : ret.entrySet()) {
            System.out.println(kp.getKey() + "  values " + Arrays.toString(kp.getValue().toArray()));
        }
    }
    public static void main(String[] args) {
        int[] c = {4, 3, 2, 1};
        String[] w = {"R", "B", "Y", "G"};
        var ob = new Oracle();
        ob.highestWeight(c, w, 6);
    }
}
