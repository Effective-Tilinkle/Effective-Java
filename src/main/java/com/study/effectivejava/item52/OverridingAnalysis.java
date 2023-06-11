package com.study.effectivejava.item52;

public class OverridingAnalysis {
    static class Super {
        public void overridingMethod() {
            System.out.println("super");
        }
    }

    static class Sub extends Super {
        @Override
        public void overridingMethod() {
            System.out.println("sub");
        }
    }

    public static void main(String[] args) {
        Super s = new Sub();
        s.overridingMethod();
    }
}
