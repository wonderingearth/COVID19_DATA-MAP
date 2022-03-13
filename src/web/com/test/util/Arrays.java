package web.com.test.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import web.com.test.domain.po.Covid19MapDaily;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Arrays {
    public static List<String> cities = new ArrayList<>();
    public static List<String> dates = new ArrayList<>();
    public static List<String> proviences = new ArrayList<>();
    public static List<String> proviencalCapitals = new ArrayList<>();
    public static List<Covid19MapDaily> covid19MapDailies = new ArrayList<>();
    public static List<String> cityCodes = new ArrayList<>();
    public static boolean isDateMatch(String date,List<String> dates){
        for(int i = 0;i<dates.size();i++){
            if(date.equals(dates.get(i)))
                return true;
        }
        return false;
    }


    public static void addCovid19MapDailies(){
        for(int count = 0;count<dates.size();count++){
            covid19MapDailies.add(new Covid19MapDaily(dates.get(count)));
        }
    }
    public static void addProviencesAndCapitals(){
        proviences.add("安徽");
        proviencalCapitals.add("合肥");
        proviences.add("福建");
        proviencalCapitals.add("福州");
        proviences.add("甘肃");
        proviencalCapitals.add("兰州");
        proviences.add("广东");
        proviencalCapitals.add("广州");
        proviences.add("广西");
        proviencalCapitals.add("南宁");
        proviences.add("海南");
        proviencalCapitals.add("海口");
        proviences.add("河北");
        proviencalCapitals.add("石家庄");
        proviences.add("黑龙江");
        proviencalCapitals.add("哈尔滨");
        proviences.add("河南");
        proviencalCapitals.add("郑州");
        proviences.add("湖北");
        proviencalCapitals.add("武汉");
        proviences.add("湖南");
        proviencalCapitals.add("长沙");
        proviences.add("江苏");
        proviencalCapitals.add("南京");
        proviences.add("江西");
        proviencalCapitals.add("南昌");
        proviences.add("吉林");
        proviencalCapitals.add("长春");
        proviences.add("辽宁");
        proviencalCapitals.add("沈阳");
        proviences.add("内蒙古");
        proviencalCapitals.add("呼和浩特");
        proviences.add("宁夏");
        proviencalCapitals.add("银川");
        proviences.add("青海");
        proviencalCapitals.add("西宁");
        proviences.add("山东");
        proviencalCapitals.add("济南");
        proviences.add("山西");
        proviencalCapitals.add("太原");
        proviences.add("陕西");
        proviencalCapitals.add("西安");
        proviences.add("四川");
        proviencalCapitals.add("成都");
        proviences.add("新疆");
        proviencalCapitals.add("乌鲁木齐");
        proviences.add("西藏");
        proviencalCapitals.add("拉萨");
        proviences.add("云南");
        proviencalCapitals.add("昆明");
        proviences.add("浙江");
        proviencalCapitals.add("杭州");
        proviences.add("台湾");
        proviencalCapitals.add("台湾");
        proviences.add("北京");
        proviencalCapitals.add("北京");
        proviences.add("天津");
        proviencalCapitals.add("天津");
        proviences.add("上海");
        proviencalCapitals.add("上海");
        proviences.add("重庆");
        proviencalCapitals.add("重庆");
        proviences.add("香港");
        proviencalCapitals.add("香港");
        proviences.add("澳门");
        proviencalCapitals.add("澳门");
    }

    public static void addCities() throws IOException {
        File file = new File("src\\main\\resources\\static\\china-cities.json");
        String jsonStr = FileUtils.readFileToString(file,"utf8");
        //System.out.println(jsonStr);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray features = JSON.parseArray(jsonObject.getString("features"));
        for(int featureCount = 0;featureCount<features.size();featureCount++){
            JSONObject properties = JSON.parseObject(features.getJSONObject(featureCount).getString("properties"));
            String city = (String)properties.getString("name");
            cities.add(city);
        }
        /*for(int i=0;i<cities.size();i++)
            System.out.println(cities.get(i));
        System.out.println(cities.size());
         */

    }
    public static void addCitieCodes() throws IOException {
        File file = new File("src\\main\\resources\\static\\china-cities.json");
        String jsonStr = FileUtils.readFileToString(file,"utf8");
        //System.out.println(jsonStr);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray features = JSON.parseArray(jsonObject.getString("features"));
        for(int featureCount = 0;featureCount<features.size();featureCount++){
            String cityCode = features.getJSONObject(featureCount).getString("id");
            cityCodes.add(cityCode);
        }
        /*for(int i=0;i<cityCodes.size();i++)
            System.out.println(cityCodes.get(i));
        System.out.println(cityCodes.size());
        */

    }
    public static void addDates() {
        String point = "-";
        List<String> years = new ArrayList<>();
        years.add("2020");
        years.add("2021");
        years.add("2022");
        for(int month = 1;month<=12;month++){
            if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                for(int day = 1;day<=31;day++){
                    String date = "";
                    if(month-10<0){
                        if(day-10<0){
                            date=years.get(0)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(1)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(2)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                        }
                        else{
                            date=years.get(0)+point+"0"+month+point+day;
                            dates.add(date);
                            date=years.get(1)+point+"0"+month+point+day;
                            dates.add(date);
                            date=years.get(2)+point+"0"+month+point+day;
                            dates.add(date);
                        }
                    }
                    else
                    {
                        if(day-10<0){
                            date=years.get(0)+point+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(1)+point+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(2)+point+month+point+"0"+day;
                            dates.add(date);
                        }
                        else{
                            date=years.get(0)+point+month+point+day;
                            dates.add(date);
                            date=years.get(1)+point+month+point+day;
                            dates.add(date);
                            date=years.get(2)+point+month+point+day;
                            dates.add(date);
                        }
                    }
                }
            }
            else if(month==4||month==6||month==9||month==11){
                for(int day = 1;day<=30;day++){
                    String date = "";
                    if(month-10<0){
                        if(day-10<0){
                            date=years.get(0)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(1)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(2)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                        }
                        else{
                            date=years.get(0)+point+"0"+month+point+day;
                            dates.add(date);
                            date=years.get(1)+point+"0"+month+point+day;
                            dates.add(date);
                            date=years.get(2)+point+"0"+month+point+day;
                            dates.add(date);
                        }
                    }
                    else{
                        if(day-10<0){
                            date=years.get(0)+point+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(1)+point+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(2)+point+month+point+"0"+day;
                            dates.add(date);
                        }
                        else{
                            date=years.get(0)+point+month+point+day;
                            dates.add(date);
                            date=years.get(1)+point+month+point+day;
                            dates.add(date);
                            date=years.get(2)+point+month+point+day;
                            dates.add(date);
                        }
                    }
                }
            }
            else if(month==2){
                for(int day = 1;day<=28;day++){
                    String date = "";
                    if(month-10<0){
                        if(day-10<0){
                            date=years.get(0)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(1)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(2)+point+"0"+month+point+"0"+day;
                            dates.add(date);
                        }
                        else{
                            date=years.get(0)+point+"0"+month+point+day;
                            dates.add(date);
                            date=years.get(1)+point+"0"+month+point+day;
                            dates.add(date);
                            date=years.get(2)+point+"0"+month+point+day;
                            dates.add(date);
                        }
                    }
                    else{
                        if(day-10<0){
                            date=years.get(0)+point+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(1)+point+month+point+"0"+day;
                            dates.add(date);
                            date=years.get(2)+point+month+point+"0"+day;
                            dates.add(date);
                        }
                        else{
                            date=years.get(0)+point+month+point+day;
                            dates.add(date);
                            date=years.get(1)+point+month+point+day;
                            dates.add(date);
                            date=years.get(2)+point+month+point+day;
                            dates.add(date);
                        }
                    }
                }
            }
        }
        dates.add("2020-02-29");
        Collections.sort(dates, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        /*for(int count=0;count<dates.size();count++)
            System.out.println(dates.get(count));

         */
    }
    public static List<String> getCities() {
        return cities;
    }

    public static List<String> getDates() {
        return dates;
    }

    public static void main(String[] args) throws IOException {
        Arrays.addCities();
    }
}


