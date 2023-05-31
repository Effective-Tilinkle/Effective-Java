package com.study.effectivejava.item47;

import java.util.Collection;
import java.util.stream.Stream;

public class CollectionAnalysis {
    public static void main(String[] args) {
        Collection<Temp> collection = null;

        Stream<Temp> stream = collection.stream(); // 컬렉션은 stream도 반환가능
        for (Temp temp : collection) { } // 컬렉션은 Iterable 구현체

        /*
        // Collection 내부

        public interface Collection<E> extends Iterable<E> {

            // ...

            default Stream<E> stream() {
                return StreamSupport.stream(spliterator(), false);
            }

            // ...
        }


         */
    }

    static class Temp {

    }
}
