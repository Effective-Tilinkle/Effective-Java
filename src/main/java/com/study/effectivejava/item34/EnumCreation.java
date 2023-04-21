package com.study.effectivejava.item34;

import java.util.HashMap;
import java.util.Map;

public enum EnumCreation {

    TEST("hi");

    private final String s;

    EnumCreation(String s) {
        System.out.println("TEST 초기화");
        this.s = s;
    }

    static Map<String, String> map = new HashMap<>();

    static {
        System.out.println("static block 호출");
        System.out.println("static map 호출 : "+map);
        System.out.println("EnumCreation.TEST 호출 : "+EnumCreation.TEST);
    }
}
