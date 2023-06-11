package com.study.effectivejava.item53;

public class VarArgs {
    static int sum(int... args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }

        return sum;
    }

    static int min(int firstArg, int... remainingArgs) {
        int min = firstArg;
        for (int remainingArg : remainingArgs) {
            if (min > remainingArg) {
                min = remainingArg;
            }
        }

        return min;
    }

    public static void main(String[] args) {

    }
}
