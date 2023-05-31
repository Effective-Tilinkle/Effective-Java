package com.study.effectivejava.item47;

import java.util.*;

public class PowerSet {

    public static <E> Collection<Set<E>> of(Set<E> set) {
        ArrayList<E> src = new ArrayList<>(set);

        if (src.size() > 30) {
            throw new IllegalArgumentException("원소가 30개 초과는 놉"); // AbstractList의 size가 int를 반환하기에.. 양수는 2^31 - 1 넘는 값은 안됨..
        }

        return new AbstractList<>() {
            @Override
            public Set<E> get(int index) {      // get이 호출되어 메모리에 result가 로드되는 시점은 iterator.next를 호출할때이다! (지연평가!) 그리고 for-each를 통해서 데이터를 계속 가지고있는게아닌, 루프를 빠져나오면 GC 대상이된다!
                HashSet<E> result = new HashSet();

                for (int i = 0; index != 0; i++, index >>= 1) {     // for문을 이렇게 잘 활용하자 + 비트를 잘 활용하자
                    if ((index & 1) == 1) {
                        result.add(src.get(i));
                    }
                }
                return result;
            }

            @Override
            public int size() {
                return 1 << src.size();
            }

            @Override
            public boolean contains(Object o) {
                return o instanceof Set && src.containsAll((Set)o);
            }
        };
    }

    public static void main(String[] args) {

        Set<String> a = Set.of("a", "b", "c");
        Collection<Set<String>> powerSets = PowerSet.of(a); // debugger에서는 이 라인에 값이 다 나오는데, 이것은 debug evaluation을 진행해서임.. 즉, 디버깅을 디버거 내부적으로 임시로 평가해놓은것.. 그렇기때문에 실제 HashSet 생성할때의 주소와 이 라인에 셋팅된 HashSet 주소가 당연다르다~
        System.out.println("멱집합 사이즈 : "+powerSets.size());
        for(Set<String> set : powerSets) {
            System.out.println(set);
        }

//        System.out.println(Integer.toBinaryString(4 >> 1));
//        boolean b = ((3 & 1) == 1);
//        System.out.println(b);

    }
}
