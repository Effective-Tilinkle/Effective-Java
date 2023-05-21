package com.study.effectivejava.item45;

public class LambdaCapturingProve {
    private static Runnable runnable;
    public static void main(String[] args) {
        // 람다 캡처링은 별도 스레드이던, 아니던 항상 이루어지고 이루어져야만한다
        createRunnable();
        test();

    }

    private static void createRunnable() {
        int a=4; // a 는 stack에 저장

        runnable = () -> {
            int b = a; // 여기서 캡처링되어야만 아래 pop이후에도 값이 유지되어 b를 4로 프린트할수가있음
            System.out.println(b);
        };

        // a는 메서드 종료와 함께 pop
    }

    public static void test() {
        runnable.run();
    }

}
