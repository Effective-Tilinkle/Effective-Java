package com.study.effectivejava.item60;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalAnalysis {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("10.5");
        BigDecimal bigDecimal2 = new BigDecimal("7.2");
        BigDecimal bigDecimal3 = new BigDecimal("7.2234234");
        BigDecimal bigDecimal4 = new BigDecimal("-7.2234234");

        System.out.println(bigDecimal + "의 scale : " + bigDecimal.scale());
        System.out.println(bigDecimal2 + "의 scale : " + bigDecimal2.scale());
        System.out.println(bigDecimal3 + "의 scale : " + bigDecimal3.scale());
        System.out.println(bigDecimal4 + "의 scale : " + bigDecimal4.scale());

        BigDecimal subtract = bigDecimal.subtract(bigDecimal2);
        System.out.println(subtract);
//        System.out.println(bigDecimal4.subtract(bigDecimal, MathContext));

    }
}
