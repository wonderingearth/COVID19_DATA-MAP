/*
*   只是需要疫情数据的话，可以不用管这个文件了
*   用于修改每一个日期对应的html文件的类，在GetAllHtml类中被使用到
* */

package web.com.test.util.FileIO;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import web.com.test.Controller;
import web.com.test.util.Arrays;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class ChangeConfirmHtml {
    public static void changeConfirmHtmlFile(String date) throws IOException {
        File file = new File("src\\main\\resources\\templates\\Covid-19_readOnly2.html");
        File file2 = new File("src\\main\\resources\\templates\\Covid-19_WriteOnly"+date+"Confirm.html");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"utf8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file2,false),"utf8");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        String line = "";
        while((line=bufferedReader.readLine())!=null){
            if(line.equals("    url: \"2020.03.15.json\",//请求数据的地址")){
                bufferedWriter.write("    url: \""+date+"ConfirmData.json\","+'\n');
            }
            else if(line.equals("      text:'新冠疫情地图',")){
                bufferedWriter.write("      text:'新冠疫情地图(每日现有确诊)"+date+"\',"+'\n');
            }
            else
                bufferedWriter.write(line+'\n');
            bufferedWriter.flush();
        }
        bufferedWriter.close();
    }
    public static void main(String[] args) throws IOException, InterruptedException {

    }
}
