package com.study.effectivejava.item10;

import java.util.ArrayList;
import java.util.List;

public class InterfaceEqualsAnalysis {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

//        // ArrayList의 equals 메서드
//
//        public boolean equals(Object o) {
//            if (o == this) {
//                return true;
//            }
//
//            if (!(o instanceof List)) { // ArrayList를 확인하지않고, List 구현체 인지만 확인한다
//                return false;
//            }
//
//            final int expectedModCount = modCount;
//            // ArrayList can be subclassed and given arbitrary behavior, but we can
//            // still deal with the common case where o is ArrayList precisely
//            boolean equal = (o.getClass() == ArrayList.class)
//                    ? equalsArrayList((ArrayList<?>) o)
//                    : equalsRange((List<?>) o, 0, size);    // List 구현체이나, ArrayList가 아니면 여기서 별도 확인해준다
//
//            checkForComodification(expectedModCount);
//            return equal;
//        }
    }

}
