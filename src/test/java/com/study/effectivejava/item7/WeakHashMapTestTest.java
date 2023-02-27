package com.study.effectivejava.item7;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WeakHashMapTestTest {
    @Test
    void test() throws InterruptedException {
        WeakHashMapWrapper weakHashMapWrapper = new WeakHashMapWrapper();
        weakHashMapWrapper.putTestKey(new WeakHashMapWrapper.TestKey("abc"));
        weakHashMapWrapper.putTestKey(new WeakHashMapWrapper.TestKey("efg"));

        WeakHashMapWrapper.TestKey abc = weakHashMapWrapper.getTestKey("abc");

        System.out.println(abc);
        abc = null;

        System.gc();

        Thread.sleep(100);
        System.out.println(weakHashMapWrapper.getTestKey("abc"));
        assertNull(weakHashMapWrapper.getTestKey("abc"));
        assertNotNull(weakHashMapWrapper.getTestKey("efg"));
    }

    static class WeakHashMapWrapper {
        WeakHashMap<TestKey, Boolean> weakHashMap = new WeakHashMap<>();

        public void putTestKey(TestKey testKey) {
            weakHashMap.put(testKey, Boolean.TRUE);
        }

        public TestKey getTestKey(String key) {
            TestKey findKey = new TestKey(key);
            for (Map.Entry<TestKey, Boolean> entry : weakHashMap.entrySet()) {
                if(entry.getKey().equals(findKey)) {
                    return entry.getKey();
                }
            }

            return null;
        }

        public static class TestKey {
            private String key;

            public TestKey(String key) {
                this.key = key;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TestKey testKey = (TestKey) o;
                return Objects.equals(key, testKey.key);
            }

            @Override
            public int hashCode() {
                return Objects.hash(key);
            }
        }
    }

    @Test
    void weakHashMap_외부에서_참조하는변수가없다면_gc수행이후_모두제거된다() throws InterruptedException {
        WeakHashMap<String, String> map = new WeakHashMap<>();;
        map.put(new String("abc"), "abc");
        map.put(new String("efg"), "efg");

        System.gc();
        Thread.sleep(100);

        assertThat(map).isEmpty();
    }
}