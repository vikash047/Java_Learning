package AtlassianCoding;

import java.util.*;

public class SnakeGameImpl implements SankeGame{
    class Cordinates {
        private int x, y;

        public Cordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
    private List<Cordinates> pq;
    private int step;
    private int n, m;
    public SnakeGameImpl(int n, int m, int x, int y, int length) {
        pq = new ArrayList<>();
        this.step = 0;
        this.n = n;
        this.m = m;
        for(int i = 0; i < length; i++) {
            pq.add(new Cordinates(x, y++));
        }
    }
    @Override
    public void moveSnake(Directions directions) throws Exception {
        switch (directions) {
            case Right -> move(1, 0);
            case Up -> move(0, 1);
            case Down -> move(0, -1);
            case Left -> move(-1, 0);
            default -> throw new Exception("Not valid direction");
        }
    }

    @Override
    public boolean isGameOver() {
        Cordinates head = pq.get(pq.size() - 1);
        return isValid(head) && !hitItSelf(head);
    }

    private void move(int dx, int dy) throws Exception {
        this.step++;
        Cordinates head = pq.get(pq.size() - 1);
        Cordinates nHead = new Cordinates(head.x + dx, head.y + dy);
        if(isValid(nHead) && !hitItSelf(nHead)) {
            pq.add(nHead);
        } else {
            throw new Exception("Game is over");
        }
        if(this.step < 4) {
            pq.remove(0);
        } else {
            this.step = 0;
        }
        printSnake();
    }

    private boolean hitItSelf(Cordinates cordinates) {
        return pq.stream().anyMatch(cordinates::equals);
    }
    private boolean isValid(Cordinates cordinates) {
        return cordinates.x >= 0  && cordinates.y >= 0 && cordinates.x < n && cordinates.y < m;
    }

    private void printSnake() {
        //System.out.println();
        pq.forEach(x -> System.out.println("{x " + x.x + " y " + x.y + "},"));
        System.out.println();
    }
}
