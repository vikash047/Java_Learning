package JMT.JavaConcepts;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.LinkedList;

public class JavaFastIo {
    private static final class tokens extends LinkedList<String> {
        tokens(String line) {
            super(new LinkedList<String>());
            for (var token: line.split(" ")) {
                add(token);
            }
        }
        String nextStr() { return remove(); }
        int nextInt() { return Integer.parseInt(remove()); }
        long nextLong() { return Long.parseLong(remove()); }
        double nextDbl() { return Double.parseDouble(remove()); }
        BigInteger nextBigInt() { return new BigInteger(remove()); }
    }
    private static tokens nextLine(BufferedReader cin) {
        String line = null;
        try { line = cin.readLine(); }
        catch (IOException e) { e.printStackTrace(); }
        return new tokens(line);
    }
    private static byte[] to_bytes(String s)  { return s.getBytes(); }
    private static byte[] to_bytes(Integer x) { return to_bytes(x.toString()); }
    private static byte[] to_bytes(Long x) { return to_bytes(x.toString()); }
    private static byte[] to_bytes(String f, Double x) { return to_bytes(String.format(f,x)); }
    private static byte[] to_bytes(BigInteger x) { return to_bytes(x.toString()); }
    private final static byte[] space = to_bytes(" ");
    private final static byte[] endl  = to_bytes("\n");
    public static void main(String[] args) throws IOException {
        final var cin  = new BufferedReader(new InputStreamReader(System.in));
        final var cout = new BufferedOutputStream(System.out);
        cout.flush();
    }
}
