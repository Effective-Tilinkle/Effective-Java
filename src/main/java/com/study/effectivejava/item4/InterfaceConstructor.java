package com.study.effectivejava.item4;

public class InterfaceConstructor {
    interface IntfFactory {
        static IntfFactory staticFactory() {
            return null;
        }

        default IntfFactory defaultMethod() {
            return null;
        }
    }

    public static void main(String[] args) {
        IntfFactory intfFactory = IntfFactory.staticFactory();
        intfFactory.defaultMethod();

    }
}
