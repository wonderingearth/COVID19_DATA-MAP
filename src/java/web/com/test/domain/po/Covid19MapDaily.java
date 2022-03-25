/*
*   Covid19MapDaily ��
*   ���ã������ʵ���������ڼ�¼ĳһ����¹�����ȷ������ݡ�
*   �����������ԣ�
*   1.  date ���ڼ�¼���ڡ�
*   2.  map LinkedHashMap���͵����ݣ����ڼ�¼��Ӧdate�µ��������ݡ�key�洢��ֵΪ���е����ƣ�value�洢��ֵΪ�ó��е�����ȷ�ﲡ��
*
*   ����һЩ����ĺ��������캯����get��set�������Լ�toString������������׸����
*   ���ļ����������ڣ�2022-03-25
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
