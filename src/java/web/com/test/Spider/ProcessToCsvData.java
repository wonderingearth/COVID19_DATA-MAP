package web.com.test.Spider;

import web.com.test.Spider.processOrGetDataUtils.process.*;
import web.com.test.util.Arrays;

import java.io.IOException;

public class ProcessToCsvData {
    public static void main(String[] args) throws IOException {
        Arrays.inilizer();
        GetExistingConfirmData.getCsvData();
        GetConfirmAddData.getCsvData();
        GetCureIncrData.getCsvData();
        GetDeathIncrData.getCsvData();
        GetTotalConfirmData.getCsvData();
        GetTotalCureData.getCsvData();
        GetTotalDeathData.getCsvData();
    }
}
