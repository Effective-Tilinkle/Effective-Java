package com.study.effectivejava.item1;

import java.util.HashMap;
import java.util.Map;

public class FlyweightPattern {

    public static class OperatorFactory {
        private final static Map<String, Operator> cached = new HashMap<>();

        private OperatorFactory() {}

        public static Operator getOperator(String operateCode) {
            Operator operator = cached.get(operateCode);

            if (operator == null) {
                if (operateCode.equals("+")) {
                    operator = new PlusOperator();
                } else if (operateCode.equals("-")) {
                    operator = new MinusOperator();
                }
                cached.put(operateCode, operator);
            }

            if (operator == null) {
                throw new IllegalArgumentException();
            }

            return operator;
        }
    }

    interface Operator {
        int operate(int leftOperand, int rightOperand);
    }

    public static class PlusOperator implements Operator {
        @Override
        public int operate(int leftOperand, int rightOperand) {
            return leftOperand + rightOperand;
        }
    }

    public static class MinusOperator implements Operator {

        @Override
        public int operate(int leftOperand, int rightOperand) {
            return leftOperand - rightOperand;
        }
    }

}
