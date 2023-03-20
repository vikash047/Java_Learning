package AtlassianCoding.Q2;

import java.util.List;

public interface ProcessFiles<T> {
    Output getTopNCollections(List<T> data, int n);
}
