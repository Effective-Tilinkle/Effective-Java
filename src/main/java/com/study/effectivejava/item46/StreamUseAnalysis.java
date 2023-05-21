package com.study.effectivejava.item46;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class StreamUseAnalysis {
    public static void main(String[] args) {
        // 단어 갯수구하기
    }

    void 바람직하지않은_stream사용() throws FileNotFoundException { // 패러다임을 잘 이해하지 못한 채 API만 사용한코드
        File file = null;
        Map<String, Long> freq = new HashMap<>();

        try(Stream<String> words = new Scanner(file).tokens()) {
            words.forEach(word -> {
                freq.merge(word, 1L, Long::sum); // 상태를 수정하고 있음
            });
        }
    }

    void 바람직한_stream사용() throws FileNotFoundException {
        File file = null;
        Map<String, Long> freq;

        try(Stream<String> words = new Scanner(file).tokens()) {
            freq = words.collect(groupingBy(String::toLowerCase, counting()));
        }

//        try(Stream<String> words = StreamUtils.asStream(new Scanner(file))) { // java 8 일때..
//            freq = words.collect(groupingBy(String::toLowerCase, Collectors.counting()));
//        }
    }

    List<String> 가장흔한단어10개뽑아내기(Map<String, Long> freq) {
        return freq.keySet().stream()
                .sorted(comparing(freq::get).reversed()) // 한정적 메서드 참조
                .limit(10)
                .collect(toList());
    }


    static class StreamUtils {

        public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
            return asStream(sourceIterator, false);
        }

        public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
            Iterable<T> iterable = () -> sourceIterator;
            return StreamSupport.stream(iterable.spliterator(), parallel);
        }
    }
}
