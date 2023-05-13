package com.study.effectivejava.item37;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class EnumMapAnalysis {
    public static void main(String[] args) {
        EnumMap<Operation, String> enumMap = new EnumMap<>(Operation.class);

        /*
        // enumMap 내부

        public class EnumMap<K extends Enum<K>, V> extends AbstractMap<K, V> implements java.io.Serializable, Cloneable {
            // ...
            public EnumMap(Class<K> keyType) { // 한정적 타입토큰 (Enum만 받도록..)을 사용하여 런타임시에 제네릭 타입에 대한 정보를 알 수 있음
                this.keyType = keyType;
                keyUniverse = getKeyUniverse(keyType);  // keyUniverse가 해당 Enum의 모든 상수정보를 참조하고있음.. 이를통해 Entry의 key에 대한 정보를 알 수 있음
                vals = new Object[keyUniverse.length];
            }

            // ...
            public V put(K key, V value) {
                typeCheck(key);

                int index = key.ordinal(); // vals(Object 배열)의 index로 ordinal을 사용
                Object oldValue = vals[index];
                vals[index] = maskNull(value);
                if (oldValue == null)
                    size++;
                return unmaskNull(oldValue);
            }
        }


         */


    }

    enum Operation {
        PLUS,
        MINUS
    }

    // 상전이 맵
    public enum Phase {
        SOLID, LIQUID, GAS;

        public enum Transition {
            MELT(SOLID, LIQUID),
            FREEZE(LIQUID, SOLID),
            BOIL(LIQUID, GAS),
            CONDENSE(GAS, LIQUID),
            SUBLIME(SOLID, GAS),
            DEPOSIT(GAS, SOLID);

            private final Phase from;
            private final Phase to;

            Transition(Phase from, Phase to) {
                this.from = from;
                this.to = to;
            }

            private static final Map<Phase, Map<Phase, Transition>> transitionMap
                    = Stream.of(values())
                    .collect(groupingBy(
                                    t -> t.from
                                    , () -> new EnumMap<>(Phase.class)
                                    , toMap(t -> t.to, Function.identity(), (v1, v2) -> v2, () -> new EnumMap<>(Phase.class))
                            )
                    );

            public static Transition from(Phase from, Phase to) {
                return transitionMap.get(from).get(to);
            }
        }
    }

}
