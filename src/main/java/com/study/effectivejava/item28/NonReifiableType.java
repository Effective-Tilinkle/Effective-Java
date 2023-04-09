package com.study.effectivejava.item28;

import java.util.Arrays;
import java.util.List;

public class NonReifiableType { // 실체화 불가타입 - 타입파라미터, 제네릭타입, 파라미터화 타입
    public static void main(String[] args) {
        // 비한정적 와일드카드 타입은 배열로 생성 가능(실체화 불가타입인 파라미터화 타입중에 한가지 예외. - 비한정적 와일드카드 타입은 실체화가능)
        List<?>[] lists = new List<?>[3];
//        List<? extends String>[] lists = new List<? extends String>[3]; 컴파일에러.. 한정적 와일드카드 타입은 안된다~

        Object[] objects = lists;
        objects[0] = Arrays.asList(1);
        objects[1] = Arrays.asList("str");

        System.out.println(lists[0].get(0));
        System.out.println(lists[1].get(0));

        objects[2] = new String[]{"str"}; // ArrayStoreException 발생
    }
}
