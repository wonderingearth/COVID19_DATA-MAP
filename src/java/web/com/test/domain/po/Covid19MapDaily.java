/*
*   Covid19MapDaily 类
*   作用：该类的实例对象用于记录某一天的新冠现有确诊的数据。
*   包含三个属性：
*   1.  date 用于记录日期。
*   2.  map LinkedHashMap类型的数据，用于记录对应date下的疫情数据。key存储的值为城市的名称，value存储的值为该城市的现有确诊病例
*
*   包含一些常规的函数：构造函数，get和set方法，以及toString方法，不过多赘述。
*   本文件最后更新日期：2022-03-25
*/
package web.com.test.domain.po;

import java.util.LinkedHashMap;
import java.util.Map;

public class Covid19MapDaily {
    private String date;
    public Map<String,String> map = new LinkedHashMap<>();

    public Covid19MapDaily(String date) {
        this.date = date;
    }

    public Covid19MapDaily() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Covid19MapDaily{" +
                "date='" + date + '\'' +
                ", map=" + map +
                '}';
    }
}
