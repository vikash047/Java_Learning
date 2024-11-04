package LLDPracctice.InMemoryDBWithIndexing;

import java.util.HashMap;
import java.util.Map;

public class TableSchema {
    private Map<String, SchemaMember> stringSchemaMemberMap;
    private boolean undefinedColAllowed;

    public TableSchema(boolean undefinedColAllowed) {
        this.stringSchemaMemberMap = new HashMap<>();
        this.undefinedColAllowed = undefinedColAllowed;
    }

    public void addSchemaMember(SchemaMember... schemaMembers) {
        for(var sm : schemaMembers) {
            stringSchemaMemberMap.putIfAbsent(sm.getcName(), sm);
        }
    }

    public Map<String, Object> validateRowData(Map<String, Object> rowData) throws Exception {
        Map<String, Object> validatedData = new HashMap<>();
        for(var kv : rowData.entrySet()) {
            if(!stringSchemaMemberMap.containsKey(kv.getKey()) && !undefinedColAllowed) {
                throw new Exception("data schema is not defined and column name is " + kv.getKey());
            }
            var sm = stringSchemaMemberMap.getOrDefault(kv.getKey(), null);
            if(sm != null) {
                validatedData.put(kv.getKey(), sm.validateValue(kv.getValue()));
            } else {
                validatedData.put(kv.getKey(), kv.getValue());
            }
        }
        for(var k : stringSchemaMemberMap.entrySet()) {
            if(!rowData.containsKey(k.getKey()) && !k.getValue().isRequired()) {
                throw new Exception("Missing column value and column name is " + k.getKey());
            }
        }
        return validatedData;
    }
}
