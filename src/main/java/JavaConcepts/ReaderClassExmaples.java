package JavaConcepts;

import java.io.*;
import java.sql.SQLOutput;

public class ReaderClassExmaples {
    // reader is an abstract class to read from the stream. subclasses is bufferReader, CharArrayReader, FilterReader,
    // InputStreamReader, PipedReader, StringReader.
    // close(), mark(int readAheadLimit), markSupported(), read(), read(char[] buf), read(char[], buf, int off, int len)
    // read(CharBuffer target), ready() --> stream ready to read, reset() --> reset the stream, skip(long n) --> skips characters.

    public void readerExample() throws IOException {
        Reader reader = new FileReader("vikash.txt");
        int data = reader.read();
        while (data != -1) {
            System.out.println((char) data);
            data = reader.read();
        }
        reader.close();
    }

    // FileReader it return data byte format like FileInputStream
    // BufferedReader used to read data from chat sequence as well read data line by line.
    // BufferedReader(Reader r), BufferedReader(Reader rd, int size)
    // read() --> single char, read(char[] c, int off, int len), String readLine(), ready(), skip(long n), reset()
    void bufferedReaderExample() throws IOException {
        FileReader fr = new FileReader("vikash.txt");
        BufferedReader r = new BufferedReader(fr);
        int i;
        while ((i = r.read()) != -1) {
            System.out.println((char) i);
        }
        r.close();
        fr.close();
    }
    // reading data from console
    void readingDataFromConsole() throws IOException {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        System.out.println("Enter input");
        String input = br.readLine();
        System.out.println("Entered input is " + input);
        br.close();
        r.close();
    }
    // CharArrayReader used to read data from char array.So array is reader stream.
    // InputStreamReader bridge bet byte stream to char streams.
    // InputStreamReader(InputStream in), InputStreamReader(InputStream in, Charset sc)
    // FilterReader(Reader in)

}
