package com.study.effectivejava.item83;

public class InitCircular {

    public static void main(String[] args) {
        ClassA classA = new ClassA();
    }
    static class ClassA {
        public static final ClassB INSTANCE_B = new ClassB();
        public ClassA() {
            INSTANCE_B.doSomething(); // 아직 INSTANCE_B가 만들어지지 않은 상태인데, 초기화 순환성으로 인해 호출되어, NullPointerException 발생
            System.out.println("ClassA initialized");
        }
    }

    static class ClassB {
        public static final ClassA INSTANCE_A = new ClassA();
        public ClassB() {
            System.out.println("ClassB initialized");
        }

        public void doSomething() {
            System.out.println("???");
        }
    }
}
