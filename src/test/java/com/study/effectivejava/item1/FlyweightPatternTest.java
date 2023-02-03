package com.study.effectivejava.item1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlyweightPatternTest {
    @Test
    void test() {
        /*
            flyweightPattern
            - 캐싱과 동일개념
            - 팩토리에서 인스턴스를 반환해줄때 팩토리에 저장되어있는 객체면 새로 생성하지않고, 기존에 저장되어있는것 반환. 그렇지않으면 새로생성하여 내부에 저장한뒤 반환
        */

        FlyweightPattern.Operator first = FlyweightPattern.OperatorFactory.getOperator("+");
        FlyweightPattern.Operator second = FlyweightPattern.OperatorFactory.getOperator("+");

        assertEquals(3, first.operate(1, 2));
        assertTrue(first == second); // 캐싱을해서 가져왔기때문에 주소도 같아야한다

    }
}