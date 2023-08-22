package com.study.effectivejava.item83;

import org.springframework.util.StopWatch;

public class LocalVariableAnalysis {

    //    public static void main(String[] args) {
//        StopWatch stopWatch = new StopWatch();
//
//        LazyInitWithLocalVar 지역변수할당_후_접근 = new LazyInitWithLocalVar();
//        LazyInitWithoutLocalVar 필드직접접근 = new LazyInitWithoutLocalVar();
//
//        stopWatch.start("인스턴스필드_지역변수할당_후_접근");
//        지역변수할당_후_접근.getField();
//        stopWatch.stop();
//
//        stopWatch.start("인스턴스필드 직접접근");
//        필드직접접근.getField();
//        stopWatch.stop();
//
//        System.out.println(stopWatch.prettyPrint());
//    }
    public static void main(String[] args) {
        final int REPEAT_COUNT = 5; // 원하는 반복 횟수
        for (int i = 0; i < REPEAT_COUNT; i++) {
            testWithLocalVar();
            testWithoutLocalVar();
        }
    }

    private static void testWithLocalVar() {
        StopWatch stopWatch = new StopWatch();
        LazyInitWithLocalVar instance = new LazyInitWithLocalVar();

        // 워밍업
        for (int i = 0; i < 100; i++) {
            instance.getField();
        }

        stopWatch.start("인스턴스필드_지역변수할당_후_접근");
        FieldType result = instance.getField();
        // 연산 결과 사용
//        System.out.println(result.str);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static void testWithoutLocalVar() {
        StopWatch stopWatch = new StopWatch();
        LazyInitWithoutLocalVar instance = new LazyInitWithoutLocalVar();

        // 워밍업
        for (int i = 0; i < 100; i++) {
            instance.getField();
        }

        stopWatch.start("인스턴스필드 직접접근");
        FieldType result = instance.getField();
        // 연산 결과 사용
//        System.out.println(result.str);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    static class LazyInitWithLocalVar {
        private volatile FieldType field;

        public FieldType getField() {
            FieldType result = field;
            if (result != null) {
                for (int i = 0; i < 100_000_000; i++) {
                    FieldType f = result;
                }
                return result;
            }

            synchronized (this) {
                if (field == null) {
                    field = new FieldType();
                }

                return field;
            }
        }
    }

    static class LazyInitWithoutLocalVar {
        private volatile FieldType field;

        public FieldType getField() {
            if (field != null) {
                for (int i = 0; i < 100_000_000; i++) {
                    FieldType f = field;
                }
                return field;
            }

            synchronized (this) {
                if (field == null) {
                    field = new FieldType();
                }

                return field;
            }
        }
    }

    private static class FieldType {
        String str = "a";
        Integer i = 10;
        Long l = 100L;
        Double d = new Double(100);
        String[] strArr = new String[100000];

    }

}
