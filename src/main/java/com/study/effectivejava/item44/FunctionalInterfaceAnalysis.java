package com.study.effectivejava.item44;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.BiPredicate;


public class FunctionalInterfaceAnalysis {
    public static void main(String[] args) {
        Map<String, String> map = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) { // 템플릿에서 변경이 필요한 부분을 하위 클래스에서 재정의하여 사용하도록 하는 템플릿 메서드 패턴
                return size() > 100;
            }
        };

        // 위의 내용을 NewLinkedHashMap(함수형 인터페이스)으로 새롭게 만든다면..
        NewLinkedHashMap<String, String> newLinkedHashMap = new NewLinkedHashMap<>((EldestEntryRemovalFunction<String, String>) (m, eldest) -> m.size() > 100);

        // 위의 내용을 표준함수형 인터페이스로 변경하면..
        NewLinkedHashMap<String, String> newLinkedHashMap_표쥰함수버전 = new NewLinkedHashMap<>((BiPredicate<Map<String, String>, Map.Entry<String, String>>) (m, eldest) -> m.size() > 100); // 표준함수형으로 만들었을때는 컴파일러가 타입추론이 어려워, 캐스팅을 통해 제네릭으로 정보를 알려줘야함.. 이게 단점이라면 단점이겠네..

        //////////////////////////////////////

        // 함수형 인터페이스를 인수로 받을때, 다중정의의 모호함
        ExecutorService executorService = null;
        executorService.submit(() -> setString("hi")); // ExecutorService.submit은 인수가 없는 람다식을 사용하는 Callable과 Runnable을 받을수있도록 오버로딩 되어있는데, 당연 컴파일러가 에러를 내진않지만, 이런식으로 사용하면 클라이언트 입장에서 Callable을 써서 결과값을 받을수있는것인지, Runnable을 써서 결과값을 받을수 없는지가 코드로 딱 보아서 파악하기 애매할듯..
        executorService.submit(() -> setObject("hi"));
        executorService.submit(() -> "hi");

        // 함수형 인터페이스의 이름만 다르고 동일한 람다형식을 받는 메서드가 오버로딩되어 있을 경우, 클라이언트가 별도의 형변환을 명시해주어야 사용가능한데, 해당 예시는 newLinkedHashMap을 보자


    }

    static Object setString(String str) {
        return new Object();
    }

    static void setObject(Object obj) {

    }

    static class NewLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

        private EldestEntryRemovalFunction<K, V> eldestEntryRemovalFunction;
        private BiPredicate<Map<K, V>, Map.Entry<K, V>> eldestEntryRemovalFunction_표준함수버전;

        public NewLinkedHashMap(EldestEntryRemovalFunction<K, V> eldestEntryRemovalFunction) {
            this.eldestEntryRemovalFunction = eldestEntryRemovalFunction;
        }

        public NewLinkedHashMap(BiPredicate<Map<K, V>, Map.Entry<K, V>> eldestEntryRemovalFunction_표준함수버전) { // 되도록이면 기존에 있는 표준함수형을 사용하자!
            this.eldestEntryRemovalFunction_표준함수버전 = eldestEntryRemovalFunction_표준함수버전;
        }

        /*
        void afterNodeInsertion(boolean evict) { // possibly remove eldest
            LinkedHashMap.Entry<K,V> first;
            if (evict && (first = head) != null && eldestEntryRemovalFunction.remove(this, first)) { // removeEldestEntry.remove(first) => eldestEntryRemovalFunction.remove(this, first) 로 변경
                K key = first.key;
                removeNode(hash(key), key, null, false, true);
            }
        }
        */
    }

    @FunctionalInterface
    interface EldestEntryRemovalFunction<K, V> {
        boolean remove(Map<K, V> map, Map.Entry<K, V> eldest);
    }


}
