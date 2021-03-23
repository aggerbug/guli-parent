package excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {


    public static void main(String[] args) {
//        String fileName="D:\\write.xlsx";
//        EasyExcel.write(fileName,DemoData.class).sheet("学生").doWrite(getDome());
        String fileName="D:\\write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }
    public static List<DemoData> getDome(){
       List<DemoData> list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }

        return list;
    }
}
