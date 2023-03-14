package com.study.effectivejava.item18;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class AbusingInheritanceTest {

    @Test
    void testProperties() {
        Properties p = new Properties();
        p.setProperty("hi","jh");

        assertTrue("jh".equals(p.getProperty("hi")));

        p.put("hi",new PropAbnormalValue("shit")); // Properteis 클래스의 불변식을 깨뜨리는 포인트.. Properties가 HashTable<Object,Object>를 상속하여서, HashTable의 메서드들이 노출되고있다. 즉, 원래 String 값만 들어가야하는데 Object가 들어가도록 허용이 되는게 문제..ㅍㅍ

        assertNull(p.getProperty("hi")); // 정상적이지 않은게 들어왔으니.. null을 반환함..
    }

    static class PropAbnormalValue {
        private String str;

        public PropAbnormalValue(String str) {
            this.str = str;
        }
    }
}