package com.study.effectivejava.item26;

import java.util.ArrayList;
import java.util.List;

public class RawTypeProblem {
    public static void main(String[] args) {
        List rawTypeList = null;
        List<Object> parameterizedTypeList = new ArrayList<>();

        List<String> stringList = null;

        rawTypeList = stringList;
//        parameterizedTypeList = stringList; // 컴파일에러.. List<String>은 List의 하위 타입이지만, List<Object>의 하위타입은 아니기에.. 엄연히 둘은 다르다! 이런 부분이 제네릭을 사용하면 타입에 대한 안정성을 높여주는것!

        parameterizedTypeList.add(new ArrayList<String>()); // 물론 add로 집어넣는것은 문제없지..

        // 위와 동일한 다른 예제
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings,Integer.valueOf(30));
        String s = strings.get(0);          // unsafeAdd에는 에러가 없지만 여기서 class cast exception 발생.. 결국 런타임시에 알 수 있는 예외가됨..
//        safeAdd(strings, Integer.valueOf(30)); // 컴파일 에러.. 이유는 위와 동일

        ////////////////////////

        // 비한정적 와일드카드 타입 사용하면 null외에는 어떤 원소도 넣을 수 없다
        List<?> wildcard = null;
//        wildcard.add("abc"); 컴파일 에러
        wildcard.add(null);

    }

    private static void unsafeAdd(List list, Object val) {
        list.add(val);
    }

    private static void safeAdd(List<Object> list, Object val) {
        list.add(val);
    }
}
