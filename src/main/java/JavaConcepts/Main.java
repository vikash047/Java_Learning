package JavaConcepts;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        OutPutStreamExample fileOutPutStreamExample = new OutPutStreamExample();
       // fileOutPutStreamExample.writeToStream();
        //fileOutPutStreamExample.byteArrayoutputStream();
        InputStreamExamples inputStreamExamples = new InputStreamExamples();
        //inputStreamExamples.fileInputStream();
        //inputStreamExamples.byteArrayInputStream();
        //fileOutPutStreamExample.dataOutputStream();
        //fileOutPutStreamExample.fileOutputStream();
        //inputStreamExamples.fileInputStreamEx();
        WriterClassExmaples wr = new WriterClassExmaples();
        //wr.writeExample();
        //wr.fileWriterExample();
       // wr.bufferedWriteExample();
        wr.filterWriterExample();
        ReaderClassExmaples rr = new ReaderClassExmaples();
        //rr.readerExample();
        //rr.bufferedReaderExample();
        rr.readingDataFromConsole();
    }
}
