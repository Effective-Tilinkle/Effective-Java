package com.study.effectivejava.item55;

import java.util.Optional;
import java.util.stream.Stream;

public class OptionalAnalysis {
    public static void main(String[] args) {
//        Optional<String> optional = null;
//        optional.orElseThrow();
        Stream<Optional<String>> optionalStream = null;

        optionalStream
                .flatMap(Optional::stream)
                .forEach(str -> System.out.println(str));
    }
}
