package LLDPracctice.InMemoryDBWithIndexing;

public class IntDataType extends ColumnDT{
    private int minValue = Integer.MIN_VALUE;
    private int maxValue = Integer.MAX_VALUE;
    public IntDataType(Class<?> dataType, int minValue, int maxValue) {
        super(dataType);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public <T> T validate(Object value) throws Exception {
        Integer result = super.validate(value);
        if(result < minValue && result > maxValue) {
            throw new RuntimeException(" value is not in the defined range");
        }
        return (T) result;
    }
}
