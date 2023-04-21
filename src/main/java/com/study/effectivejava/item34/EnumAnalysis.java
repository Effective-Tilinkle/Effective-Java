package com.study.effectivejava.item34;

import java.util.HashMap;
import java.util.Map;

public class EnumAnalysis {

    public enum Fruit {
        Apple("a"), // Apple은 public static final로 선언된 단 하나의 인스턴스
        Orange("b"),
        Mango("c");

        private final String s;
        public static final Map<String, Fruit> map = new HashMap<>();
        Fruit(String s) { // public 으로 선언불가
            this.s = s;
//            map.put(s, this); // 컴파일에러! enum 생성자에서는 "정적 필드 중"에서 상수 변수만 접근가능하고, 이외(ex. map)는 불가능! 열거타입 생성자가 실행되는 시점에는 정적 필드들이 아직 초기화되기 전이라, 자기 자신을 추가하지 못하게 하는 제약이 꼭 필요..
        }

        @Override
        public String toString() {
            return "toString은 재정의가능~";
        }
    }

    enum Singleton {
        INSTANCE, // 원소가 하나뿐인 열거타입은 싱글턴이다.
    }

    public static void main(String[] args) {
//        Fruit fruit = new Fruit(); // enum은 생성 불가
        Fruit apple = Fruit.Apple;
        Fruit orange = Fruit.Orange;
        Fruit mange = Fruit.Mango;
        System.out.println(mange.compareTo(orange));
        Enum e;

    }
}
