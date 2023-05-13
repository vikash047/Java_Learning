package FairMatic.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayOrderSummary {
    private Map<Integer, Hours> orderOfTheDay;
    private int total;
    public DayOrderSummary() {
        orderOfTheDay = new HashMap<>();
    }
    public void add(Integer hour, Integer depId, Integer count) {
        orderOfTheDay.putIfAbsent(hour, new Hours());
        orderOfTheDay.get(hour).add(depId, count);
        total += count;
    }

    public List<Map.Entry<Integer, List<Summary>>> getbyOrder() {
      return orderOfTheDay.entrySet().stream().map(x -> Map.entry(x.getKey(), x.getValue().getSummary())).collect(Collectors.toList());
    }
}
