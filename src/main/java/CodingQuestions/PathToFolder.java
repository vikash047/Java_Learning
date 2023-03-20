package CodingQuestions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PathToFolder {
    public PathToFolder() {
        tree = new ConcurrentHashMap<>();
    }

    public class Folder {
        private final int id;
        private final List<Integer> sibling;
        private final String name;

        public Folder(int id, List<Integer> sibling, String name) {
            this.id = id;
            this.sibling = sibling;
            this.name = name;
        }
    }
    class Parent {
        private final Integer pr;
        private final Integer ipr;

        Parent(Integer pr, Integer ipr) {
            this.pr = pr;
            this.ipr = ipr;
        }
    }
    class RootFound {
        Boolean found = false;
    }
    private final Map<Integer, Map.Entry<Parent, List<Folder>>> tree;

    void addFolder(Folder fr) {
        tree.putIfAbsent(fr.id, new AbstractMap.SimpleEntry<>(new Parent(-1, -1), new ArrayList<>()));
        int index = tree.get(fr.id).getValue().size();
        tree.get(fr.id).getValue().add(fr);
        for(var si : fr.sibling) {
            if(tree.containsKey(si)) {
                var entry = tree.get(si);
                var p = new Parent(fr.id, index);
                tree.put(si, new AbstractMap.SimpleEntry<>(p, entry.getValue()));
            } else {
                tree.put(si, new AbstractMap.SimpleEntry<>(new Parent(fr.id, index), new ArrayList<>()));
            }

        }
    }
    String path(Integer index, Integer inner, RootFound f) {
        if(index == -1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        var entry = tree.get(index);
        Folder fr = entry.getValue().get(inner);
        var p = entry.getKey();
        if(index == 0) {
            f.found = true;
        }
        return sb.append(path(p.pr, p.ipr, f)).append("/").append(fr.name).toString();
    }
    void printPath(Integer index) {
        RootFound f = new RootFound();
        String p = path(index, 0, f);
        if(f.found) {
            System.out.println(p);
        }
    }

}
