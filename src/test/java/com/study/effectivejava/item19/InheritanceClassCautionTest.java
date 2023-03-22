package com.study.effectivejava.item19;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InheritanceClassCautionTest {

    @Test
    void 상속클래스의_생성자에서_재정의가능_메서드를_호출하면_안된다() {
        Sub sub = new Sub();
        sub.overrideMe();

    }

    static class Super {
        public Super() {
            overrideMe();
        }

        protected void overrideMe() {
        }
    }

    static class Sub extends Super {
        private final LocalDateTime now;
        public Sub() {
            now = LocalDateTime.now();
        }

        @Override
        protected void overrideMe() {
            System.out.println(now); // final이니 null이 생길수 없어야하는데, 상위클래스에서 하위 클래스 생성자가 호출되기 전에 먼저 호출하게되니 null
        }
    }
}