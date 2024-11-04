package Media.Net.Models;
public class IdGenerator {
    static  int id = 1;
    public static int id() {
        int t = id;
        id++;
        return t;
    }
}
