package com.study.effectivejava.item42;

import java.util.function.DoubleBinaryOperator;

public enum OperationWithLambda {
    PLUS("+", (a,b) -> a+b),
    MINUS("-", (a,b) -> a-b);

    private final String symbol;
    private final DoubleBinaryOperator doubleBinaryOperator;

    OperationWithLambda(String symbol, DoubleBinaryOperator doubleBinaryOperator) {
        this.symbol = symbol;
        this.doubleBinaryOperator = doubleBinaryOperator;
    }

    public static void main(String[] args) {
        OperationWithLambda plus = OperationWithLambda.PLUS;
    }
}
