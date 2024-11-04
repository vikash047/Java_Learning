package LLDPracctice.InMemoryDBWithIndexing;

public class SchemaMember {
    private ColumnDT columnDT;
    private String cName;
    private boolean required;

    public SchemaMember(ColumnDT columnDT, String cName, boolean required) {
        this.columnDT = columnDT;
        this.cName = cName;
        this.required = required;
    }

    public <T> T validateValue(Object value) throws Exception {
        if(required && value == null) {
            throw new Exception("value is required");
        }
        return columnDT.validate(value);
    }

    public String getcName() {
        return cName;
    }

    public boolean isRequired() {
        return required;
    }
}
