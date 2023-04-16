package com.study.effectivejava.item32;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VaragrsWithGeneric {
    static void dangerous(List<String> ... stringLists) {
        Object[] objects = stringLists; // 배열은 공변이니 가능
        List<Integer> integers = Arrays.asList(1, 2);
        objects[0] = integers; // 힙 오염

        String s = stringLists[0].get(0); // ClassCastException 발생
    }

    static <T> T[] toArray(T ... args) { // T가 Object이면 Object 배열로 생성된 args가 오고, T가 String이면 String 배열로 생성된 args가 넘어온다. 물론 런타임에 T는 Object이나 args는 나 자신의 구체적인 배열타입을 알고 있다.
        return args;
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b); // 여기서 toArray는 T라는 타입을 알 수 없으니 모두 받을 수 있또록 varargs는 Object[] 을 생성하게된다.. 그렇게되니, T가 String이라면 리턴타입으로 String[]로 캐스팅하도록 컴파일러가 만들어줄텐데, 그때에 Object[]을 String[]로 캐스팅할 수 없으므로 캐스팅 에러가나게되는것!
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }

        throw new AssertionError();
    }

    public static void main(String[] args) {
//        dangerous(Arrays.asList("hi")); // 컴파일러가 제네릭 가변인수 메서드를 호출하는 쪽에서도 경고를 준다. => java: unchecked generic array creation for varargs parameter of type java.util.List<java.lang.String>[]

        //////////////////
        Arrays.asList(1,2,3); // Arrays 내부적으로 ArrayList를 따로 만들어서 사용한다..

        //////////////////
//        String[] strings = pickTwo("a", "b", "c"); // 런타임에러.. cast 할 수 없다고함..

        //////////////////
        Serializable[] serializables = toArray("a", "b", 1); // 가변인수는 자신이 알 수 있는 가장 구체타입의 배열을 만들어주는듯함!


        /////////  배열간의 형변환   /////////
        Object obj1 = "a";
        Object obj2 = "a";
        Object obj3 = "a";
        Object[] objects = toArray(obj1, obj2, obj3);

//      obj1의 class String이 맞으나, obj1,obj2,obj3을 기반으로 Object배열을 생성햇을때, 이를 String 배열로 캐스팅은 안된다! (Object
//      (String[]) new Object[]{obj1, obj2, obj3}
//      어찌보면 당연히 안되는것.. 배열또한 하나의 클래스로 생성된것인데, 배열안에 담긴 내용물이 같다고해서 캐스팅이 되어야하는건 말이안됨..

//        String[] strings = (String[]) objects; // 컴파일에러
        System.out.println((String) objects[0]); // 이렇게 캐스팅은 당연 문제없음
        String[] strings = toArray("a","b","c");
        Object[] o = strings; // 배열은 공변이기에 이것도 문제없음

    }

    @SafeVarargs
    static <T> List<T> flatten(List<? extends T>... lists) { // 제네릭 varargs 매개변수를 안전하게 사용하는 메서드
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }

        return result;
    }

    static <T> List<T> flatten_list반환버전(List<List<? extends T>> lists) { // List로 변환하였기때문에 직접 @SafeVarargs를 선언할 필요없다. 물론, 타입 안전하다~. 다만 클라이언트 코드가 약간 지저분해질수있고(List.of()와 같은걸로 한번 묶어줘야..) 속도가 조금 느려질 수 있다
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }

        return result;
    }
}
