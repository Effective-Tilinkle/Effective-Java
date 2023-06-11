package com.study.effectivejava.item52;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetListChaos {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();

        for (int i = -3; i < 3; i++) {
            set.add(i);
            list.add(i);
        }

//        for (int i = 0; i < 3; i++) {
//            set.remove(i);
//            list.remove(i);
//        }

        for (Integer i = 0; i < 3; i++) {
            set.remove(i);
            list.remove(i);
        }

        System.out.println("set: "+set);
        System.out.println("list: "+list);

        // List의 remove가 int를 받는것과 Object를 받는것으로 다중정의되어있기때문에 혼란스러움.....

    }
}
