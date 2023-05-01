package com.study.effectivejava.item39;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationAnalysis {

    /***
     * 테스트 메서드임을 선언하는 애너테이션
     * 매개변수 없는 정적 메서드 전용이다. // (컴파일 타임에 정적메세드가 아닌곳에 선언하는걸 잡으려면 애너테이션 처리기를 구현하여야함)
     */
    @Retention(RetentionPolicy.RUNTIME) // 메타어노테이션. Runtime시에도 유지되는 어노테이션. 이렇게 선언해야 Runtime시에 @Test를 인식할수있다..
    @Target(ElementType.METHOD) // 메타어노테이션. Method에만 달수있는 어노테이션이라는뜻
    public @interface Test { // 단순히 대상에 마킹만 하는 마커 어노테이션

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTest {
        Class<? extends Throwable>[] value(); // 한정적 타입 토큰 활용. Throwable 포함 하위클래스로 제한.. 즉, 모든 예외(와 오류) 타입을 수용하는것
    }

    static class Sample {
        @Test
        public static void m1() {
        }

        @Test
        @ExceptionTest(RuntimeException.class)
        public static void m2() { // 실패. 예외를 던지지않음
        }

        @Test
        @ExceptionTest(RuntimeException.class)
        public static void m3() {
            throw new RuntimeException("성공");
        }

        @Test
        public static void m4() {
            throw new RuntimeException("실패");
        }

        @Test
        public void m5() { // 실패
        } // 잘못사용.. 정적메서드아님

        public static void m6() {
        }

        @Test
        @ExceptionTest(value = {ArithmeticException.class, IllegalArgumentException.class})
        public static void m7() {
            throw new IllegalArgumentException("성공");
        }

        public static void m8() {
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;

        Class<?> testClass = Class.forName(Sample.class.getName());

        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++;

                try {
                    m.invoke(null);
                    if (m.isAnnotationPresent(ExceptionTest.class)) {
                        System.out.println(m + " 실패 : 예외를 던지지않음");
                        continue;
                    }
                    passed++;
                } catch (InvocationTargetException wrappedEx) { // 테스트 메서드가 예외를 던지면 리플렉션 메커니즘이 InvocationTargetException으로 감싸서 다시 던진다.. 그래서 getCause 메서드를 통해 메서드 호출시 발생한 진짜 에러를 꺼내와야한
                    Throwable realEx = wrappedEx.getCause();
                    if (m.isAnnotationPresent(ExceptionTest.class)) {
                        int oldPass = passed;

                        Class<? extends Throwable>[] exs = m.getAnnotation(ExceptionTest.class).value();
                        for (Class<? extends Throwable> ex : exs) {
                            if (ex.isInstance(realEx)) {
                                passed++;
                                break;
                            }
                        }

                        if (oldPass == passed) {
                            System.out.println(m + " 실패 : " + realEx);
                        }
                    } else {
                        System.out.println(m + " 실패 : " + realEx);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("잘못 사용한 @Test: " + m);
                }
            }
        }

        System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);
    }
}
