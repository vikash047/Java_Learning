package CodingQuestions;

import java.util.*;

public class OpenLocks {
    private int charToInt(char ch) {
        //System.out.println("character to int " + (ch - '0'));
        return Integer.parseInt(String.valueOf(ch));
    }
    public int openLock(String[] deadends, String target) {
        Set<String> de = new HashSet<>();
        String st = new String("0000");
        for(var s : deadends) {
            if(s.equals(st) || s.equals(target)) {
                return -1;
            }
            de.add(s);
        }
        if(target.equals("0000")) {
            return 0;
        }

        Queue<String> pq = new ArrayDeque<>();
        pq.offer(st);
        int step = 0;
        int[] sa = {1, -1};
        while(!pq.isEmpty()) {
            int sz  = pq.size();
            //System.out.println("step is " + step + " size is " + sz);
            for(int i = 0; i < sz; i++) {
                var tp = pq.poll();
                //System.out.println("poll from queue " + tp);
                if(tp.equals(target)) {
                    return step;
                }
                for(int j = 0; j < tp.length(); j++) {
                    char ch = tp.charAt(j);
                    for(var e : sa) {
                        int val = charToInt(ch) + e;
                        if(val < 0) val += 10;
                        char d = Character.forDigit(val%10, 10);
                        StringBuilder s = new StringBuilder(tp);
                        s.setCharAt(j, d);
                        String temp = s.toString();
                        if(!de.contains(temp)) {
                            pq.offer(temp);
                            de.add(temp);
                        }
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
