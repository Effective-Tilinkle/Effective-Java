package com.study.effectivejava.item31;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class PECS {
    public static void main(String[] args) {
        // 타입 파라미터 E 가 생산자(producer - 제공자) 역할이면 extend ( PE )
        // 타입 파라미터 E 가 소비자(consumer) 역할이면 super ( CS )

        List<ScheduledFuture<?>> list = null;

//        E e = max_PECS적용안함(list); // 컴파일에러.. ScheduledFuture가 Comparable인데, ScheduledFuture에 직접 Comparable을 구현한게아닌, 이를 구현한 Delayed를 상속받아서 Comparable이 된다.. 그러나 제네릭에서는 PECS를 적용안하게되면 ScheduledFuture가 직접 Comparable을 구현해야만 컴파일 에러가 나지않는다(제네릭 특성인 불공변때문..)
        ScheduledFuture<?> scheduledFuture = max_PECS적용(list); // Comparable<? super E> 이기때문에 가능.

        List rawtypeList = new ArrayList();

        swap(rawtypeList,1,2);
    }

    static <E extends Comparable<E>> E max_PECS적용안함(List<E> list) {
        return null;
    }

    static <E extends Comparable<? super E>> E max_PECS적용(List<? extends E> list) {
        // 파라머터로 넘겨받는 list는 E를 제공해주는 생산자 역할이므로 extends
        // Comparable<E>는 E를 소비하는 역할이므로 super를 사용하여, Comparable<? super E>
        // <E extends Comparable<? super E>> 라는 것은 E 라는 타입은 Comparable 타입으로 한정하는데, 이 Comparable은 E 포함해서 상위 타입이면 된다는 뜻. 즉, E포함 상위타입중 Comparable 타입이 있으면 된다는것!
          // ~~컴파일러가 super 키워드가 있으면, 재귀로 상위를 찾을듯..~~
        return null;
    }

    /* max_PECS적용 static 메서드 바이트코드
      static <E extends java.lang.Comparable<? super E>> E max_PECS적용(java.util.List<? extends E>);
        descriptor: (Ljava/util/List;)Ljava/lang/Comparable;
        flags: ACC_STATIC
        Code:
          stack=1, locals=1, args_size=1
             0: aconst_null
             1: areturn
          LineNumberTable:
            line 29: 0
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0       2     0  list   Ljava/util/List;
          LocalVariableTypeTable:
            Start  Length  Slot  Name   Signature
                0       2     0  list   Ljava/util/List<+TE;>;
        Signature: #35                          // <E::Ljava/lang/Comparable<-TE;>;>(Ljava/util/List<+TE;>;)TE;


        // 마지막 Signature 부분의 chatGPT 설명
        설명하자면:
            1. <E::Ljava/lang/Comparable<-TE;>;>: 타입 매개변수 E를 정의하는 부분입니다. 여기서 E는 Comparable 인터페이스를 구현한 타입으로 제한되어 있습니다. Comparable 인터페이스는 -TE를 통해 하한 와일드카드(? super E)를 사용하여 표현되어 있습니다. 이는 E 타입 또는 그 하위 타입이 Comparable 인터페이스를 구현해야 함을 나타냅니다. // 하위타입이 아니라 상위타입인듯..
            2. (Ljava/util/List<+TE;>;): 메서드의 매개변수를 나타냅니다. 이 경우 List를 매개변수로 받는데, 리스트의 요소 타입은 +TE로 표현됩니다. 이는 상한 와일드카드(? extends E)를 사용하여 E 타입 또는 그 상위 타입으로 지정됩니다. // 상위타입이 아니라 하위타입인듯..
            3. TE;: 메서드의 리턴 타입입니다. 여기서는 E 타입을 반환한다는 것을 나타냅니다.
            // T는 그냥 컴파일러가 붙여주는거 같은데, 리턴타입이 E가 아닌 TE라고 기록하는지 모르겠음.. 챗gpt가 이상하게 설명해준건지도 확인해봐야할듯.. (공식문서보자..)Signature

     */


    static void swap(List<?> list, int i, int j) {

    }

    static void swap2(List list, int i, int j) {

    }
}
