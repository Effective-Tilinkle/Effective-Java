package com.study.effectivejava.item23;

public class ClazzWithTag {
    static class BadFigure { // 두가지 이상의 의미를 표현 할 수 있고, 현재의 의미를 표현해주는 태그달린 클래스
        enum Shape {RECTANGLE, CIRCLE}

        final Shape shape;  // 이걸 태그라고 생각하면되려나

        double length;
        double width;

        double radius; // RECTANGLE을 만드는 상황이면 쓰지 않지만 초기화는 되기에.. 메모리 낭비.. + 내가 만들고자하는 인스턴스에 불필요한 필드..

        public BadFigure(double radius) {
            this.shape = Shape.CIRCLE;
            this.radius = radius;
        }

        public BadFigure(double length, double width) {
            this.shape = Shape.RECTANGLE;
            this.length = length;
            this.width = width;
        }

        double area() {
            switch (shape) { // shape가 늘어나는곳마다 switch문을 계속 수정해줘야한다.. 혹여 다른곳에서도 switch문이 있다면 일일이 수정이 필요하고, 실수로 수정못하면 런타임에 에러..
                case RECTANGLE:
                    return length * width;
                case CIRCLE:
                    return Math.PI * (radius * radius);
                default:
                    throw new AssertionError(shape);
            }
        }
    }

    // 이런 클래스는 타입만으로는 현재 나타내는 의미를 알 길이 전혀없다.. 이게 RECTANGLE인지.. CIRCLE인지..
    // 즉, 태그달린 클래스는 장황하고, 오류내기쉽고, 비효율적.. => 계층구조 클래스로 표현하라(상속)


    static abstract class GoodFigure { // 루트가 되는 추상클래스
        abstract double area(); // 공통으로 쓰이니깐 추상메서드로 빼준다. 상태값(태그값)에 따라 분기처리되는 구간을 추상메서드로 빼주면 될듯
        // 태그 값에 상관없이 동작이 일정한 메서드들은 여기에 구현해주자..(ex. 템플릿)
        // 데이터 필드를 공통으로 사용하는게 있다면 당연 여기 루트 클래스로~
    }

    static class Circle extends GoodFigure {
        final double radius;

        Circle(double radius) {
            this.radius = radius; // 컴파일러가 생성시에 반드시 필요한 값을 넣도록 체크해주기때문에 필수값 누락될일도없다
        }

        public void specialMethod() {} // 하위 클래스만의 특정 메서드(혹은 변수)를 받을수도잇음

        @Override
        double area() { // 이로인해 switch 문 없다~ 또한 컴파일러가 area 메서드를 를 구현해야하는것을 알려준다
            return Math.PI * (radius * radius);
        }
    }

    static class Rectangle extends GoodFigure {
        final double length;
        final double width;

        Rectangle(double length, double width) {
            this.length = length;
            this.width = width;
        }

        @Override
        double area() {
            return length * width;
        }
    }

    static class Square extends Rectangle { // 이런 계층구조로 정사각형 만드는거 개꿀

        Square(double side) {
            super(side, side);
        }
    }
}
