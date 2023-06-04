package com.study.effectivejava.item50;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BreakInvariant {

    public static void main(String[] args) {
        WrapperMap wrapperMap = new WrapperMap();
        CustomKey customKey = new CustomKey("key1");

        wrapperMap.addKeyValueWithoutCopy(customKey, "val1");
        wrapperMap.addKeyValueWithoutCopy(new CustomKey("key2"), "val2");

        System.out.println(wrapperMap.getValue(customKey)); // val1
        System.out.println(wrapperMap.getValue(new CustomKey("key2"))); // val2

        customKey.setKey("changeKey");
        System.out.println(wrapperMap.getValue(customKey)); // null => hashcode 불일치
        System.out.println(wrapperMap.getValue(new CustomKey("key1"))); // null => hashcode는 일치, equals 호출시 실패
        System.out.println(wrapperMap.getValue(new CustomKey("changeKey"))); // null => hashcode 불일치

        // CustomKey("key1")의 value인 "val1"은 다신 찾을수 없다...

    }

    static class WrapperMap {
        Map<CustomKey, String> map = new HashMap<>();

        public void addKeyValueWithoutCopy(CustomKey customKey, String value) {
            map.put(customKey, value);
        }

        public String getValue(CustomKey customKey) {
            return map.get(customKey);
        }
    }

    static class CustomKey {
        String key;

        CustomKey(String key) {
            this.key = key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CustomKey customKey = (CustomKey) o;
            return Objects.equals(key, customKey.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
