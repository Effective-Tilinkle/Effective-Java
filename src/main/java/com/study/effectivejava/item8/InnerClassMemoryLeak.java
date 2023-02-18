package com.study.effectivejava.item8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class InnerClassMemoryLeak {

    // `HashMap`은 내부에 `KeySet`을 inner class 로 정의하여 `HashMap.keySet()` 호출시 Map의 key값들을 Set 형태로 return 해준다
    // 이를통해 inner class 가 어떻게 동작하는지 테스트
    // visualvm 활용

    /*
    결론: causeMemoryLeakCode()을 보면, HashMap에 계속 새로운 객체를 할당했으나, inner class인 keySet은
        containing 클래스인 HashMap을 계속 참조하기떄문에 HashMap은 GC가 수거해가지않았다.
        즉, keySet의 inner class가 List에 담게되면서, HashMap보다 더 긴 시간 살아있게되었고,
        그러다보니 HashMap은 그에 따라 같이 참조되어 GC가 수거하지않는다.
        이런 부분이 containing 클래스가 GC될 것을 기대할수있으나, 정상동작하지않고 예상치못한 메모리 릭을 유발할 수 있는 구간이 될 수 있다.

     */
    public static void main(String[] args) throws InterruptedException {
        notMemoryLeakCode();
//        causeMemoryLeakCode();


    }

    private static void notMemoryLeakCode() {
        for (int i=0;i<10;i++){
            HashMap<String, String> map = new HashMap<>();

            map.put(i+"",i+"");

            Set<String> strings = map.keySet();
        }
        System.out.println("hi");
    }

    private static void causeMemoryLeakCode() {
        List<Set<String>> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            HashMap<String, String> map = new HashMap<>();

            map.put(i+"",i+"");

            Set<String> strings = map.keySet();
            list.add(strings);
        }
        System.out.println("hi");
    }
}
