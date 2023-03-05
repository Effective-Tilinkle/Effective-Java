package com.study.effectivejava.item14;

public class FunctionalInterfaceTest {
    @FunctionalInterface
    interface TestIntf {

        int compare();

        boolean equals(Object o); // Object의 public method는 추상메서드가 1개여야하는 함수형 인터페이스에서 count하지않는다

        String toString();

        default void testMethod() {
            TestIntf t = this;
        }
    }
}
