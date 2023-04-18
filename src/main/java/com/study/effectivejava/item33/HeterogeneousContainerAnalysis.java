package com.study.effectivejava.item33;

import jdk.jfr.AnnotationElement;

import java.lang.annotation.Annotation;
import java.util.*;

public class HeterogeneousContainerAnalysis {
    static class Favorites {
        private Map<Class<?>, Object> favorites = new HashMap<>(); // (1)
        // private Map<?, Object> favorites = new HashMap<>(); // (2)

        public <T> void putFavorite(Class<T> type, T instance) { // (4)
            favorites.put(type, instance);
//            favorites.put(type, type.cast(instance)); // (3)
        }

        public <T> T getFavorite(Class<T> type) {
            return type.cast(favorites.get(type));
        }

        /*
         (1) Map이 아니라, Key(Class)에 와일드카드타입이 있는것! => 키가 서로다른 매개변수화 타입이므로 여러 타입을 받을 수 있게됐다!
         (2) 이렇게되면 Map이 와일드카드타입이기때문에 key에 null외에는 못들어간다..
         (3) Favorites가 타입불변식을 어기는 일이 없도록 보장하려면 value에 cast를 사용하면된다!
           - cast는 형변환 연산자의 동적 버전.. (즉, 런타임시에 가능한..)
           - 로타입 혼용해서 쓰다가 타입 잘못 넣는것 방지가능. 즉, 런타임에 타입 안정성 확보
           - 이게 가능한것은 결국 런타임에도 타입토큰을 통해서 정보를 알고있기때문에, 컴파일타임과 런타임에 타입정보를 동일하게 가지는 즉, 실체화가 가능하기때문이다
         (4) 실체화 불가타입은 클래스 리터럴 사용불가한 제약이 있음 List<String>.class 나 List<Integer>.class는 모두 불가..
           - 슈퍼타입 토큰으로 어느정도해결.. (완벽히는 해결못한다는데, 한계점은 찾아보자..)


         */
    }

    public static void main(String[] args) {
        Favorites favorites = new Favorites();
        favorites.putFavorite((Class) Integer.class, "hi"); // 컴파일 경고뜸

        /////////////
        Set<String> strings = Collections.checkedSet(new HashSet<>(), String.class);

        /*
        // CheckedCollection 내부
        static class CheckedCollection<E> implements Collection<E>, Serializable {
            // ...
            @SuppressWarnings("serial") // Conditionally serializable
            final Class<E> type;

            // ...

            public boolean add(E e)          { return c.add(typeCheck(e)); }

            @SuppressWarnings("unchecked")
            E typeCheck(Object o) {
                if (o != null && !type.isInstance(o))       // 실체화 하는곳.. 이를 통해서 클라이언트가 제네릭과 로타입을 섞어쓸때에 잘못된 타입의 원소를 넣지 못하게 추적하는데 도움을 준다
                    throw new ClassCastException(badElementMsg(o));
                return (E) o;           // type.cast 를 사용하진않음..
            }

            // ...
        }

         */
    }

    // 한정적 타입토큰(class 리터럴사용)을 받는데, 비한정적 타입토큰은 어떻게 넘겨줄수 있을까?
    static Annotation getAnnotation(AnnotationElement element, String annotationTypeName) {
        Class<?> annotationType = null;
        try {
            annotationType = Class.forName(annotationTypeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

//        return element.getAnnotation(annotationType); // (1)
        return element.getAnnotation(annotationType.asSubclass(Annotation.class)); // (2)

        /*
        (1) element.getAnnotation은 매개변수로 한정적 타입토큰(Class<? extends Annotation>)을 사용하기때문에, Class<?> 타입이 인자로들어오려하면 컴파일 에러가 난다
        (2) Class 클래스는 asSubclass 메서드를 통해서 인자로 들어오는 타입의 클래스로 동적 형변환(Class<? extends U>)을 해준다 (여기서 형변환 된다는것은 annotationType 클래스가 Annotation 클래스의 하위클래스라는뜻)
         */
    }
}
