package JavaConcepts;

import java.io.*;

public class InputStreamExamples {
    public void fileInputStream() throws IOException {
        FileInputStream fin = new FileInputStream("vikash.txt");
        BufferedInputStream bin = new BufferedInputStream(System.in);
        int i;
        while ((i = bin.read()) != -1) {
            System.out.print((char) i);
        }
        bin.close();
        fin.close();
    }

    public void byteArrayInputStream() {
        byte[] buf = {35, 36, 37, 38, 39, 40};
        ByteArrayInputStream byt = new ByteArrayInputStream(buf);
        int k = 0;
        while ((k = byt.read()) != -1) {
            char ch = (char) k;
            System.out.println("Value of the char is " + ch);
        }
    }

    public void fileInputStreamEx() throws IOException {
        File data = new File("vikash.txt");
        FileInputStream in = new FileInputStream(data);
        FilterInputStream filter = new BufferedInputStream(in); // subclass bufferedInputStream, dataInputStream
        int k = 0;
        while ((k = filter.read()) != -1) {
            System.out.println((char)k);
        }
        in.close();
        filter.close();
    }


}