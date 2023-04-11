package com.study.effectivejava.item30;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenericMethod {
    /*
    // Collections 내부
    @SuppressWarnings("unchecked") // (1)
    public static <T> Comparator<T> reverseOrder() { // 제네릭 싱글턴 팩토리
        return (Comparator<T>) ReverseComparator.REVERSE_ORDER; // (2)
    }

    private static class ReverseComparator implements Comparator<Comparable<Object>>, Serializable { // (3)
        // ...

        static final ReverseComparator REVERSE_ORDER = new ReverseComparator(); // 이건 여러번 생성될 필요가 없으니.. 싱글턴으로..

        public int compare(Comparable<Object> c1, Comparable<Object> c2) { // Comparator(Functional Interface) 구현부
            return c2.compareTo(c1);
        }

        // ...
    }

      (1) (Comparator<T>) 로인해 비검사 경고가 뜨나, 캐스팅해도 문제가되지않으므로 해당 어노테이션을 사용하여 비검사 형변환 경고를 없애자
      (2) 타입 매개변수에 맞게 매번 해당 객체의 타입으로 캐스팅해준다. 하지만 이를 사용하는 클라이언트가 Comparable를 구현한 구현체가 아닌걸 넣어도 여기서는 확인할 방법이없음.. 만약 Comparable 구현체가 아닌게 들어가서 비교하기 시작하면, 런타임에 compare 메서드를 호출할때 ClassCastException 떨어짐.
          => 이는 재귀적 타입한정을 사용했으면 해결되었을듯.. ex. <T extends Comparable<T>>
      (3) 여기 Comparator를 해석하면, Comparable을 구현한 Object를 비교하는 Comparator라고 읽을 수 있겠다.. 하지만, 런타임시에 <Comparable<Object>> 는 소거되고, 오직 Comparator만 남게된다. 컴파일러도 신경쓰는것은 Compartor일뿐, 소거되는 타입 파라미터인 Comparable<Object>는 구현부(ReverseComparator.compare)에서만 신경쓸뿐이다. 그래서 Comparator에 어떤 객체의 타입도 받아 줄 수 있게되었지만, 해당 객체가 Comparable의 구현체 인지까지는 확인못하여, 런타임시에 위에서 이야기한 ClassCastException이 나타날 수 있는것이다..
    */

    public static void main(String[] args) {
        Comparator<Temp> objectComparator = Collections.reverseOrder();
        Temp temp1 = new Temp();
        Temp temp2 = new Temp();

        int compare = objectComparator.compare(temp1, temp2); // 런타임시에 temp1, temp2는 Comparable이 아니기때문에 ClassCastException이 떨어짐
        System.out.println(compare);

        // 위의 문제를 아래로 개선가능..
//        Comparator<Temp> comparator = GenericMethod.reverseOrder(); // 컴파일에러..
        Comparator<ComparableTemp> comparator2 = GenericMethod.reverseOrder();

        // Collections.max 또한 재귀적 타입한정을 사용하여 타입 파라미터에 Comparable을 구현한 구현체가 아니면 타입파라미터에 못들어 오게하였다.
        List<Integer> integers = Arrays.asList(3, 1, 5, 2, 10);
        Integer max = Collections.max(integers);

        List<Temp> temps = Arrays.asList(new Temp(), new Temp(), new Temp());
//        Temp max1 = Collections.max(temps); // 컴파일에러.. Temp는 Comparable을 구현하지않았기때문..

    }

    public static <T extends Comparable<T>> Comparator<T> reverseOrder() {
        return (Comparator<T>) ReverseComparator.REVERSE_ORDER;
    }

    private static class ReverseComparator
            implements Comparator<Comparable<Object>>, Serializable {

        static final ReverseComparator REVERSE_ORDER
                = new ReverseComparator();

        public int compare(Comparable<Object> c1, Comparable<Object> c2) {
            return c2.compareTo(c1);
        }

        @Override
        public Comparator<Comparable<Object>> reversed() {
            return Comparator.naturalOrder();
        }
    }
    static class Temp { }

    static class ComparableTemp implements Comparable<ComparableTemp> {
        @Override
        public int compareTo(ComparableTemp o) {
            return 0;
        }
    }
}
