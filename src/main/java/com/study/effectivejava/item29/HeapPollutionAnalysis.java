package com.study.effectivejava.item29;

import java.util.ArrayList;
import java.util.List;

public class HeapPollutionAnalysis {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>(); // String만 들어갈것으로 기대
        stringList.add("a");

        Object obj = stringList;
        List<Integer> integerList = (List<Integer>) obj;
        integerList.add(1); // 런타임에 List의 제네릭 타입파라미터는 소거되므로.. 1이 들어갈 수 있다.. 그로인해 힙 오염이 유발된다 (힙 오염은 파라미터화 타입의 참조가 다른 파라미터화 타입을 바라보게 되는것 발생되는데, List<String>으로 된 참조가 List<Integer>가 가능하게되어 힙 오염이 발생된것!)

        System.out.println(stringList.get(0)); // 바이트 코드상 로타입 list.get(0)에 String 으로 형변환 해준것과 동일
        System.out.println(stringList.get(1)); // ClassCastException 발생!

//        for (String s : stringList) {
//            System.out.println(s);
//        }

        /*
        heap pollution : In the Java programming language, heap pollution is a situation that arises when a variable of a parameterized type refers to an object that is not of that parameterized type.[1]
        출처 : https://en.wikipedia.org/wiki/Heap_pollution

         */
    }
}
