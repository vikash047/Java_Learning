package Sales;

import java.util.*;

public class Main {
    /*
      Utility methods on redis cluster
      0-16384 slots numbers
      key -> hashed -> calculated with slots
      each redis server will serve slots range
      List<String> getRedisServer();
      List<String> slotRanges(String serverId); -> [start-end, start1-end1]
      boolean isAllSlotsCovered();
      List<String> getMissingSlots();
      List<String> getConflictSlots();
     */

    private final int start = 0;
    private final int end = 16384;

    private final RedisServer redisServer;

    public Main(RedisServer redisServer) {
        this.redisServer = redisServer;
    }

    public boolean isAllSlotsCovered() {
        List<String> redisServers = redisServer.getRedisServers();
        Map<String, List<int[]>> mappingRedisServerToRanges = new HashMap<>();
        for(var id : redisServers) {
            mappingRedisServerToRanges.put(id, sortRange(redisServer.getSlotRanged(id)));
        }
        var missing = this.missingSlots(mappingRedisServerToRanges);
        return missing.size() == 0;
    }

    public List<String> getMissingSlots() {
        List<String> redisServers = redisServer.getRedisServers();
        Map<String, List<int[]>> mappingRedisServerToRanges = new HashMap<>();
        for(var id : redisServers) {
            mappingRedisServerToRanges.put(id, sortRange(redisServer.getSlotRanged(id)));
        }
        var missing = this.missingSlots(mappingRedisServerToRanges);
        List<String> ret = new ArrayList<>();
        for(var m : missing) {
            ret.add(m[0] + "-" + m[1]);
        }
        return ret;
    }

    List<String> getConflictSlot() {
        List<String> redisServers = redisServer.getRedisServers();
        Map<String, List<int[]>> mappingRedisServerToRanges = new HashMap<>();
        for(var id : redisServers) {
            mappingRedisServerToRanges.put(id, sortRange(redisServer.getSlotRanged(id)));
        }
        PriorityQueue<Data> pq = new PriorityQueue<>();
        for(var kv : mappingRedisServerToRanges.entrySet()) {
            pq.offer(new Data(kv.getKey(), kv.getValue().get(0)[0], kv.getValue().get(0)[1], 1));
        }
        List<String> ret = new ArrayList<>();
        //0-> [2, 10], [3, 15], [5, 9]
        //1->  [1, 3], [5, 6], [16, 20]
        // [1, 3], [2, 10], [3, 15], [5, 6], [5, 9], [16, 20]
        while (!pq.isEmpty()) {
            var top = pq.poll();
            while (!pq.isEmpty() && pq.peek().start <= top.end) {
                var p = pq.poll();
                ret.add(p.start + "-" + p.end);
                if(mappingRedisServerToRanges.get(p.id).size() < p.index) {
                    var kv = mappingRedisServerToRanges.get(p.id);
                    pq.offer(new Data(p.id, kv.get(p.index)[0], kv.get(p.index)[1], p.index+1));
                }
            }
            if(mappingRedisServerToRanges.get(top.id).size() < top.index) {
                var kv = mappingRedisServerToRanges.get(top.id);
                pq.offer(new Data(top.id, kv.get(top.index)[0], kv.get(top.index)[1], top.index+1));
            }
        }
        return ret;
    }
    private class Data implements Comparable<Data>{
        String id;
        int start;
        int end;

        int index;

        public Data(String id, int start, int end, int index) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.index = index;
        }

        @Override
        public int compareTo(Data o) {
            if(this.start == o.start) {
                return Integer.compare(this.end, o.end);
            }
            return Integer.compare(this.start, o.start);
        }
    }

    private List<int[]> sortRange(List<String> range) {
        List<int[]> ret = new ArrayList<>();
        for(var r : range) {
            String[] s = r.split("-");
            int[] temp = new int[] {Integer.parseInt(s[0]), Integer.parseInt(s[1])};
            ret.add(temp);
        }
        Collections.sort(ret, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        return ret;
    }
    private List<int[]> missingSlots(Map<String, List<int[]>> slots) {
        // [1, 20], [2, 3], [15, 40], [45, 60],
        int exp = this.start;
        PriorityQueue<Data> pq = new PriorityQueue<>();
        for(var kv : slots.entrySet()) {
            pq.offer(new Data(kv.getKey(), kv.getValue().get(0)[0], kv.getValue().get(0)[1], 0));
        }
        List<int[]> missingSlot = new ArrayList<>();
        while (!pq.isEmpty()) {
            var top = pq.poll();
            if(exp < top.start) {
                int[] m = new int[]{exp, top.start - 1};
                missingSlot.add(m);
            }
            if(exp <= top.end)
                exp = top.end + 1;
            int index = top.index;
            if(index < slots.get(top.id).size() - 1) {
                index++;
                var entry = slots.get(top.id);
                pq.offer(new Data(top.id, entry.get(index)[0], entry.get(index)[1], index));
            }
        }
        if(exp < this.end) {
            missingSlot.add(new int[]{exp, end});
        }
        return missingSlot;
    }
}
