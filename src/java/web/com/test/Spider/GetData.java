/*
 *   GetData 类
 *   作用：
 *   1.  爬取每一天更新的数据
 *   2.  将更新的数据和以前的数据进行合并
 *   3.  将数据存储为json的文件
 *
 *   本文件的作用在于爬取并整合出原始json格式的数据，具体数据的处理分类由其他类来实现
 *
 *   爬取的api接口介绍 （希望大家不要给这个api过高的带宽压力，找到一个能用的返回疫情历史数据的接口不容易。）
 *   例子： https://i.snssdk.com/forum/ncov_data/?data_type=%5B1%5D&city_code=%5B%22650500%22%5D&src_type=local&recent_days=800
 *   这个是今日头条疫情地图的接口，这里感谢他们。（通过网页端，f12调出开发者界面，选取网络，然后xhr，就可以找到这个接口）
 *   需要注意的参数为以下两个：
 *       city_code参数 这里修改值为对应的城市码，就可以返回对应的城市的疫情数据
 *       recent_days参数 这里返回的是对应参数值的天数的数据，这里我使用参数值=1，每天运行一次，获取前一天的数据。
 *
 *   之前使用过另外一个api，但是存在数据错误，不过还是放在下面仅供大家参考
 *   https://api.inews.qq.com/newsqa/v1/query/pubished/daily/list?province=%E6%B9%96%E5%8C%97&city=%E6%AD%A6%E6%B1%89
 *   修改参数 province=省份名称 city=城市名称 可以得到对应地级市的数据（可以使用中文）
 *   但是由于存在 某些城市中2022年的数据覆盖到之前的数据上的问题，故不再采用。
 */

package web.com.test.Spider;

import web.com.test.Spider.processOrGetDataUtils.process.ConbinedNewAndProcessed;
import web.com.test.Spider.processOrGetDataUtils.get.GetOriginalData;
import web.com.test.util.Arrays;
import web.com.test.util.FileIO.WriteToFile;
import java.io.IOException;

public class GetData {

    public static void main(String[] args) throws IOException {
        //调用 Arrays类的一些静态方法来初始化辅助用的一些 ArrayList (Array类就是用来管理一些辅助ArrayList的工具类，具体可以见该类的注释)
        Arrays.inilizer();
        //以下两行代码是要爬取的URL中不变动的部分
        final String BASE_URL1 = "https://i.snssdk.com/forum/ncov_data/?data_type=%5B1%5D&city_code=%5B%22";
        final String BASE_URL2 = "%22%5D&src_type=local&recent_days=1";

        int count2 = 0;//用于判断是否到省会的计数器

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
                filePath = "src\\main\\resources\\OriginalData\\city\\New\\covid_19_data" + Arrays.proviences.get(count2) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count)+ "_"  + ".json";
                count2++;
            }
            else {
                System.out.println(Arrays.proviences.get(count2-1) + " : " + Arrays.cities.get(count) + Arrays.cityCodes.get(count));
                filePath = "src\\main\\resources\\OriginalData\\city\\New\\covid_19_data" + Arrays.proviences.get(count2-1) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count) + ".json";
            }

            String jsonStr = GetOriginalData.getOriginalJsonStr(url);//爬取 jsonStr
            WriteToFile.WriteTo(filePath,jsonStr,false);// 将原始数据写入文件中，在 src/main/resources/ConfirmData 中

        }
        //将新爬取到的数据和以前爬取到的数据进行合并，仍然为json格式
        ConbinedNewAndProcessed.Conbined();
    }
}
