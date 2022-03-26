

package web.com.test.Spider.processOrGetDataUtils.get;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import web.com.test.util.Arrays;
import web.com.test.util.FileIO.WriteToCsv;
import web.com.test.util.FileIO.WriteToFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetOriginalData {
    //爬取原始jsonStr的函数，爬取的结果是 json格式的一个城市的疫情数据，这个数据包含的内容非常多，大家可以根据需要进行不同的处理
    public static String getOriginalJsonStr(String url) throws IOException {
        System.out.println(url);
        Connection connection = Jsoup.connect(url).ignoreContentType(true);
        connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36 Edg/92.0.902.73");
        Document document = connection.get();
        String jsonStr = document.select("body").text();
        //System.out.println(jsonStr);
        return jsonStr;
    }
}
