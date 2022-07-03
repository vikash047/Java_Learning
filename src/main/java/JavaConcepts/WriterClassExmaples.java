package JavaConcepts;

import JavaConcurrencyBaeldung.SenderReceiverPattern.ForkJoin.CustomeRecursiveTask;

import java.io.*;

public class WriterClassExmaples {
    // abstract class for writing to character stream.
    // constructor protected Write(), Write(Object lock)
    // append(char c), append(CharSequence csq), close(), flush(), write(char[] buf), write(char[] buf, int off, int len)
    // write(int c), write(String str), write(String str, int off, int len)
    public void writeExample() throws IOException {
        Writer writer = new FileWriter("vikash.txt");
        String content = "I love my country";
        writer.write(content);
        writer.close();
        System.out.println("Done");
    }

    // FileWrite it is same as FileOutputStream but you have to convert data into byte but in this one you can write directly string to file.
    public void fileWriterExample() throws IOException {
        FileWriter fw = new FileWriter("vikash.txt");
        fw.write("Welcome to Java Point Masti!");
        fw.close();
    }
    // BufferedWriter provides buffering for the writing instances. it makes performance fast.
    // BufferedWriter(Write obj), BufferedWriter(Writer obj, int sz)
    // newLine(), write(char c), write(char[] c, int off, int len), write(String str, int off, int len), flush(), close();
    public void bufferedWriteExample() throws IOException {
        FileWriter fw  = new FileWriter("vikash.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        bufferedWriter.write("Welcome to our house!");
        bufferedWriter.close();
    }
    // CharArrayWriter is buffer used to write to multiple stream writeTo(Writer out) method used.
    // OutputStreamWriter convert character to stream byte,
    // OutputStreamWriter(OutputStream out),
    // FilterWriter filter the input

    class CustomFilter extends FilterWriter {

        /**
         * Create a new filtered writer.
         *
         * @param out a Writer object to provide the underlying stream.
         * @throws NullPointerException if <code>out</code> is <code>null</code>
         */
        protected CustomFilter(Writer out) {
            super(out);
        }

        public void write(String str) throws IOException {
            super.write(str.toLowerCase());
        }
    }

    public void filterWriterExample() throws IOException {
        FileWriter fw = new FileWriter("vikash.txt");
        CustomFilter customFilter = new CustomFilter(fw);
        customFilter.write("I LOVE MY COUNTRY!");
        customFilter.close();
        FileReader fr = new FileReader("vikash.txt");
        BufferedReader bufferedReader = new BufferedReader(fr);
        int k;
        while ((k = bufferedReader.read()) != -1) {
            System.out.println((char) k);
        }
        bufferedReader.close();;
        fr.close();
    }
}
