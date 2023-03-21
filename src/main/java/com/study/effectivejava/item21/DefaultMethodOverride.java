package com.study.effectivejava.item21;

import java.util.*;

public class DefaultMethodOverride {
    public static void main(String[] args) {
        List<String> listNotThreadSafe = new ArrayList<>();
        Collection<String> strings = Collections.synchronizedCollection(listNotThreadSafe);

        /*

            // Collections.SynchronizedCollection
            static class SynchronizedCollection<E> implements Collection<E>, Serializable {
                @SuppressWarnings("serial") // Conditionally serializable
                final Collection<E> c;  // Backing Collection
                @SuppressWarnings("serial") // Conditionally serializable
                final Object mutex;     // Object on which to synchronize

                SynchronizedCollection(Collection<E> c) {
                    this.c = Objects.requireNonNull(c);
                    mutex = this;
                }

                // ...

                @Override
                public boolean removeIf(Predicate<? super E> filter) {
                    synchronized (mutex) {return c.removeIf(filter);} // 동기화가 필요하기때문에 래퍼 클래스로 감싸고 동기화 작업을 수행하도록 removeIf를 재정의
                }

                // ...
            }

            // Collection 인터페이스
            public interface Collection<E> extends Iterable<E> {

                // ...
                default boolean removeIf(Predicate<? super E> filter) {
                    Objects.requireNonNull(filter);
                    boolean removed = false;
                    final Iterator<E> each = iterator();
                    while (each.hasNext()) {
                        if (filter.test(each.next())) {
                            each.remove();
                            removed = true;
                        }
                    }
                    return removed;
                }
                // ...
            }

         */

    }
}
