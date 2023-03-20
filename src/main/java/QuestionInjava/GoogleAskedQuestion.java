package QuestionInjava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

abstract class DataType<T> implements Comparable<T> {
    String str;
    int index;
    public DataType(String str, int index) {
        this.str = str;
        this.index = index;
    }
}

class Person extends DataType<Person> {
    String age;
    String name;
    public Person(String str, int index) {
        super(str, index);
        parse();
    }

    private void parse() {
        String[] str = this.str.split(" ");
        System.out.println("value is " + str[0] + " " + str[1]);
        this.name = str[0];
        this.age = str[1];
    }

    @Override
    public int compareTo(Person o) {
        return this.age.compareTo(o.age);
    }
}

interface Factory<T> {
    T getObject(String str, int index);
}

class FactoryPerson implements Factory<Person> {

    @Override
    public Person getObject(String str, int index) {
        return new Person(str, index);
    }
}

class Solve<T extends DataType>  {

    public List<T> solve(List<BufferedReader> readers, Factory<T> fr) {
        PriorityQueue<T> pq = new PriorityQueue<>();
        List<T> result = new ArrayList<>();
        for(int i = 0; i < readers.size(); i++) {
            BufferedReader r = readers.get(i);
            String str = null;
            try {
                str = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(str != null) {
                pq.add(fr.getObject(str, i));
            }
        }
        while(!pq.isEmpty()) {
            T d = pq.poll();
            result.add(d);
            String  str = null;
            try {
                str = readers.get(d.index).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(str != null) {
                pq.add(fr.getObject(str, d.index));
            }
        }
        return result;
    }
}
public class GoogleAskedQuestion {
    public static void writeToFile() throws IOException {
        Writer wr1 = new FileWriter("vikash.txt");
        Writer wr2 = new FileWriter("vikash2.txt");
        BufferedWriter b1 = new BufferedWriter(wr1);
        BufferedWriter b2 = new BufferedWriter(wr2);
        b1.write("Vikash 19 \n");
        b1.write("Vikash 20 \n");
        b1.write("Komal 23 \n");
        b2.write("Konal 25 \n");
        b2.write("Deepak 50 \n");
        b1.close();
        b2.close();
        wr1.close();
        wr2.close();
    }
    public static List<BufferedReader> readers() throws FileNotFoundException {
        List<BufferedReader> lst = new ArrayList<>();
        Reader r1 = new FileReader("vikash.txt");
        Reader r2 = new FileReader("vikash2.txt");
        BufferedReader b1 = new BufferedReader(r1);
        lst.add(b1);
        BufferedReader b2 = new BufferedReader(r2);
        lst.add(b2);
        return lst;
    }
    public static void main(String[] args) throws IOException {
        writeToFile();
        List<BufferedReader> lst = readers();
        FactoryPerson p = new FactoryPerson();
        Solve<Person> s = new Solve<>();
        List<Person> result = s.solve(lst, p);
        for(Person pr : result) {
            System.out.println(pr.name + " " + pr.age);
        }
    }
}
