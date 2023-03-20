package CodingQuestions;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public class TaskSchedule {
    final Comparator<Map.Entry<Character, Integer>> comparator = Comparator.comparing(x -> x.getValue());
    TreeSet<Map.Entry<Character, Integer>> orderedSet = new TreeSet<>(comparator.reversed());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskSchedule that = (TaskSchedule) o;
        return Objects.equals(comparator, that.comparator) && Objects.equals(orderedSet, that.orderedSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comparator, orderedSet);
    }
}
