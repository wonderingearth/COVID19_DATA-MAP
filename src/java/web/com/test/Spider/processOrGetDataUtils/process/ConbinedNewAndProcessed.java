package web.com.test.Spider.processOrGetDataUtils.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import web.com.test.util.Arrays;
import web.com.test.util.FileIO.ReadFromJsonFile;
import web.com.test.util.FileIO.WriteToFile;

import java.io.IOException;

public class ConbinedNewAndProcessed {
    public static void Conbined() throws IOException {
        int count2 = 0;//用于判断是否到省会的计数器

        int forLoopLength = Arrays.cities.size();//循环边界
        for (int count = 0; count < forLoopLength; count++) {
            if(Arrays.cityCodes.get(count).equals("371200"))
                continue;
            String processedFilePath = "";
            String writeFilePath = "";
            String newFIlePath = "";
            if (Arrays.cities.get(count).equals(Arrays.proviencalCapitals.get(count2))) {
                System.out.println(Arrays.proviences.get(count2) + " : " + Arrays.cities.get(count) +":"+ Arrays.cityCodes.get(count));
                processedFilePath = "src\\main\\resources\\OriginalData\\city\\Processed\\covid_19_data" + Arrays.proviences.get(count2) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count)+ "_"  + ".json";
                newFIlePath = "src\\main\\resources\\OriginalData\\city\\New\\covid_19_data" + Arrays.proviences.get(count2) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count)+ "_"  + ".json";

                count2++;
            }
            else {
                System.out.println(Arrays.proviences.get(count2-1) + " : " + Arrays.cities.get(count) + Arrays.cityCodes.get(count));
                processedFilePath = "src\\main\\resources\\OriginalData\\city\\Processed\\covid_19_data" + Arrays.proviences.get(count2-1) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count) + ".json";
                newFIlePath = "src\\main\\resources\\OriginalData\\city\\New\\covid_19_data" + Arrays.proviences.get(count2-1) + "_" + Arrays.cities.get(count) + Arrays.cityCodes.get(count) + ".json";

            }
            writeFilePath = processedFilePath;
            String originalJsonStr = ReadFromJsonFile.ReadFrom(processedFilePath);
            String jsonStr = ReadFromJsonFile.ReadFrom(newFIlePath);


            JSONArray processedJsonArray = JSON.parseArray(originalJsonStr);

            JSONObject newJsonObject = JSON.parseObject(jsonStr);
            newJsonObject = JSON.parseObject(newJsonObject.getString("ncov_city_data"));
            newJsonObject = JSON.parseObject(newJsonObject.getString(Arrays.cityCodes.get(count)));
            JSONArray jsonArray2 = JSON.parseArray(newJsonObject.getString("series"));

            for(int i = 0;i<jsonArray2.size();i++){
                processedJsonArray.add(jsonArray2.get(i));
            }
            String processedJson = JSON.toJSONString(processedJsonArray);

            WriteToFile.WriteTo(writeFilePath,processedJson,false);// 将原始数据写入文件中，在 src/main/resources/ConfirmData 中
        }
    }
}
