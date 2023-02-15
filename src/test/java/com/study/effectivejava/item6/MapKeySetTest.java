package com.study.effectivejava.item6;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapKeySetTest {
    @Test
    void keyset으로_리턴받은_set은_동일할수있다() {
        Map<MapKey, MapValue> map = new HashMap<>();

        map.put(new MapKey("jh1"), new MapValue("jh_val"));
        map.put(new MapKey("jh2"), new MapValue("jh_val"));
        map.put(new MapKey("jh3"), new MapValue("jh_val"));

        Set<MapKey> mapKeys1 = map.keySet();
        Set<MapKey> mapKeys2 = map.keySet();

        assertTrue(mapKeys1 == mapKeys2);

    }

    @Test
    void keyset으로_리턴된_요소를_수정하면_당연_모두수정된다() {
        Map<MapKey, MapValue> map = new HashMap<>();

        map.put(new MapKey("jh1"), new MapValue("jh_val"));
        map.put(new MapKey("jh2"), new MapValue("jh_val"));
        map.put(new MapKey("jh3"), new MapValue("jh_val"));

        Set<MapKey> mapKeys1 = map.keySet();
        Set<MapKey> mapKeys2 = map.keySet();

        assertThat(mapKeys2)
                .doesNotContain(new MapKey("jh5"));

        for (MapKey mapKey : mapKeys1) {
            if(mapKey.getKey().equals("jh1")) {
                mapKey.setKey("jh5");
            }
        }

        assertThat(mapKeys2)
                .contains(new MapKey("jh5"));

    }

    static class MapKey {
        String key;

        public MapKey(String key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MapKey mapKey = (MapKey) o;
            return Objects.equals(key, mapKey.key);
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) { // 요런 setter는 바람직하지않은 예
            this.key = key;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    static class MapValue {
        String value;

        public MapValue(String value) {
            this.value = value;
        }
    }
}
