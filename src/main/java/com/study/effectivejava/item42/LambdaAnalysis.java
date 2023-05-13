package com.study.effectivejava.item42;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LambdaAnalysis {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.sort(Comparator.comparingInt(String::length));

//        Test t = () -> {
//            System.out.println("hi");
//        }
    }

    static abstract class Test {
        abstract void m();
    }

    private static class ComparatorWithSerializable implements Comparator{

        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    }
}
