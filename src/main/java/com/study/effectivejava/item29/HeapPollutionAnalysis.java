package com.study.effectivejava.item29;

import java.util.ArrayList;
import java.util.List;

public class HeapPollutionAnalysis {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>(); // String만 들어갈것으로 기대.
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

        이펙티브자바에서 아래의 코드를 힙 오염을 유발할 수 있는 코드라고 설명한다
        elements = (E[]) new Object()[DEFAULT_INITIAL_CAPACITY] // 멤버변수에 "E[] elements" 로 선언

        E라는 파라미터 타입이 런타임에는 Object가 되긴하나, 컴파일타임에는 특정 타입이 될것이다. 그렇기에 제네릭 배열로 캐스팅하게되면 특정 타입이 elements에 들어갈 것을 기대하고 그렇게 되어야만한다.(비록 런타임엔 Object긴 하지만..)
        하지만, elements를 제네릭 배열로 캐스팅하지않고 그냥 new Object[]로 만들어놓는다면, 특정 타입을 기대하는게 아닌, 그냥 Object 배열로써 여러 타입의 데이터가 들어와도(그러면 안되지만..) 해당 배열 자체는 문제가 있는게 아니다.
        물론, 형변환은 한번은 이루어져야하고 후자의 경우 elements의 원소를 가져올때에 형변환이 필요로하지만 이를 힙오염으로 이펙자바에서는 해석하지않는듯함

        힙오염은 제네릭타입을 로타입으로 쓰게되면 발생할수밖에없음.. 그런데 이펙자바에서 소개한 힙오염을 유발할 수 있는 코드뿐아니라, 그렇지않게짜더라도 힙 오염을 유발시킬 수 있음 (위 예제 참고)

        */
    }
}
