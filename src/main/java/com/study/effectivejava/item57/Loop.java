package com.study.effectivejava.item57;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Loop {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }};

//        remove_쓰레기(list);
        remove_정상(list);
    }

    static void remove_쓰레기(List<Integer> list) {
        for (Integer integer : list) {
            if (integer == 2 || integer == 3) {
                list.remove(integer); // 컴파일러가 내부적으로 iterator.hasNext로 다음 원소를 계속 찾도록 되어있는데.. list.remove로 지우니.. 2를 지운순간 사이즈는 2개가 되고, iterator의 hasNext는 없게되어 원소 3을 넘어가버린다..
            }
        }

        System.out.println(list);
    }

    static void remove_정상(List<Integer> list) {
        for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); ) {
            Integer integer = iterator.next();
            if (integer == 2 || integer == 3) {
                iterator.remove();
            }
        }

        System.out.println(list);
    }

    static void for_좋은_관용구() {
        for (int i = 0, n = expensiveComputation(); i < n; i++) { // 이렇게 사용하면 n은 i의 한계값이기때문에 블럭 밖에서 쓸 필요도 없고, 그렇다고 계속 블록 안에서 n을 계산해서 확인할 필요도 없다
            // ...
        }
    }

    private static int expensiveComputation() {
        return 10;
    }
}
