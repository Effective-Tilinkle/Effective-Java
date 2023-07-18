package com.study.effectivejava.item67;

import java.awt.*;

public class OptimizationAnalysis {
    public static void main(String[] args) {
        Component component = null;
        Dimension dimension = component.getSize();
        dimension.setSize(1,2); // 가변클래스.. getSize 메서드 호출시 계속 Dimension 객체를 새로 만들어서 전달(방어적 복사). => 불변으로 만드는게 이상적

        // getSize의 대안으로, getSize 호출시마다 Dimension을 새로 만드는대신, 아예 getWidth, getHeight를 호출하여 Dimension의 기본 타입 값을 반환하도록..(즉 괜히 Dimension을 만들어서 width와 height 가져오지말고, 바로 Component에서 전달해준다..) => 자바2부터
        component.getWidth();
        component.getHeight();

        // 하지만 이미 getSize를 호출하여 사용하고 있는 클라이언트는 자바2부터 개선은 되었지만 기존 코드를 수정하지못한다면 계속 가변클래스로 인해 나타나는 복사를 계속 경험해야한다.. => API 설계의 중요
    }
}
