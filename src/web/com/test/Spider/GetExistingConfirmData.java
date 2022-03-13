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

package web.com.test.Spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import web.com.test.util.Arrays;
import web.com.test.util.FileIO.WriteToFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetExistingConfirmData {

    //爬取原始jsonStr的函数，爬取的结果是 json格式的一个城市的疫情数据，这个数据包含的内容非常多，大家可以根据需要进行不同的处理
    public static String getJsonStr(String url) throws IOException {
        System.out.println(url);
        Connection connection = Jsoup.connect(url).ignoreContentType(true);
        connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36 Edg/92.0.902.73");
        Document document = connection.get();
        String jsonStr = document.select("body").text();
        //System.out.println(jsonStr);
        return jsonStr;
    }

    /*处理原始jsonStr的函数,将原本按城市分类的包含大量信息的数据，处理成安=按日期分类的只包含现有确诊的数据
    处理结果是 Arrays类中的 Covid19MapDailies有数据了
    */
    public static void processJsonStr(String jsonStr,String city,int count) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        jsonObject = JSON.parseObject(jsonObject.getString("ncov_city_data"));
        jsonObject = JSON.parseObject(jsonObject.getString(Arrays.cityCodes.get(count)));
        JSONArray jsonArray = JSON.parseArray(jsonObject.getString("series"));
        if (jsonArray != null)
            for (int jsonCount = 0; jsonCount < jsonArray.size(); jsonCount++) {
                JSONObject detail = jsonArray.getJSONObject(jsonCount);
                String date = "";
                date = detail.getString("date");
                int integer = (Integer.parseInt(detail.getString("confirmedNum")) - Integer.parseInt(detail.getString("curesNum")) - Integer.parseInt(detail.getString("deathsNum")));
                if (integer < 0)
                    integer = 0;
                String confirm = String.valueOf(integer);
                if (confirm.equals(""))
                    confirm = "0";
                if (Arrays.isDateMatch(date, Arrays.dates)) {
                    for (int loop3 = 0; loop3 < Arrays.covid19MapDailies.size(); loop3++) {
                        if (date.equals(Arrays.covid19MapDailies.get(loop3).getDate())) {
                            Arrays.covid19MapDailies.get(loop3).map.put(city, confirm);
                            break;
                        }
                    }
                }
            }
    }

    //将经过处理的数据（map类型）处理成json，并写入到文件中去
    public static void processMapToJsonData() throws IOException {
        for(int i = 0;i<Arrays.covid19MapDailies.size();i++) {
            System.out.println(Arrays.covid19MapDailies.get(i));
            System.out.println("json : ");
            JSONArray jsonArray = new JSONArray();
            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();
            for(String key:Arrays.covid19MapDailies.get(i).map.keySet())
            {
                keys.add(key);
                values.add(Arrays.covid19MapDailies.get(i).map.get(key));
            }
            String answerJson = "[";
            String str1 = "{";
            String str2 = "}";
            String str3 = ",";
            String name = "\"name\":";
            String value = "\"value\":";
            String date = Arrays.covid19MapDailies.get(i).getDate();
            for(int j = 0;j<Arrays.covid19MapDailies.get(i).map.size();j++){
                if(j==Arrays.covid19MapDailies.get(i).map.size()-1) {
                    if(!values.get(j).equals("0"))
                        answerJson += str1 + name + "\"" + keys.get(j) + "\"" + str3 + value + values.get(j) + str2 + "]";
                    else
                        answerJson += str1 + name + "\"" + "null" + "\"" + str3 + value + values.get(j) + str2 + "]";
                }
                else {
                    if(!values.get(j).equals("0"))
                        answerJson += str1 + name + "\"" + keys.get(j) + "\"" + str3 + value + values.get(j) + str2 + str3;
                }
            }
            String filePath = "src\\main\\resources\\static\\"+date+"ConfirmData.json";
            WriteToFile.WriteTo(filePath,answerJson);
            System.out.println(answerJson);
            System.out.println('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        //调用 Arrays类的一些静态方法来初始化辅助用的一些 ArrayList (Array类就是用来管理一些辅助ArrayList的工具类，具体可以见该类的注释)
        Arrays.addCities();//添加城市名称
        Arrays.addCitieCodes();//添加城市对应的城市码
        Arrays.addDates();//添加日期
        Arrays.addProviencesAndCapitals();//添加省会城市名称，单纯为了存储文件时，可以给城市加上省份名称
        Arrays.addCovid19MapDailies();//添加 多个 Covid19MapDailies 类的实例，用于存储每一天的疫情数据

        //以下两行代码是要爬取的URL中不变动的部分
        final String BASE_URL1 = "https://i.snssdk.com/forum/ncov_data/?data_type=%5B1%5D&city_code=%5B%22";
        final String BASE_URL2 = "%22%5D&src_type=local&recent_days=800";

        int count2 = 0;//用于判断是否到省会的计数器

        /*开始根据 Arrays.cityCodes来爬取数据，因为使用的是同一份 china-cities.json，
        * 所以无论是用Arrays.cities.siza()还是cityCode.size()来做循环参数其实问题不大.
        */
        int forLoopLength = Arrays.cities.size();//循环边界
        for (int count = 0; count < forLoopLength; count++) {

            /*额，这个是因为莱芜市在2019年被撤销地级市，而我使用的china-city.json数据不是最新的，
            * 这里使用一个if判断将其过滤掉，这也算是一个小瑕疵，希望大神们来改正
            */
            if(Arrays.cityCodes.get(count).equals("371200"))
                continue;

            String url = "";//待爬取的url链接
            String city = "";//城市名称
            String filePath = "";//存储文件路径（使用相对路径）

            city = Arrays.cities.get(count);
            url = BASE_URL1 + Arrays.cityCodes.get(count) + BASE_URL2;

            //用于在存储文件时，将城市前面加上省份的代码块，获取 filePath的值
            if (Arrays.cities.get(count).equals(Arrays.proviencalCapitals.get(count2))) {
                System.out.println(Arrays.proviences.get(count2) + " : " + Arrays.cities.get(count) +":"+ Arrays.cityCodes.get(count));
                filePath = "src\\main\\resources\\ConfirmData\\covid_19_data" + Arrays.proviences.get(count2) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count)+ "_"  + ".json";
                count2++;
            }
            else {
                System.out.println(Arrays.proviences.get(count2-1) + " : " + Arrays.cities.get(count) + Arrays.cityCodes.get(count));
                filePath = "src\\main\\resources\\ConfirmData\\covid_19_data" + Arrays.proviences.get(count2-1) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count) + ".json";
            }

            String jsonStr = getJsonStr(url);//爬取 jsonStr
            WriteToFile.WriteTo(filePath,jsonStr);// 将原始数据写入文件中，在 src/main/resources/ConfirmData 中
            processJsonStr(jsonStr,city,count);//处理 jsonStr
        }
        //调用函数,将已经处理好的数据，转化为json格式，并写入文件
        processMapToJsonData();
    }
}