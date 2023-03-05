package com.study.effectivejava.item13;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CloneAnalysisTest {
    
    @Test
    void testClone() {
        A a = new A();
        A cloneA = a.clone();
        System.out.println(a.str);

        assertFalse(a == cloneA);
        assertTrue(a.str == cloneA.str);
    }
    
    @Test
    void 상속에도_clone_문제없다() {
        B b = new B();
        B cloneB = b.clone();

        assertFalse(b == cloneB);
        assertTrue(b.getClass() == cloneB.getClass());
        assertTrue(b.str == cloneB.str);
        assertTrue(b.getSuperStr() == cloneB.getSuperStr());
    }

    @Test
    void 배열_clone() {
        A[] aArr = new A[2];
        aArr[0] = new A();
        aArr[1] = new B();

        A[] clone = aArr.clone();

        assertFalse(aArr == clone);
        assertTrue(aArr[0] == clone[0]);
        assertTrue(aArr[1] == clone[1]);
        assertTrue(Arrays.equals(aArr, clone));
    }

    @Test
    void t() {
    }


    static class A implements Cloneable {
        String str = "a";

        @Override
        public A clone() {
            try {
                return (A) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            A a = (A) o;
            return Objects.equals(str, a.str);
        }

        @Override
        public int hashCode() {
            return Objects.hash(str);
        }
    }

    static class B extends A implements Cloneable {
        String str = "b";

        public String getSuperStr() {
            return super.str;
        }

        @Override
        public B clone() {
            return (B)super.clone();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            B b = (B) o;
            return Objects.equals(str, b.str);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), str);
        }
    }

    static class FinalFieldClass implements Serializable {
        final A[] aArr = new A[]{new A(), new B()};

        @Override
        public FinalFieldClass clone() {
            try {
                FinalFieldClass clone = (FinalFieldClass) super.clone();
//                clone.aArr = aArr.clone(); // final로 선언하면, 컴파일 에러로 배열에 대한 clone이 일어나지않음
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}