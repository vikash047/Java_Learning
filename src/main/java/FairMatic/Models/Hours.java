package FairMatic.Models;

import java.util.*;
import java.util.stream.Collectors;

public class Hours {
    Map<Integer, Integer> hs;
    int total;
    public Hours() {
        hs = new HashMap<>();
    }

    public void add(int dep, int cnt) {
        hs.put(dep, hs.getOrDefault(dep, 0) + cnt);
        total += cnt;
    }

    public List<Summary> getSummary() {
        var res = hs.entrySet().stream().map(x -> new Summary(x.getKey(), x.getValue()/(double)total))
                .collect(Collectors.toList());
        Collections.sort(res, new Comparator<Summary>() {
            @Override
            public int compare(Summary o1, Summary o2) {
                return Double.compare(o1.percentage, o2.percentage);
            }
        });
        return res;
    }

}
