package com.study.effectivejava.item49;

import java.util.Collections;

public class AssertAnalysis {

    static void methodWithAssert(int i) {
//        if(1==1) throw new AssertionError("test"); // 이렇게쓰면 바로 예외던짐. 즉, AssertionError를 명시적으로 선언하면 정상적으로 예외던진다..

        assert i > 0; // VM option으로 -ea를 붙여주어야한다

        System.out.println("메서드 수행");
    }

    public static void main(String[] args) {
        methodWithAssert(-3);
    }
}
