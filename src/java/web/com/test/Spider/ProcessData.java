/*
 *   ProcessData 类
 *   1.作用：
 *      将原始按照地级市分类的数据进行处理。处理为不同的数据，并按照时间分类，存储为json的数据，供数据可视化使用。
 *
 *      原始数据包含的信息比较丰富，我这里处理为以下几个：
 *          （1）新增确诊
 *              本文件处理的相关数据在 src/main/resources/AddData下
 *          （2）累计确诊
 *              本文件处理的相关数据在 src/main/resources/TotalConfirmData下
 *          （3）现有确诊
 *              本文件处理的相关数据在 src/main/resources/ExistingConfirmedData下
 *          （4）新增治愈
 *              本文件处理的相关数据在 src/main/resources/CureIncrData下
 *          （5）新增死亡
 *              本文件处理的相关数据在 src/main/resources/DeathIncrData下
 *          （6）累计治愈
 *              本文件处理的相关数据在 src/main/resources/TotalCureData下
 *          （7）累计死亡
 *              本文件处理的相关数据在 src/main/resources/TotalDeathData下
 *   2.概述：
 *      本文件主要通过对储存有原始文件的数据的读取(原始文件位置为src/main/resources/OriginalData/city/下)
 *      调用 ../processOrGetDataUtils/process/ 下对应的类的方法来实现不同数据的整理和存储
 *
 *   本文件最后注释更新日期：2022-03-25
 */

package web.com.test.Spider;

import web.com.test.Spider.processOrGetDataUtils.process.*;
import web.com.test.util.Arrays;
import web.com.test.util.FileIO.ReadFromJsonFile;

import java.io.IOException;

public class ProcessData {

    public static void main(String[] args) throws IOException {
        //调用 Arrays类的一些静态方法来初始化辅助用的一些 ArrayList (Array类就是用来管理一些辅助ArrayList的工具类，具体可以见该类的注释)
        Arrays.inilizer();

        int count2 = 0;//用于判断是否到省会的计数器

        int forLoopLength = Arrays.cities.size();//循环边界
        for (int count = 0; count < forLoopLength; count++) {

            /*额，这个是因为莱芜市在2019年被撤销地级市，而我使用的china-city.json数据不是最新的，
             * 这里使用一个if判断将其过滤掉，这也算是一个小瑕疵，希望大神们来改正
             */
            if(Arrays.cityCodes.get(count).equals("371200"))
                continue;

            String city = "";//城市名称
            String filePath = "";//原始数据文件路径
            String cityCode = "";

            city = Arrays.cities.get(count);
            cityCode = Arrays.cityCodes.get(count);

            //用于在存储文件时，将城市前面加上省份的代码块，获取 filePath的值
            if (Arrays.cities.get(count).equals(Arrays.proviencalCapitals.get(count2))) {
                System.out.println(Arrays.proviences.get(count2) + " : " + Arrays.cities.get(count) +":"+ Arrays.cityCodes.get(count));
                filePath = "src\\main\\resources\\OriginalData\\city\\Processed\\covid_19_data" + Arrays.proviences.get(count2) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count)+ "_"  + ".json";
                count2++;
            }
            else {
                System.out.println(Arrays.proviences.get(count2-1) + " : " + Arrays.cities.get(count) + Arrays.cityCodes.get(count));
                filePath = "src\\main\\resources\\OriginalData\\city\\Processed\\covid_19_data" + Arrays.proviences.get(count2-1) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count) + ".json";
            }

            String jsonStr = ReadFromJsonFile.ReadFrom(filePath);
            /*从原始文件中读取出json格式的字符串
            这里调用的是../../util/FileIO/ReadFromJsonFile.java 专门用于读取出json格式的字符串
            因为后续都是基于json格式来进行处理
            */

            //调用各个类来处理分类数据
            GetExistingConfirmData.processJsonStr(jsonStr,city,cityCode);
            GetConfirmAddData.processJsonStr(jsonStr,city,cityCode);
            GetDeathIncrData.processJsonStr(jsonStr,city,cityCode);
            GetCureIncrData.processJsonStr(jsonStr,city,cityCode);
            GetTotalConfirmData.processJsonStr(jsonStr,city,cityCode);
            GetTotalCureData.processJsonStr(jsonStr,city,cityCode);
            GetTotalDeathData.processJsonStr(jsonStr,city,cityCode);
        }
        //调用各个类来将分类好的数据转化为json格式字符串并写入文件
        GetExistingConfirmData.processMapToJsonData();
        GetConfirmAddData.processMapToJsonData();
        GetDeathIncrData.processMapToJsonData();
        GetCureIncrData.processMapToJsonData();
        GetTotalConfirmData.processMapToJsonData();
        GetTotalCureData.processMapToJsonData();
        GetTotalDeathData.processMapToJsonData();
    }
}
