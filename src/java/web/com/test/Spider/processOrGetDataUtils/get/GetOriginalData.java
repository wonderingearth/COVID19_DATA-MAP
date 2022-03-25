/*
 *   GetExistingConfirmData 类
 *   作用：
 *   1.  爬取原始数据（每个地级市的疫情数据）
 *   2.  处理数据，将原始的数据处理成每一天的疫情数据
 *   3.  将数据存储为json的文件
 *
 *   因为涉及到的函数和其他类很多，所以详细内容都在代码的注释中。
 *   主要有三个函数（函数作用可以看具体的代码里的注释，不过函数名字已经尽量表达意思了）：
 *       public static String getJsonStr(String url)
 *       public static void processJsonStr(String jsonStr,String city,int count)
 *       public static void processMapToJsonData()
 *
 *   最关键的在于对于json格式数据的熟悉和处理。
 *   本文件处理的是现有确诊数据，即累计确诊-治愈-死亡。如果需要处理新增数据或者其他，分析原始的json数据，然后修改数据处理时的参数即可。
 *

 *   爬取的api接口介绍 （希望大家不要给这个api过高的带宽压力，找到一个能用的返回疫情历史数据的接口不容易。）
 *   例子： https://i.snssdk.com/forum/ncov_data/?data_type=%5B1%5D&city_code=%5B%22650500%22%5D&src_type=local&recent_days=800
 *   这个是今日头条疫情地图的接口，这里感谢他们。（通过网页端，f12调出开发者界面，选取网络，然后xhr，就可以找到这个接口）
 *   需要注意的参数为以下两个：
 *       city_code参数 这里修改值为对应的城市码，就可以返回对应的城市的疫情数据
 *       recent_days参数 这里返回的是对应参数值的天数的数据，这里我使用参数值=800，可以返回从2020年1月25日至今（2022-3-13）的所有数据
 *
 *   之前使用过另外一个api，但是存在数据错误，不过还是放在下面仅供大家参考
 *   https://api.inews.qq.com/newsqa/v1/query/pubished/daily/list?province=%E6%B9%96%E5%8C%97&city=%E6%AD%A6%E6%B1%89
 *   修改参数 province=省份名称 city=城市名称 可以得到对应地级市的数据（可以使用中文）
 *   但是由于存在 某些城市中2022年的数据覆盖到之前的数据上的问题，故不再采用。
 */

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
