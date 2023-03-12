package com.study.effectivejava.item10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloatingPointTest {
    @Test
    void test() {
        assertFalse(1.1 + 0.1 == 1.2); // 소수점 기본타입은 double
        assertTrue(1.1f + 0.1f == 1.2f);
        assertFalse(1.1f + 0.2f == 1.3f); // float 끼리 더한다고 항상 같지않음 (가수부 범위 넘어가면 반올림이 있기때문에 무한소수일경우는 기대했던 계산 결과가 나오지않을수있다)
        assertFalse(1.1f + 0.1f == 1.2d);

        assertTrue(1.25f == 1.25d); // 가수부가 길지않기에 (23bit보다 적기에) 문제발생안함
        assertFalse((float)0.1 == 0.1);
        assertFalse(Double.compare(0.1f, 0.1) == 0);

        System.out.printf("1.1f 의 이진수: %32s%n",Integer.toBinaryString(Float.floatToIntBits(1.1f))); // 가수부분 저장시 잘려나가는 데이터가있는 경우 반올림해서 저장(마지막이 1100으로 끝나야할것 같은데 1101로 저장되는이유)
        System.out.printf("0.1f 의 이진수: %32s%n",Integer.toBinaryString(Float.floatToIntBits(0.1f)));
        System.out.printf("1.2f 의 이진수: %32s%n",Integer.toBinaryString(Float.floatToIntBits(1.2f))); // float 끼리 더하면 항상 맞으려나..?
        System.out.printf("1.2d 의 이진수: %64s%n",Long.toBinaryString(Double.doubleToLongBits(1.2d)));
        System.out.printf("(double)1.2f 의 이진수: %64s%n",Long.toBinaryString(Double.doubleToLongBits(1.2f))); // 가수부는 그냥 double 크기에 맞추어 0 더 붙인거일뿐.. 그렇기에 1.2d와 같지가 않다..

        System.out.println();

        System.out.printf("1.1f 의 이진수: %32s%n",Integer.toBinaryString(Float.floatToIntBits(1.1f)));
        System.out.printf("0.2f 의 이진수: %32s%n",Integer.toBinaryString(Float.floatToIntBits(0.2f)));
        System.out.printf("1.3f 의 이진수: %32s%n",Integer.toBinaryString(Float.floatToIntBits(1.3f)));

        System.out.println();

        System.out.printf("1.25f 의 이진수: %32s%n",Integer.toBinaryString(Float.floatToIntBits(1.25f)));
        System.out.printf("0.1f 의 이진수: %32s%n",Integer.toBinaryString(Float.floatToIntBits(0.1f)));
        System.out.printf("0.1d 의 이진수: %64s%n",Long.toBinaryString(Double.doubleToLongBits(0.1d)));
            // 0.1d 의 이진수
            // 0    01111111011   1001100110011001100110011001100110011001100110011010
            // 부호  지수부          가수부 (무한소수일 경우에는 double이나 float 같은 경우 가수부 범위가 넘어서면 잘리기때문에 오차가 발생)

    }
}