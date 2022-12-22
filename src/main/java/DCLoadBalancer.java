import java.util.*;
import java.util.stream.Collectors;

public class DCLoadBalancer {
    class Machine {
        public Integer id;
        public Integer cap;
        public Integer used;
        public TreeMap<Integer, Integer> position;
        public HashMap<Integer, Pair> apps;
        public int index;
        public Machine(Integer id, Integer cap) {
            this.id = id;
            this.cap = cap;
            this.used = 0;
            this.apps = new HashMap<>();
            this.position = new TreeMap<>();
            this.index = 0;
        }
        public void addApp(int id, int load) {
            apps.put(id, new Pair(load, index++));
            position.put(index, id);
            used += load;
        }
        public void removeApp(int id) {
            if(apps.containsKey(id)) {
                Pair p = apps.get(id);
                used -= p.key;
                position.remove(p.value);
                apps.remove(id);
                System.out.println("removed app " + id);
            }
        }
        public Integer free() {
            return  cap - used;
        }
        public List<Pair> getApps() {
            for(Map.Entry<Integer, Integer> mp : position.entrySet()) {
                System.out.println(" position " + mp.getKey() + " Id " + mp.getValue());
                if(!apps.containsKey(mp.getValue())) {
                    System.out.println("False");
                }
            }
            return position.entrySet().stream().map(x -> new Pair(x.getValue(), apps.get(x.getValue()).key))
                    .collect(Collectors.toList());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Machine machine = (Machine) o;
            return Objects.equals(id, machine.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
    class Pair {
        public Integer key;
        public Integer value;

        public Pair(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(key, pair.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
    private final HashMap<Integer, Machine> machines = new HashMap<>();
    private final TreeSet<Pair> pm = new TreeSet<>(new Comparator<Pair>() {
        @Override
        public int compare(Pair o1, Pair o2) {
           if(o1.value == o2.value) {
               return o1.key.compareTo(o2.key);
           }
           return o2.value.compareTo(o1.value);
        }
    });
    private final HashMap<Integer, Integer> appToMachine = new HashMap<>();
    public void addMachine(int machineId, int capacity) {
        Machine m = new Machine(machineId, capacity);
        machines.put(m.id, m);
        pm.add(new Pair(m.id, m.free()));
    }

    public void removeMachine(int machineId) {
        Machine m = machines.get(machineId);
        Pair pp = pm.ceiling(new Pair(m.id, m.free()));
        pm.remove(pp);
        for (Pair p : m.getApps()) {
            appToMachine.remove(p.key);
            addApplication(p.key, p.value);
        }
        machines.remove(machineId);
    }

    public int addApplication(int appId, int loadUse) {
        Pair p = pm.pollFirst();
        if(p.value < loadUse) {
            return -1;
        }
        Machine m = machines.get(p.key);
        m.addApp(appId, loadUse);
        pm.add(new Pair(m.id, m.free()));
        appToMachine.put(appId, m.id);
        return m.id;
    }

    public void stopApplication(int appId) {
        if(!appToMachine.containsKey(appId)) {
            return;
        }
        Integer mId = appToMachine.get(appId);
        Machine m = machines.get(mId);
        Pair p = pm.ceiling(new Pair(m.id, m.free()));
        pm.remove(p);
        m.removeApp(appId);
        appToMachine.remove(appId);
        pm.add(new Pair(m.id, m.free()));
    }

    public List<Integer> getApplications(int machineId) {
        return machines.get(machineId).getApps().stream().map(x -> x.key)
                .collect(Collectors.toList());
    }
}
