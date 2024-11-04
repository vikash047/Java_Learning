package LLDPracctice.SnakeLadder;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Integer, Snake> snakeMap = new HashMap<>();
    private Map<Integer, Ladder> ladderMap = new HashMap<>();

    public void addSnake(Snake snake) {
        snakeMap.put(snake.endPos, snake);
    }
    public void addLadder(Ladder ladder) {
        ladderMap.put(ladder.startPos, ladder);
    }

    private int getRow(int pos) {
        return (pos - 1)/10;
    }

    private int getCol(int pos) {
        int row = getRow(pos);
        if(row%2 == 0) {
            return (pos - 1)%10;
        } else {
            return 9 - (pos - 1)%10;
        }
    }
    public int move(int currentPos, int step) {
        int next = currentPos + step;
        if(snakeMap.containsKey(next)) {
            return snakeMap.get(next).endPos;
        } else if(ladderMap.containsKey(next)) {
            return ladderMap.get(next).endPos;
        }
        return next;
    }
}
