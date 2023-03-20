package AtlassianCoding.Q2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    /*

For this system we would like to generate a report that lists:

The total size of all files stored; and

The top N collections (by file size) where N can be a user-defined value

file1.txt (size: 100)
file2.txt (size: 200) in collection "collection1"
file3.txt (size: 200) in collection "collection1"
file4.txt (size: 300) in collection "collection2"
file5.txt (size: 10)

Collection -> size ->

total size ->
N total entry and we wanted top k
Nlog(K)

(K)


intrafce {
     addFile(fileName, size, collectionName);
     List<Collection> getTopNCollection(Input, int n);
     int SizeOfFiles();
}

     */

    public static void main(String[] args) {
        List<DataFormat> lst = new ArrayList<>();
        lst.add(new DataFormat("a", 100, null));
        lst.add(new DataFormat("b", 100, "c1"));
        lst.add(new DataFormat("a", 200, "c2"));
        lst.add(new DataFormat("c", 300, "c1"));
        lst.add(new DataFormat("d", 100, null));
        lst.add(new DataFormat("e", 100, "c3"));
        ProcessFiles<DataFormat> p = new ProcessFilesImp();
        var result  = p.getTopNCollections(lst, 10);
        System.out.println(result.getTotalSize());
        for(var e : result.getTopNCollBySize()) {
            System.out.println(e.getName() + " " + e.getSize());
        }
    }
}
