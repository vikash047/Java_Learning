package JavaConcepts;

import java.io.*;

public class OutPutStreamExample {
    public void writeToStream() {
        try {
            FileOutputStream fout = new FileOutputStream("vikash.txt");
            fout.write(65);
            //fout.close();
            String s = "Welcome to learn file out put stream";
            fout.write(s.getBytes());
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bufferOutPutStream() {
        try {
            FileOutputStream fout = new FileOutputStream("vikash.txt");
            BufferedOutputStream bout = new BufferedOutputStream(fout);
            String s = "Welcome to shit india";
            bout.write(s.getBytes());
            bout.flush();
            bout.close();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void byteArrayoutputStream() throws IOException {
        FileOutputStream f1 = new FileOutputStream("vikash.txt");
        FileOutputStream f2 = new FileOutputStream("avi.txt");
        // can accept int value in constructor to fix the size of byte
        // have methods int size(), byte[] toByteArray(), toString(), write(int b), write(byte[] b, int off, int len)
        // writeTo(OutputStream sout), reset(), close()
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bout.write(65);
        bout.writeTo(f1);
        bout.writeTo(f2);
        bout.flush();
        bout.close();
        System.out.println("Done");

    }

    public void dataOutputStream() {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream("vikash.txt");
            DataOutputStream data = new DataOutputStream(file);
            data.write(65);
            data.flush();
            data.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fileOutputStream() {
        try {
            File data = new File("vikash.txt");
            FileOutputStream file = new FileOutputStream(data);
            FileOutputStream filter = new FileOutputStream(data);
            String s = "welcome to java point";
            byte b[] = s.getBytes();
            filter.write(b);
            filter.flush();
            filter.close();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
