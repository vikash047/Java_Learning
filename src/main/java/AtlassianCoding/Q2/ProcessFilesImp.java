package AtlassianCoding.Q2;

import java.util.*;

public class ProcessFilesImp implements ProcessFiles<DataFormat>{

    /*data size n
      (n) + (nlog(k) => (O) nlog(k)
     */
    @Override
    public Output getTopNCollections(List<DataFormat> data, int n) {
        PriorityQueue<FileCollection> pq = new PriorityQueue<>();
        Map<String, FileCollection> record = new HashMap<>();
        int totalSize = 0;
        for(var d : data) {
            totalSize += d.getSize();
            if(record.containsKey(d.getColleName())) {
                record.get(d.getColleName()).incrementSize(d.getSize());
            } else if(d.getColleName() != null){
                record.put(d.getColleName(), new FileCollection(d.getSize(), d.getColleName()));
            }
        }
        for(var kv : record.entrySet()) {
            if(pq.size() < n) {
                pq.offer(kv.getValue());
            } else if(pq.size() >= n && pq.peek().getSize() < kv.getValue().getSize()) {
                pq.poll();
                pq.offer(kv.getValue());
            }
        }
        List<FileCollection> ret = new ArrayList<>();
        while(!pq.isEmpty()) {
            ret.add(pq.poll());
        }
        return  new Output(totalSize, ret);
    }
}
