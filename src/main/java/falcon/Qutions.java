package falcon;

import java.util.ArrayList;
import java.util.List;

public class Qutions {
    static int maxPeak(List<Integer> input) {
        int n = input.size();
        if(n < 3) {
            return 0;
        }
        int ans = 0;
        int prev = input.get(0);
        int currlen =1;
        boolean f = false;
        for(int i = 1; i < n; i++) {
            int curr = input.get(i);
            if(curr > prev && !f) {
                currlen++;
            } else if(curr == prev) {
                f = false;
                currlen = 1;
            } else if(curr > prev && f) {
                ans = Math.max(ans, currlen);
                currlen = 2;
                f = false;
            } else if(curr < prev && !f) {
                currlen++;
                if(currlen >= 3)
                    ans = Math.max(ans, currlen);
                f = true;
            } else {
                currlen++;
            }
            prev = curr;
        }
        return ans;
    }

/*
// Input:
// [2, 0, 0, 3, 0],
// [2, 0, 1, 0, 0],
// [0, 0, 1, 0, 1],
// [1, 0, 1, 0, 1],
// [1, 0, 1, 1, 0]

id = 4
n m
[m*m]

O(n*m)
dfs (n*m)
 */
// Output:
// [2, 1, 5, 2, 2]

// Input:
// [1]

// Output:
// [1]

    static int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    static boolean isValidMove(int x, int y, int n, int m) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }
    static int dfs(int x, int y, int[][] input, int id, int n, int m) {
        input[x][y] = id;
        int cnt = 1;
        for(int i = 0; i < 4; i++) {
            int nx = x + dir[i][0];
            int ny = y + dir[i][1];
            if(isValidMove(nx, ny, n, m) && input[nx][ny] == 1) {
                cnt += dfs(nx, ny, input, id, n, m);
            }
        }
        return cnt;
    }
    static List<Integer> allRiverSize(int[][] input) {
        int n = input.length;
        if(n == 0) {
            return null;
        }
        int m = input[0].length;
        int id = 2;
        //System.out.println(n + "  " + m);
        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                //System.out.println("Hello " + input[i][j]);
                if(input[i][j] == 1) {
                    //System.out.println("Hello ");
                   int ret = dfs(i, j, input, id, n, m);

                   id++;
                   ans.add(ret);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        //List<Integer> input = Arrays.asList(1, 3, 2);
        int[][] input = {{1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0},
                {1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0},
                {0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0},
                {1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1}};
        System.out.println(allRiverSize(input));
    }
}
