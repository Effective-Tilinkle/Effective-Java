package com.study.effectivejava.item54;

import java.util.ArrayList;
import java.util.List;

public class EmptyArray {

    static class ListWrapper {
        List<String> list = new ArrayList<>();
        private static final String[] EMPTY_ARRAY = new String[0];

        public String[] toArray일반() {
            return list.toArray(new String[0]); // 이렇게 되면 항상 내부적으로 전달받은 배열을 사용하는게 아닌, 새로운 배열을 생성하게됨. 즉, 저기서 길이 0짜리 배열을 전달해주는것은 우리가 원하는 반환 타입을 알려주는 역할정도라고 생각하면됨 (만약 list size가 0이면 매개변수로 전달한 new String[0] 그대로 전달해줌).
        }

        public String[] toArray최적화() {
            return list.toArray(EMPTY_ARRAY); // 성능 최적화를 위해 new String[0]을 계속 만들지않고 정적변수로 관리..
        }

        public String[] toArray최적화아님() {
            return list.toArray(new String[list.size()]); // 최적화가 안된다함.. 그냥 new String[0]을 파라미터로 넘겨주어 새로 배열만드는게 더 성능상 좋은듯..
        }
    }

    public static void main(String[] args) {
        // ArrayList.toArray 테스트
        List<String> list = new ArrayList<>() {{
            add("a");
            add("b");
        }};

        String[] emptyStringArr = new String[0];
        String[] array = list.toArray(emptyStringArr);
        System.out.println(array == emptyStringArr); // false => list의 요소 수보다 배열의 크기가 더 작기때문에 새로운 array 만들어서 반환

        String[] emptyStringArr2 = new String[10];
        String[] array2 = list.toArray(emptyStringArr2);
        System.out.println(array2 == emptyStringArr2); // true => list의 요소 수보다 배열의 크기가 더 크기때문에 전달받은 배열 사용 (이게 성능이 별로라함..)


        /*
         // ArrayList 내부

         public <T> T[] toArray(T[] a) {
            if (a.length < size)
                // Make a new array of a's runtime type, but my contents:
                return (T[]) Arrays.copyOf(elementData, size, a.getClass());
            System.arraycopy(elementData, 0, a, 0, size);
            if (a.length > size)
                a[size] = null;
            return a;
        }


        */
    }
}
