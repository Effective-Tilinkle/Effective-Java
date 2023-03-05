package com.study.effectivejava.item14;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class ComparableAnalysisTest {
    @Test
    void compareTo로_비교시_동일할때_equals가_true가아니면() {
        BigDecimal bigDecimal = new BigDecimal("1.0");
        BigDecimal sameValue = new BigDecimal("1.00");

        assertTrue(bigDecimal.compareTo(sameValue) == 0);
        assertFalse(bigDecimal.equals(sameValue)); // BigDecimal은 compareTo가 동일하더라도 equals는 다를수있다
        assertFalse(bigDecimal.hashCode() == sameValue.hashCode()); // 논리적으로 값이 같지만, hashCode또한 다르다

        HashSet<BigDecimal> hashSet = new HashSet();
        hashSet.add(bigDecimal);
        hashSet.add(sameValue); // 결국 hashCode와 equals로 비교해서 진행하기때문에, 위에서 본대로 값이 다르므로 새로이 추가된다

        assertTrue(hashSet.size() == 2);


        TreeSet<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(bigDecimal);
        treeSet.add(sameValue);

        assertTrue(treeSet.size() == 1); // 아래 참고

        /*
            // TreeSet.put
            public V put(K key, V value) {
                //...

                if (key == null)
                    throw new NullPointerException();
                @SuppressWarnings("unchecked")
                Comparable<? super K> k = (Comparable<? super K>) key;
                do {
                    parent = t;
                    cmp = k.compareTo(t.key);
                    if (cmp < 0)
                        t = t.left;
                    else if (cmp > 0)
                        t = t.right;
                    else
                        return t.setValue(value);  // equals에 대한 비교가 아니라 Comparable로 비교하여 크기가 동일한 값(==0)이면 갱신한다
                } while (t != null);

                //...
            }


        */
    }
}