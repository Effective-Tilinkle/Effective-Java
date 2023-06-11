package com.study.effectivejava.item54;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmptyList {
    public static void main(String[] args) {
        List<String> emptyList = Collections.emptyList();
        List<String> emptyList2 = Collections.emptyList();

        System.out.println(emptyList == emptyList2); // true

        ArrayList<String> arrayList = new ArrayList<>(emptyList);
        ArrayList<String> arrayList2 = new ArrayList<>(emptyList);
        // arrayList.elementData와 arrayList2.elementData는 동일한 배열을 가리킨다
    }
}
