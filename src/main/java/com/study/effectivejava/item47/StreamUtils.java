package com.study.effectivejava.item47;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {
    public static <E> Iterable<E> streamToIterable(Stream<E> stream) {
        return stream::iterator;
    }

    public static <E> Stream<E> iterableToStream(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
