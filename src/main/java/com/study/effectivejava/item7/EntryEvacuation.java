package com.study.effectivejava.item7;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntryEvacuation {
    public static void main(String[] args) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) { // 가장 처음 들어온 entry
                return size() == 6 ? true : false;
            }
        };

        linkedHashMap.put("a","b");
        linkedHashMap.put("b","b");
        linkedHashMap.put("c","b");
        linkedHashMap.put("d","b");
        linkedHashMap.put("e","b");
        linkedHashMap.put("f","b");
        linkedHashMap.put("g","b");

        System.out.println(linkedHashMap);
    }
}
