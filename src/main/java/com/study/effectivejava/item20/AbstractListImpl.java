package com.study.effectivejava.item20;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;

public class AbstractListImpl extends AbstractList<Integer> {
    // AbstractList 가 골격구현 클래스이기에 필요한 몇가지만 오버라이딩하면 List의 기능을 모두 사용할 수 있다..!
    // 템플릿 메서드 패턴
    private final Integer[] a;

    public AbstractListImpl(Integer[] a) {
        this.a = a;
    }

    @Override
    public Integer get(int index) {
        return a[index];
    }

    @Override
    public Integer set(int index, Integer element) {
        int oldVal = a[index];
        a[index] = element;
        return oldVal;
    }

    @Override
    public int size() {
        return a.length;
    }

    public static void main(String[] args) {
        AbstractListImpl integers = new AbstractListImpl(Arrays.asList(1, 2, 3, 4, 5).toArray(new Integer[0]));
        integers.set(1,10);
        Iterator<Integer> iterator = integers.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
