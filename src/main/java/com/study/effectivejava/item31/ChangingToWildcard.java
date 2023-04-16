package com.study.effectivejava.item31;

import java.util.ArrayList;
import java.util.List;

public class ChangingToWildcard {
    public static <E> void swap_별로임(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    public static void swap_wildcard사용(List<?> list, int i, int j) { // public api를 사용한다면 이렇게 작성하자
//        list.set(i, list.set(j, list.get(i))); // 컴파일에러.. 와일드카드 타입일때 null 이외에는 set 불가. 이를 위해 swap_별로임 메서드를 private 도우미 메서드를 활용
        swap_별로임(list, i, j); // 추후 실제로 만들게되면 이 도우미 메서드는 private으로 변경되어야한다
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        ChangingToWildcard.<String>swap_별로임(list, 1, 3);
        ChangingToWildcard.swap_wildcard사용(list, 1, 3);
    }
}
