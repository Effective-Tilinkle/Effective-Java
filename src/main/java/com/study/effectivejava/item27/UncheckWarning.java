package com.study.effectivejava.item27;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UncheckWarning {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("a","b","c");
        String[] strings = list.toArray(new String[0]);

        for (String string : strings) {
            System.out.println(string);
        }


    }
}
