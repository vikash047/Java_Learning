package AtlassianCoding.Q2;

public class DataFormat {
    private String fileName;
    private int size;
    private String colleName;

    public DataFormat(String fileName, int size, String colleName) {
        this.fileName = fileName;
        this.size = size;
        this.colleName = colleName;
    }

    public String getFileName() {
        return fileName;
    }

    public int getSize() {
        return size;
    }

    public String getColleName() {
        return colleName;
    }
}
