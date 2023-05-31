package com.study.effectivejava.item47;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubList {
    // 입력리스트의 (연속적인) 부분리스트만들기

    public static <E> Stream<List<E>> of(List<E> list) {
        /*
            prefix의 suffix를 사용하자
              - prefix: 맨 앞의 원소가 포함된 부분리스트
              - suffix: 맨 마지막 원소가 포함된 부분리스트
         */

//        return Stream.concat(Stream.of(Collections.emptyList()),
//                IntStream.rangeClosed(1, list.size())
//                        .mapToObj(start -> list.subList(0, start))
//                        .flatMap(l -> IntStream.range(0, l.size())
//                                .mapToObj(end -> l.subList(end, l.size()))));

        // 이중 for문을 스트림으로 변경한것

        return Stream.concat(Stream.of(Collections.emptyList()),
                prefix(list).flatMap(SubList::suffix));
    }

    private static <E> Stream<List<E>> prefix(List<E> list) {
        return IntStream.rangeClosed(1, list.size())
                .mapToObj(start -> list.subList(0, start));
    }

    private static <E> Stream<List<E>> suffix(List<E> list) {
        return IntStream.range(0, list.size())
                .mapToObj(end -> list.subList(end, list.size()));
    }

    public static void main(String[] args) {
        SubList.of(Arrays.asList("a", "b", "c"))
                .forEach(System.out::println);
    }
}
