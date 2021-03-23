package excel;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.*;

public class test {

    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        map.put("nine",9);
        map.put("six",6);
        map.put("name",6);
        map.put("eight",8);
        map.put("zero",0);
        map.put("one",1);
        map.put("four",4);
        map.put("two",2);
        //根据map中的key值排序
        sortMap(map);
        }



    public static void sortMap(Map<String, Integer> map){
        //获取entrySet
        Set<Map.Entry<String,Integer>> mapEntries = map.entrySet();
        for(Entry<String, Integer> entry : mapEntries){
            System.out.println("key:" +entry.getKey()+"   value:"+entry.getValue() );
        }
        //使用链表来对集合进行排序，使用LinkedList，利于插入元素
        List<Map.Entry<String, Integer>> result = new LinkedList<>(mapEntries);
       // 自定义比较器来比较链表中的元素
        Collections.sort(result, new Comparator<Entry<String, Integer>>() {
            //基于entry的值（Entry.getValue()），来排            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                    return o1.getValue().compareTo(o2.getValue()) ;
            }
        });
        //将排好序的存入到LinkedHashMap(可保持顺序)中，需要存储键和值信息对到新的映射中。
        Map<String,Integer> linkMap = new LinkedHashMap<>();
        for(Entry<String,Integer> newEntry :result){
            linkMap.put(newEntry.getKey(), newEntry.getValue());
        }
        //根据entrySet()方法遍历linkMap
            for(Map.Entry<String, Integer> mapEntry : linkMap.entrySet()){
                System.out.println("key:"+mapEntry.getKey()+"  value:"+mapEntry.getValue());
            }
        }

}
