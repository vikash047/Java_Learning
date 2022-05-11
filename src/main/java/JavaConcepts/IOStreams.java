package JavaConcepts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOStreams {
    // output stream uses stream to write to a destination; it may be a file, an array, peripheral, device or socket.
    // Input stream to read data from source;  it may be file, an array, device or socket.
    // write(int) throw IOException  write  a byte to current output stream.
    //  write(byte[]) throw IOException write an array of  byte  to current output stream.
    //   flush() throw  IOException  flushes   the  current output stream.
    //   close() is  used to close  the  current output  stream.
    // input  stream  read(), available() return estimated byte that can be read from the current  input   stream.
    // close()
    // File<IN/OUT>Stream, ByteArray<>Stream, Filter<>Stream, Piped<>Stream, Object<>Stream.
    // FileIput

    public static void fileStream() throws IOException {
        FileOutputStream  fout = new FileOutputStream("vikash.txt");
        String s =  "Welcome to the hell";
        fout.write(s.getBytes());
        fout.close();
        System.out.println("Success");
        FileInputStream fin = new FileInputStream("vikash.txt");
        int i = 0;
        while ((i = fin.read()) !=  -1) {
            System.out.println((char)i);
        }
        fin.close();
    }
    public static void  main(String args[]) throws IOException {
        fileStream();
    }
}
