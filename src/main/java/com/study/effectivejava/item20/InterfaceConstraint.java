package com.study.effectivejava.item20;

public class InterfaceConstraint {
    interface A {
        public static String a = null; // protected, private 컴파일에러, 인스턴스 필드 가질 수 없음
        String b = null; // 없으면 public static 붙은거와 동일

        public void pubInstanceMethod();
//      public static void pubStaticMethod(); 컴파일에러.. static은 항상 body 필요

        public static void aab() {
            privateStaticMethod();

//            privateInstanceMethod(); 컴파일 에러
        }

        default void defaultMethod() {
            privateStaticMethod();
            privateInstanceMethod();
        }

//        default boolean equals(Object o) { // 컴파일에러
//            return true;
//        }

        private static void privateStaticMethod() {}

        private void privateInstanceMethod() {}

//        protected void protectedInstanceMethod() {} 컴파일에러.. protected 불가
    }

}
