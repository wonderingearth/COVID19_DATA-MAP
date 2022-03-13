package web.com.test.util.FileIO;

import java.io.*;

public class WriteToFile {
    public static void WriteTo(String filePath,String content) throws IOException {
        File file = new File(filePath);

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file,false),"utf8");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
