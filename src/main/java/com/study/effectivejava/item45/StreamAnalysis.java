package com.study.effectivejava.item45;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class StreamAnalysis {
    public static void main(String[] args) {
        Map<String, Set<String>> map = new HashMap<>();
//        map.computeIfAbsent()

//        Stream<String> s;
//        s.collect(Collectors.groupingBy(w -> w))
//                .values()
//                .stream();
//
//        s.collect(Collectors.groupingBy(w -> {
//                    char[] chars = w.toCharArray();
//                    Arrays.sort(chars);
//                    return new String(chars);
//                }))
//                .values()

        Stream.iterate(BigInteger.TWO, i -> i.nextProbablePrime());
         Integer result = Stream.iterate(0, i -> i + 1)
                 .peek(System.out::println)
                .limit(10)
                .reduce(Integer::sum)
                .get();

        System.out.println(result);
    }

    private static String alphabetize(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
