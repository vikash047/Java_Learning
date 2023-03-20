package CodingQuestions;

import JavaConcurrencyAndThreading.BarbarShop;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PathToFolderTest {
    private final PathToFolder pathToFolder = new PathToFolder();
    @Test
    void printPath() {
        pathToFolder.addFolder(create(0, Arrays.asList(3, 7), "abc"));
        pathToFolder.addFolder(create(3, Arrays.asList(), "pqr"));
        pathToFolder.addFolder(create(8, Arrays.asList(), "def"));
        pathToFolder.addFolder(create(7, Arrays.asList(9), "ijk"));
        pathToFolder.addFolder(create(9, Arrays.asList(), "lmn"));
        pathToFolder.addFolder(create(0, Arrays.asList(8), "xyz"));
        pathToFolder.printPath(8);
    }

    private PathToFolder.Folder create(Integer id, List<Integer> child, String name) {


        return pathToFolder.new Folder(id, child, name);
    }
}