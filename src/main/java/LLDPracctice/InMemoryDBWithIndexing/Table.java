package LLDPracctice.InMemoryDBWithIndexing;

import java.util.List;
import java.util.Map;

public class Table {
    private String tName;
    private String primaryKey;

    private TableSchema tableSchema;

    List<String> indexes;

    public Table(String tName, String primaryKey, TableSchema tableSchema, List<String> indexes) {
        this.tName = tName;
        this.primaryKey = primaryKey;
        this.tableSchema = tableSchema;
        this.indexes = indexes;
    }

    public void createIndex(String cName, IndexesType type) {

    }

    public void insertData(Map<String, Object> rowData) {

    }

    private void indexRowData(Map<String, Object> rowData) {

    }

    private List<Map<String, Object>> scanOnFilters(List<String> filterKeys, List<Map<String, Object>> rowData) {
        return null;
    }

    private List<Map<String, Object>> scanByIndexesValue(Map<String, Object> filterKeyAndValues) {
        return null;
    }

    private void deleteIndex(String cName, IndexesType indexesType) {

    }

    private void deleteData(Object value) {

    }
}
