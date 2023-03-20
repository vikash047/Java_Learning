package AtlassianCoding;

public class Main {

    /*

     snake -> wall  ->
     hit the wall game over.
     move -> length 1  5 step then increase 1 length

     3 length intial ->
     (0,0) -> (0, 1) -> (0, 2)
     Board size -> [10X10]
     5 step need increase length 1 and reset = 0;
     Deque<> put  cordinate of the snake -> pop front and add to last
     [(0,0), (0, 1) (0, 2)]



     */
    public static void main(String[] args) throws Exception {
        int n = 10;
        int m = 10;
        int x = 0, y = 0, l = 3;
        var game = new SnakeGameImpl(n, m, x, y, l);
        game.moveSnake(Directions.Up);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
        game.moveSnake(Directions.Right);
    }
}
