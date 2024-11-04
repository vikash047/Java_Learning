package LLDPracctice.InMemoryDBWithIndexing;

import java.util.List;
import java.util.Map;

public interface Indexes {
    void indexRowData(Map<String, Object> rowData);
    void indexRow(Map<String, Object> row);

    List<Map<String, Object>> getData(Object FilterValue);

    void removeIndexValue(Map<String, Object> rowData);
}
