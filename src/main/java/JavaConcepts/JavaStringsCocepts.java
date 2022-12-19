package JavaConcepts;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class JavaStringsCocepts {
    public static void TestStrings() {
        String s = "GeeksForGeeks";
        String s1 = new String("GeeksForGeeks");
        System.out.println(s + " second one is " + s1);
        StringBuilder str = new StringBuilder();
        str.append("GFG");
        System.out.println(str);
        str.append("Vikash");
        System.out.println(str);
        //s.replace('G', 'F');
        s += "New";
        System.out.println(s);
        StringBuffer buffer = new StringBuffer("Geeks");
        System.out.println(buffer.toString());
        buffer.setCharAt(0, 'F');
        char ch = str.charAt(0);
        System.out.println(ch);
        buffer.append("New");
        System.out.println(buffer);
        StringTokenizer tokenizer = new StringTokenizer("Hello geeks How are you", " ");
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
            StringTokenizer t2 = new StringTokenizer("JAVA : CODE : String", " :", true);
            while (t2.hasMoreTokens()) {
                System.out.println(t2.nextToken());
            }
        }

        ArrayList<String> arr1 = new ArrayList<>();
        arr1.add("Ram");
        arr1.add("Shayam");
        arr1.add("Alice");
        arr1.add("Bob");
        StringJoiner sj1 = new StringJoiner(",");
        sj1.setEmptyValue("Sj1 is empty");
        System.out.println(sj1);
        sj1.add(arr1.get(0));
        sj1.add(arr1.get(1));
        System.out.println(sj1 + " length " + sj1.length());
        StringJoiner sj2 = new StringJoiner(":");
        sj2.add(arr1.get(2));
        sj2.add(arr1.get(3));
        System.out.println(sj2 + " length " + sj2.length());
        sj1.merge(sj2);
        System.out.println(sj1);
        System.out.println(sj2);
    }

    public static void main(String[] args) {
        JavaStringsCocepts.TestStrings();
    }
}
