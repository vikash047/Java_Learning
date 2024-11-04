package Adobe;


import java.util.HashSet;

/*
[1:02 PM] Ashwin Karollil

Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

[1:02 PM] Ashwin Karollil

Input: board =

[["A","B","C","E"],

["S","E","C","C"],

["A","E","E","E"]],

(0,0) -> (0, 1) ->

word = "ABCCEE"

Output: true
 */
public class Main {

    private boolean isValid(int x, int y, int n, int m) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }
    private int[][] dir = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

    private class Data {
        int x, y;
        public Data(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private char[][] board;
    int n, m;
    private boolean dfs(int x, int y, String w, int index, HashSet<Data> visit) {
        if(index >= w.length()) {
            return true;
        }
        visit.add(new Data(x, y));
        boolean ans = false;
        for(int i = 0; i < 4; i++) {
            int nx = x + dir[i][0];
            int ny = y + dir[i][1];
            if(isValid(nx, ny, n, m) && !visit.contains(new Data(nx, ny)) && board[nx][ny] == w.charAt(index + 1)) {
                ans |= dfs(nx, ny, w, index + 1, visit);
            }
        }
        visit.remove(new Data(x, y));
        return ans;
    }
    private boolean findWordInBoard(char[][] board, String word) {
        this.board = board;
        n = board.length;
        if(n == 0) {
            return false;
        }
        m = board[0].length;
        HashSet<Data> visit = new HashSet<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] == word.charAt(0)) {
                    if(dfs(i, j, word, 0, visit)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
