package LLDPracctice.InMemoryDBWithIndexing;


public abstract class ColumnDT {

    private Class<?> dataType;
    public ColumnDT(Class<?> dataType) {
        this.dataType = dataType;
    }

    public <T> T validate(Object value) throws Exception {
        if(value != null && dataType.isInstance(value)) {
            return (T) dataType.cast(value);
        }
        throw new Exception("Data type of value does not match " + value);
    }
}
