package CodingQuestions;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QuestionTestVM {
    class Solution {
        private class Data {
            int id;
            int end;
            int p;
            public Data(int id, int end, int p) {
                this.id = id;
                this.end = end;
                this.p = p;
            }
        }
        int getNext(List<Data> jobs, int d, int i) {
            int l = i;
            int r = jobs.size() - 1;
            int ans = jobs.size();
            while(l <= r) {
                int m = (l + r) >> 1;
                if(jobs.get(m).end >= d) {
                    ans = m;
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            return ans;
        }
        int helper(List<Data> jobs, int index, int d) {
            if(index >= jobs.size()) {
                return 0;
            }
            int ans = 0;
            for(int i = index; i < jobs.size(); i++) {
                int next = getNext(jobs, d + 1, i + 1);
                ans = Math.max(ans, helper(jobs, next, d + 1) + jobs.get(i).p);
            }
            return ans;
        }
        int maxProfit(List<Integer> j, List<Integer> p) {
            int n = p.size();
            List<Data> jobs = new ArrayList<>();
            for(int i = 0; i < n; i++) {
                jobs.add(new Data(i, j.get(i), p.get(i)));
            }
            Collections.sort(jobs, new Comparator<Data>() {
                public int compare(Data a, Data b) {
                    if(a.end == b.end) {
                        return a.p - b.p;
                    }
                    return a.end - b.end;
                }
            });
            return helper(jobs, 0, 1);
        }
    }

    @Test
    public void test() {
        List<Integer> jobs = Arrays.asList(4, 1, 1, 1, 1, 1);
        List<Integer> p = Arrays.asList(50, 10, 30, 40, 70, 80);
        Solution solution = new Solution();
        BlockingQueue<Integer> pq = new ArrayBlockingQueue<>(10, true);
        var res = solution.maxProfit(jobs, p);
        System.out.println(res);
    }

    class Solution2 {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> output = new ArrayList();
            output.add(new ArrayList<Integer>());

            for (int num : nums) {
                List<List<Integer>> newSubsets = new ArrayList();
                for (List<Integer> curr : output) {
                    newSubsets.add(new ArrayList<Integer>(curr){
                        {add(num); add(2);}
                    });
                }
                for (List<Integer> curr : newSubsets) {
                    output.add(curr);
                }
            }
            return output;
        }
    }
}
