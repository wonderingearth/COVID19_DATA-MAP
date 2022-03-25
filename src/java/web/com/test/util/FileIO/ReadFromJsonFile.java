package web.com.test.util.FileIO;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class ReadFromJsonFile {
    public static String ReadFrom(String filePath) throws IOException {
        File file = new File(filePath);

        String content = FileUtils.readFileToString(file,"utf8");
        return content;
    }
}
