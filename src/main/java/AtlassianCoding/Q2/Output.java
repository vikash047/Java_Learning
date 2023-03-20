package AtlassianCoding.Q2;

import java.util.List;

public class Output {
    private int totalSize;
    private List<FileCollection> topNCollBySize;

    public Output(int totalSize, List<FileCollection> topNCollBySize) {
        this.totalSize = totalSize;
        this.topNCollBySize = topNCollBySize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public List<FileCollection> getTopNCollBySize() {
        return topNCollBySize;
    }
}
