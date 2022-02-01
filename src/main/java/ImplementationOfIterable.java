import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImplementationOfIterable implements Iterable{
    List<Integer> lst = new ArrayList<>();
    @Override
    public Iterator iterator() {
        return lst.iterator();
    }
}
