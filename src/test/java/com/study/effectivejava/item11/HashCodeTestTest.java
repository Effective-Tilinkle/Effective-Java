package com.study.effectivejava.item11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashCodeTestTest {
    @Test
    void test() {
        A a = new A("a");
        A anotherA = new A("a");
        assertFalse(a.hashCode() == anotherA.hashCode());
        assertFalse(System.identityHashCode(a) == System.identityHashCode(anotherA));
        assertTrue(a.hashCode() == System.identityHashCode(a));
    }

    static class A {
        String a;

        public A(String a) {
            this.a = a;
        }
    }
}