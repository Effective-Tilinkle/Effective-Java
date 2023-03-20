package com.study.effectivejava.item20;

public class SkeletalImplementation {
    /*

    // AbstractCollection 내부 구조
    public abstract class AbstractCollection<E> implements Collection<E> {
        // ...
        public boolean contains(Object o) { // Collection 인터페이스에서 정의한 내용을 여기서(골격구현 클래스) 구현해줌
            Iterator<E> it = iterator();
            if (o==null) {
                while (it.hasNext())
                    if (it.next()==null)
                        return true;
            } else {
                while (it.hasNext())
                    if (o.equals(it.next()))
                        return true;
            }
            return false;
        }
        // 위의 contains는 미리 구현되어있고(template), iterator만 하위클래스에서 구현해주면되는데, 이렇게 알고리즘의 골격을 정의하며, 알고리즘의 여러단계중 일부는 서브클래스에서 구현하도록 만드는 패턴이 템플릿 메서드 패턴이라 부른다

        // ...

        public abstract Iterator<E> iterator(); // Iterable 인터페이스에서 정의한 메서드는 AbstractCollection을 사용하는 client가 반드시 구현하도록 만들어놓음. 이렇게 재정의가 필요한 몇몇 부분만 클라이언트가 만들게되면, AbstractCollection에서 제공해주는 여러 기능들을 빠르게 사용가능

        // ...

        public String toString() { // 골격 구현 클래스에서 toString을 재정의해야한다.. 인터페이스에서 default 메서드로 Object의 메서드를 재정의해서는 안된다
            Iterator<E> it = iterator();
            if (! it.hasNext())
                return "[]";

            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (;;) {
                E e = it.next();
                sb.append(e == this ? "(this Collection)" : e);
                if (! it.hasNext())
                    return sb.append(']').toString();
                sb.append(',').append(' ');
            }
        }
    }

    // Collection
    public interface Collection<E> extends Iterable<E> {
        // ...
        boolean contains(Object o);

        // ...
    }

    // Iterable
    public interface Iterable<T> {
        // ...
        Iterator<T> iterator(); // 기반 메서드.. (골격구현 클래스인 AbstractCollection의 추상메서드)

        // ...
    }

     */
}
