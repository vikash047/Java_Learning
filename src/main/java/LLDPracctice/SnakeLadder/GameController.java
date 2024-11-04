package LLDPracctice.SnakeLadder;


import java.util.ArrayList;
import java.util.List;

/*
   Entity
   Board -> have snake, ladder,
   Dice -> 6 face
   Player ->

 */
public class GameController {
    private List<User> userList = new ArrayList<>();
    private Board board = new Board();

    public void addSnake(int start, int end) {
        board.addSnake(new Snake(start, end));
    }

    public void addLadder(int start, int end) {
        board.addLadder(new Ladder(start, end));
    }
    public int rollDice() {
        return Dice.getNext();
    }

    public User turn() {
        var u = userList.remove(0);
        userList.add(u);
        return u;
    }

    public void move(User user, int pos) {
        var next = board.move(user.getCurrentPos(), pos);
        if(next == 100) {
            System.out.println("User win game over for user " + user.getId());
            userList.remove(user);
        } else {
            var u = user.changePost(next);
            userList.remove(user);
            userList.add(u);
        }
    }

}
