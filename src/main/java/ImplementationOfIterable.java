import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ImplementationOfIterable implements Iterable{
    List<Integer> lst = new ArrayList<>();
    @Override
    public Iterator iterator() {
        return lst.iterator();
    }

    public Comparator<Pair<Integer, Integer>> cmp = Comparator.comparing(a -> a.first());
}
