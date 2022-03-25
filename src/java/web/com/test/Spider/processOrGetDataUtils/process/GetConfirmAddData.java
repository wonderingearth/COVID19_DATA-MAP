
/*
 * 用于获取新增确诊的类
 * */
package web.com.test.Spider.processOrGetDataUtils.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import web.com.test.util.Arrays;
import web.com.test.util.FileIO.ReadFromJsonFile;
import web.com.test.util.FileIO.WriteToCsv;
import web.com.test.util.FileIO.WriteToFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetConfirmAddData {
    public static void getCsvData() throws IOException {
        getJsonfileDataToArray();
        writeAllCovid19DataTocsv();
    }
    public static void getJsonfileDataToArray() throws IOException {
        for(int i = 24;i<Arrays.dates.size();i++){
            String date = Arrays.dates.get(i);
            //src/main/resources/AddData/jsonData/date/2020-02-08ConfirmAddDataSortedByDate.json
            String filePath = "src\\main\\resources\\AddData\\jsonData\\date\\"+date+"ConfirmAddDataSortedByDate.json";
            String jsonStr = ReadFromJsonFile.ReadFrom(filePath);
            JSONArray jsonArray;
            try {
                jsonArray = JSON.parseArray(jsonStr);
            }
            catch (Exception e){
                break;
            }
            if(jsonArray!=null)
                for(int jsonCount = 0;jsonCount<jsonArray.size();jsonCount++){
                    JSONObject detail = jsonArray.getJSONObject(jsonCount);
                    String city = detail.getString("name");
                    if(city.equals("null"))
                        continue;
                    String confirm = detail.getString("value");
                    storeAllCovid19Data(city,date,confirm);
                }
        }
    }
    /*处理原始jsonStr的函数,将原本按城市分类的包含大量信息的数据，处理成安=按日期分类的只包含现有确诊的数据
    处理结果是 Arrays类中的 Covid19MapDailies有数据了
    */
    public static void storeAllCovid19Data(String city,String date,String confirm){
        int flag1 = 0,flag2 = 0;
        //将数据存储到二维数组中去
        for(int i = 1;i<Arrays.cities.size()+1;i++){
            if(Arrays.allCovid19Data_AddConfirmed[i][0].equals(city))
                flag1 = i;
        }
        for(int j = 1;j<Arrays.dates.size()+1;j++){
            if(Arrays.allCovid19Data_AddConfirmed[0][j].equals(date))
                flag2 = j;
        }
        Arrays.allCovid19Data_AddConfirmed[flag1][flag2]=confirm;
    }
    public static void processJsonStr(String jsonStr,String city,String cityCodes) {
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        if (jsonArray != null)
            for (int jsonCount = 0; jsonCount < jsonArray.size(); jsonCount++) {
                JSONObject detail = jsonArray.getJSONObject(jsonCount);
                String date = "";
                date = detail.getString("date");
                int integer = (Integer.parseInt(detail.getString("confirmedIncr"))  );
                if (integer < 0)
                    integer = 0;
                String confirm = String.valueOf(integer);
                if (confirm.equals(""))
                    confirm = "0";

                //调用函数存储所有的数据到一个二维数组中去
                storeAllCovid19Data(city,date,confirm);

                if (Arrays.isDateMatch(date, Arrays.dates)) {
                    for (int loop3 = 0; loop3 < Arrays.covid19MapDailies_AddConfirmed.size(); loop3++) {
                        if (date.equals(Arrays.covid19MapDailies_AddConfirmed.get(loop3).getDate())) {
                            Arrays.covid19MapDailies_AddConfirmed.get(loop3).map.put(city, confirm);
                            break;
                        }
                    }
                }
            }
    }

    //将经过处理的数据（map类型）处理成json，并写入到文件中去
    public static void processMapToJsonData() throws IOException {

        for(int i = 0;i<Arrays.covid19MapDailies_AddConfirmed.size();i++) {
            String date = Arrays.covid19MapDailies_AddConfirmed.get(i).getDate();

            //分时数据
            System.out.println(Arrays.covid19MapDailies_AddConfirmed.get(i));
            System.out.println("json : ");
            JSONArray jsonArray = new JSONArray();
            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();
            for(String key:Arrays.covid19MapDailies_AddConfirmed.get(i).map.keySet())
            {
                keys.add(key);
                values.add(Arrays.covid19MapDailies_AddConfirmed.get(i).map.get(key));
            }
            String answerJson = "[";
            String str1 = "{";
            String str2 = "}";
            String str3 = ",";
            String name = "\"name\":";
            String value = "\"value\":";

            for(int j = 0;j<Arrays.covid19MapDailies_AddConfirmed.get(i).map.size();j++){
                if(j==Arrays.covid19MapDailies_AddConfirmed.get(i).map.size()-1) {
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
            String filePath = "src\\main\\resources\\AddData\\jsonData\\date\\"+date+"ConfirmAddDataSortedByDate.json";
            WriteToFile.WriteTo(filePath,answerJson,false);

        }
    }

    public static void writeAllCovid19DataTocsv() throws IOException {
        String filePath = "src\\main\\resources\\AddData\\csvData\\"+"AddConfirmedData.csv";
        String content = "";
        System.out.println(filePath);
        for(int i = 0;i<Arrays.cities.size()+1;i++){
            content = Arrays.allCovid19Data_AddConfirmed[i][0]+",";
            WriteToCsv.WriteTo(filePath,content);
            for(int j = 25;j<Arrays.dates.size()-1;j++){
                content = Arrays.allCovid19Data_AddConfirmed[i][j]+",";
                WriteToCsv.WriteTo(filePath,content);

            }
            content = Arrays.allCovid19Data_AddConfirmed[i][Arrays.dates.size()-1]+"\n";
            WriteToCsv.WriteTo(filePath,content);
        }
    }
}
