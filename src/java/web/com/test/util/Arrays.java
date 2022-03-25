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
    public static List<String> cityCodes = new ArrayList<>();

    public static List<Covid19MapDaily> covid19MapDailies_ExistingConfirmed = new ArrayList<>();
    public static List<Covid19MapDaily> covid19MapDailies_AddConfirmed = new ArrayList<>();
    public static List<Covid19MapDaily> covid19MapDailies_DeathIncr = new ArrayList<>();
    public static List<Covid19MapDaily> covid19MapDailies_CureIncr = new ArrayList<>();
    public static List<Covid19MapDaily> covid19MapDailies_TotalConfirm = new ArrayList<>();
    public static List<Covid19MapDaily> covid19MapDailies_TotalCure = new ArrayList<>();
    public static List<Covid19MapDaily> covid19MapDailies_TotalDeath = new ArrayList<>();

    public static String [][] allCovid19Data_ExistConfirmed;
    public static String [][] allCovid19Data_AddConfirmed;
    public static String [][] allCovid19Data_DeathIncr;
    public static String [][] allCovid19Data_CureIncr;
    public static String [][] allCovid19Data_TotalConfirm;
    public static String [][] allCovid19Data_TotalCure;
    public static String [][] allCovid19Data_TotalDeath;


    public static void inilizer() throws IOException {
        addCities();//添加城市名称
        addCitieCodes();//添加城市对应的城市码
        addDates();//添加日期
        addProviencesAndCapitals();//添加省会城市名称，单纯为了存储文件时，可以给城市加上省份名称
        addCovid19MapDailies();//添加 多个 Covid19MapDailies 类的实例，用于存储每一天的疫情数据
        inilizearAllCovid19Data();
    }
    public static void inilizearAllCovid19Data() throws IOException {

        int citiesSize = Arrays.cities.size();
        int dateSize = Arrays.dates.size();
        allCovid19Data_ExistConfirmed = new String[citiesSize+1][dateSize+1];
        allCovid19Data_AddConfirmed = new String[citiesSize+1][dateSize+1];
        allCovid19Data_DeathIncr = new String[citiesSize+1][dateSize+1];
        allCovid19Data_CureIncr = new String[citiesSize+1][dateSize+1];
        allCovid19Data_TotalConfirm = new String[citiesSize+1][dateSize+1];
        allCovid19Data_TotalCure = new String[citiesSize+1][dateSize+1];
        allCovid19Data_TotalDeath = new String[citiesSize+1][dateSize+1];

        for(int i = 0;i<Arrays.cities.size()+1;i++){
            for(int j = 0;j<Arrays.dates.size()+1;j++){
                allCovid19Data_ExistConfirmed[i][j]="0";
                allCovid19Data_AddConfirmed[i][j]="0";
                allCovid19Data_DeathIncr[i][j]="0";
                allCovid19Data_CureIncr[i][j]="0";
                allCovid19Data_TotalConfirm[i][j]="0";
                allCovid19Data_TotalCure[i][j]="0";
                allCovid19Data_TotalDeath[i][j]="0";
            }
        }
        for(int i = 1;i<citiesSize+1;i++) {
            allCovid19Data_ExistConfirmed[i][0] = Arrays.cities.get(i - 1);
            allCovid19Data_AddConfirmed[i][0] = Arrays.cities.get(i - 1);
            allCovid19Data_DeathIncr[i][0] = Arrays.cities.get(i - 1);
            allCovid19Data_CureIncr[i][0] = Arrays.cities.get(i - 1);
            allCovid19Data_TotalConfirm[i][0] = Arrays.cities.get(i - 1);
            allCovid19Data_TotalCure[i][0] = Arrays.cities.get(i - 1);
            allCovid19Data_TotalDeath[i][0] = Arrays.cities.get(i - 1);
        }
        for(int j = 1;j<dateSize+1;j++) {
            allCovid19Data_ExistConfirmed[0][j] = Arrays.dates.get(j - 1);
            allCovid19Data_AddConfirmed[0][j] = Arrays.dates.get(j - 1);
            allCovid19Data_DeathIncr[0][j] = Arrays.dates.get(j - 1);
            allCovid19Data_CureIncr[0][j] = Arrays.dates.get(j - 1);
            allCovid19Data_TotalConfirm[0][j] = Arrays.dates.get(j - 1);
            allCovid19Data_TotalCure[0][j] = Arrays.dates.get(j - 1);
            allCovid19Data_TotalDeath[0][j] = Arrays.dates.get(j - 1);
        }
    }
    public static boolean isDateMatch(String date,List<String> dates){
        for(int i = 0;i<dates.size();i++){
            if(date.equals(dates.get(i)))
                return true;
        }
        return false;
    }


    public static void addCovid19MapDailies(){
        for(int count = 0;count<dates.size();count++){
            covid19MapDailies_ExistingConfirmed.add(new Covid19MapDaily(dates.get(count)));
            covid19MapDailies_AddConfirmed.add(new Covid19MapDaily(dates.get(count)));
            covid19MapDailies_DeathIncr.add(new Covid19MapDaily(dates.get(count)));
            covid19MapDailies_CureIncr.add(new Covid19MapDaily(dates.get(count)));
            covid19MapDailies_TotalConfirm.add(new Covid19MapDaily(dates.get(count)));
            covid19MapDailies_TotalCure.add(new Covid19MapDaily(dates.get(count)));
            covid19MapDailies_TotalDeath.add(new Covid19MapDaily(dates.get(count)));
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

}


