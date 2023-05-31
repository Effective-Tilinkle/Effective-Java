package com.study.effectivejava.item47;

import java.util.Iterator;
import java.util.List;

public class ForEachAnalysis {
    public static void main(String[] args) {
        List<String> list = null;
        list.stream();

        Iterable<Temp> iterable = () -> {
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Temp next() {
                    return null;
                }
            };
        };

        for (Temp t : iterable) {
            System.out.println(t);
            System.out.println();
        }

        String[] strArr = new String[]{"A", "B"};

        for (String s : strArr) {
            System.out.println(s);
        }

        for (String s : list) {
            System.out.println(s);
        }

    }

    static class Temp {

    }
}
