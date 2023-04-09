package com.study.effectivejava.item28;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MigrateArrayCastToList {
    static class ChooserWithoutGeneric {
        private final Object[] choiceArray;

        public ChooserWithoutGeneric(Collection choices) {
            this.choiceArray = choices.toArray();
        }

        public Object choose() { // 이를 호출하면 클라이언트가 형변환을 계속 해주어야함.. 잠재적 형변환 오류 발생구역.. => 제네릭을 사용하자!!
            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            return choiceArray[rnd.nextInt(choiceArray.length)];
        }
    }

    static class ChooserApplyGeneric_1<T> {
        private final T[] choiceArray;

        public ChooserApplyGeneric_1(Collection<T> choices) {
            this.choiceArray = (T[])choices.toArray(); // 컴파일에러는 없지만, unchecked cast 경고가 있음(런타임에 잠재적으로 ClassCastException 만날수있다는뜻) -> 즉, 컴파일러가 안전을 보장못한다는뜻.. 안전한게 확실하면 @SuppressWarnings("unchecked")를 달면되지만, 되도록 근본적인 해결로 경고를 없애자.
        }

        public T choose() {
            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            return choiceArray[rnd.nextInt(choiceArray.length)];
        }
    }

    static class ChooserApplyGeneric_complete<T> {
        private final List<T> choiceArray;

        public ChooserApplyGeneric_complete(Collection<T> choices) {
            this.choiceArray = new ArrayList<>(choices); // 비검사형 경고를 완전히 제거하기위해 배열대신 아예 리스트로! 조금 더 느릴 수 있지만 런타임에 ClassCastException 만날리 없다~~
        }

        public T choose() {
            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            return choiceArray.get(rnd.nextInt(choiceArray.size()));
        }
    }

    public static void main(String[] args) {
        ChooserApplyGeneric_1<String> stringChooserApplyGeneric_1 = new ChooserApplyGeneric_1<>(Arrays.asList("hi"));
        stringChooserApplyGeneric_1.choose();
    }
}
