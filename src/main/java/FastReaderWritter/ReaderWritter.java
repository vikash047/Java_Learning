package FastReaderWritter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class ReaderWritter {
    public static class Reader{
        private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer stringTokenizer;
        public String next()
        {
            while (stringTokenizer == null || !stringTokenizer.hasMoreElements())
            {
                try {
                    stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return stringTokenizer.nextToken();
        }
        public Integer nextInt()
        {
            return Integer.parseInt(next());
        }
        public Long nextLong()
        {
            return Long.parseLong(next());
        }
        public Double nextDouble()
        {
            return Double.parseDouble(next());
        }
        public String nextLine()
        {
            String line = "";
            try {
                line = bufferedReader.readLine();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return line;
        }
    }
    public static class Writter{
        private BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(System.out);
        public void out(Integer value) throws IOException {
            bufferedOutputStream.write(value);
        }
        public void out(String value) throws IOException {
            bufferedOutputStream.write(value.getBytes());
        }
        public void flush() throws IOException {
            bufferedOutputStream.flush();
        }
    }

}
