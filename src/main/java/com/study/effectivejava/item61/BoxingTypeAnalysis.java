package com.study.effectivejava.item61;

import java.util.Comparator;

public class BoxingTypeAnalysis {
    public static void main(String[] args) {
        Integer integer = Integer.valueOf(3000);
        Integer integer2 = Integer.valueOf(3000);
        Integer integer3 = Integer.valueOf(10);
        Integer integer4 = Integer.valueOf(10);
        System.out.println(System.identityHashCode(integer));
        System.out.println(System.identityHashCode(integer2));

        System.out.println(integer == integer2); // false
        System.out.println(integer3 == integer4); // true

        Comparator<Integer> integerComparator = (i, j) -> i < j ? -1 : (i == j ? 0 : 1);
        System.out.println(integerComparator.compare(new Integer(10), new Integer(10))); // 1반환.. i < j 비교시엔 오토언박싱을 통해서 예상된 값을 반환하나, i == j 비교시에 "객체 참조의 식별성"을 보게되므로.. 두 인스턴스가 비록 값은 같을지라도 엄연히 다르니.. 0이 아닌 1을 반환한다..
        System.out.println(integerComparator.compare(Integer.valueOf(10), Integer.valueOf(10))); // 0 반환. -128~127 까지는 캐싱되어있기때문에 동일한 인스턴스가 동일하다

        Comparator<Integer> tComparator = Comparator.naturalOrder();
        int compare = tComparator.compare(new Integer(10), new Integer(10));
        System.out.println(compare); // 0 반환. Integer에 구현되어있는 compareTo를 사용하게되어 문제없이 비교됨


        //////////////////////////////////
        // 오토박싱시에 계속 객체 생성되나?

        int i = 128; // -128 ~ 127 까지는 캐싱된 인스턴스를 가져오기때문에 오토박싱해도 항상 동일한 인스턴스를 가져오긴한다..
        int j = 128;

        Integer boxedI = i; // 오토박싱 -> 새로운 객체 생성
        Integer boxedJ = j; // 오토박싱 -> 새로운 객체 생성

        System.out.println(boxedI == boxedJ);

    }
}
