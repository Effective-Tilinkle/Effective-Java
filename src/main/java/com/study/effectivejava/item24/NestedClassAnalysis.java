package com.study.effectivejava.item24;

public class NestedClassAnalysis {
    private int privateField_outer;
    int instanceField_outer;
    static int staticField_outer;

    void instanceMethod_outer() { }

    static void staticMethod_outer() { }

    void 익명클래스_비정적문맥() {
        int localVar = 3;
        localVar = 5;

        StaticMemberClass anonymousClass = new StaticMemberClass() { // 익명 클래스는 쓰이는 시점에 인스턴스 만들어짐
//            static int staticVar = 3; // 컴파일 에러.. 정적멤버 가질 수 없음
            final static int finalStaticVar = 3;  // 상수는 가질수 있음
            int a = 5;
            void newMethod() {
                instanceField_outer++;
                int a = instanceField_outer;
                int b = super.memberField;
                super.method(); // 상위타입 멤버 호출가능

                int c = staticField_outer;
                staticMethod_outer();

//                int d = localVar; // 컴파일에러.. localVar이 final 일때만 가능.. 로직상 중간에 변경하는 부분 없으면 알아서 final로 가져감
            }
        };
    }

    static void 익명클래스_정적문맥() {
        int localVar = 3;
        localVar = 5;

        StaticMemberClass anonymousClass = new StaticMemberClass() { // 익명 클래스는 쓰이는 시점에 인스턴스 만들어짐
//            static int staticVar = 3; // 컴파일 에러.. 정적멤버 가질 수 없음
            final static int finalStaticVar = 3;  // 상수는 가질수 있음

            int a = 5;
            void newMethod() {
//                int a = instanceField_outer; // 컴파일에러.. static 메서드안에서 사용되었으므로, 바깥 클래스의 인스턴스 변수 가질수 없음 (인스턴스와 static 간의 초기화 시점이다르니..)
                int b = super.memberField;
                super.method(); // 상위타입 멤버 호출가능

                int c = staticField_outer;
                staticMethod_outer();

//                int d = localVar; // 컴파일에러.. localVar이 final 일때만 가능.. 로직상 중간에 변경하는 부분 없으면 알아서 final로 가져감
            }
        };

//        anonymousClass.newMethod(); // 컴파일에러.. 익명클래스 자신의 메서드의 인스턴스는 호출불가.. 익명클래스가 구현한 상위클래스만 사용가능
        anonymousClass.method(); // StaticMemberClass로 타입을 가지고있으니 어찌보면 당연한것..
    }

    void 지역클래스() {
        class LocalClass { // 유효범위가 지역변수와 동일함, 클래스이름이 있음
//            static int staticVar = 3; // 컴파일 에러.. 정적멤버 가질 수 없음 (멤버변수, 멤버메서드 모두)
            final static int finalStaticVar = 3;  // 상수는 가질수 있음
            int localClassMemberField = 5;
            int localClassMemberField2 = instanceField_outer;

            public void localClassMethod() {
                NestedClassAnalysis.this.instanceMethod_outer();
                NestedClassAnalysis.staticMethod_outer();
            }

        }

        LocalClass localClass = new LocalClass();
    }

    static class StaticMemberClass { // 클래스 내부에 있다는 점과 다르고, 거의 일반 클래스와 동일
        static int staticMemberField = 0;
        int memberField = 0;

        void method() {
            int a = new NestedClassAnalysis().privateField_outer; // 바깥클래스의 private 필드 접근가능
//            int b = instanceField_outer; // 컴파일에러 바깥클래스 인스턴스 변수 참조불가 (인스턴스 메서드도 마찬가지)
            int c = staticField_outer; // 당연 static은 모두 접근가능
        }
    }

    class NonStaticMemberClass {
        //        static int staticMemberField = 5; 컴파일에러.. static을 사용하기위해서는 final 로 선언해야함
        final static int staticMemberField = 0;
        int memberField;

        void method() {
            NestedClassAnalysis.this.instanceMethod_outer();
            int a = NestedClassAnalysis.this.instanceField_outer;
            int b = NestedClassAnalysis.staticField_outer;
        }
    }
}
