package AtlassianCoding.Q2;

public class FileCollection implements Comparable<FileCollection>{
    private int size;

    private String Name;
    public FileCollection(int size) {
        this.size = size;
    }

    public FileCollection(int size, String name) {
        this.size = size;
        Name = name;
    }

    public void incrementSize(int n) {
        this.size += n;
    }
    public int getSize() {
        return size;
    }

    public String getName() {
        return Name;
    }

    @Override
    public int compareTo(FileCollection o) {
        return this.size - o.size;
    }
}
