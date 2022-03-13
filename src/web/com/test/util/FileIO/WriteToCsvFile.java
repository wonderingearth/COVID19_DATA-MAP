package web.com.test.util.FileIO;

import java.io.*;

public class WriteToCsvFile {
    public static void WriteTo(String filePath,String content) throws IOException {
        File file = new File(filePath);

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file,true),"utf8");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(content);
        bufferedWriter.write(',');
        bufferedWriter.write('\n');
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
