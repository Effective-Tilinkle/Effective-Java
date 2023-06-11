package com.study.effectivejava.item52;

import java.util.EnumSet;
import java.util.concurrent.Executors;

public class OverloadChaos {
    public static void main(String[] args) {
        new Thread(System.out::println).start(); // 문제없음
        // Thread에는 Runnable밖에 없기때문에 문제없음

//        Executors.newCachedThreadPool().submit(System.out::println); // 컴파일 에러
        // submit도 다중정의되어있고, println도 다중정의되어있기때문에 컴파일러가 제대로 못찾음..(다중정의 메서드 찾는 알고리즘수행한다함..)

        Runnable r = System.out::println;
        Executors.newCachedThreadPool().submit(r); // 이건 가능. 아마 정확하게 Runnable로 명시하기때문에 문제없는듯함


        /////////////////
        // 아래 두개는 다르게 동작한다.. 다중정의 사용의 혼란으로 인한 예..
        Object obj = null;
        String s1 = String.valueOf(obj);
        System.out.println(s1); // null

        char[] chars = null;
        String s2 = String.valueOf(chars); // NullPointerException
        System.out.println(s2);

    }
}
