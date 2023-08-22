package com.study.effectivejava.item79;

import com.study.effectivejava.item18.Composition;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OverSyncAnalysis {
    public static void main(String[] args) {
        /*
        동기화 메서드나 동기화 블록 안에서는 제어를 절대로 클라이언트에게 양도해주면 안된다!
        즉, 클라이언트가 오버라이딩 혹은 람다로 넘겨줄 수 있는 부분을 동기화 메서드나 동기화 블럭 안에서 사용하지마라!
        만약, 동기화 메서드나 동기화 블록안에서 클라이언트 제어권을 넘겨주면, 아래와 같은 응답 불가와 안전실패가 날 수 있다
          - 아래 안전실패의 원인은 지워지면 안될 list의 데이터가 지워진것으로, 불변식을 깨뜨린것(다행이도 예외가 발생하여 클라이언트가 이를 인지할 수 있는 상황)
          - 응답 불가 원인은 데드락..
         */

        SetObserver<Integer> 문제없음 = (s, e) -> System.out.println(e);
        SetObserver<Integer> 에러유발_안전실패 = new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    set.removeObserver(this); // removeObserver 메서드에는 synchronized가 있어서 락을 획득해야하는데, 현재 added 메서드 호출하기전에도 동일한 객체에서의 락을 획득한 상태로 진입하였기에, 더이상 추가적으로 락을 획득하지않고 진입이 가능하다..(자바는 재진입가능) 그래서 loop돌고 있던 list를 제거하여 문제발생
                }
            }
        };

        SetObserver<Integer> 에러유발_응답불가_교착상태 = new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    try {
                        executorService.submit(() -> set.removeObserver(this)).get(); // 안전실패예시와는 달리, 새로운 스레드가 락을 획득하려하는데, 현재 added 메서드를 호출하기전에 이미 락이 잡혀있는 객체이기에, 데드락 상태가되어, 응답불가 상태가 된다 (당연, get()메서드를 사용하지않았다면 데드락은 안되겠지..)
                    } catch (InterruptedException | ExecutionException e) {
                        throw new AssertionError(e);
                    } finally {
                        executorService.shutdown();
                    }
                }
            }
        };


        ObservableSet<Integer> set = new ObservableSet<>(new HashSet());

//        set.addObserver(문제없음);
        set.addObserver(에러유발_안전실패);
//        set.addObserver(에러유발_응답불가_교착상태);
        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }

    static class ObservableSet<E> extends Composition.ForwardingSet<E> {

        public ObservableSet(Set set) {
            super(set);
        }

        private final List<SetObserver<E>> observers = new ArrayList<>();

        public void addObserver(SetObserver<E> observer) {
            synchronized (observers) {
                observers.add(observer);
            }
        }

        public void removeObserver(SetObserver<E> observer) {
            synchronized (observers) {
                observers.remove(observer);
            }
        }

        private void notifyElementAdded(E element) {
            synchronized (observers) {
                for (SetObserver<E> observer : observers) {
                    observer.added(this, element);
                }
            }
        }

        @Override
        public boolean add(E element) {
            boolean added = super.add(element);

            if (added) {
                notifyElementAdded(element);
            }
            return added;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean result = false;

            for (E element : c) {
                result |= add(element);
            }
            return result;
        }
    }

    @FunctionalInterface
    interface SetObserver<E> {
        void added(ObservableSet<E> set, E element);
    }

    static class ObservableSetWithCopyOnWrite<E> extends ObservableSet<E> {

        public ObservableSetWithCopyOnWrite(Set<E> set) {
            super(set);
        }

        private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<>(); // 아래 동기화 로직이 다 사라졌다

        public void addObserver(SetObserver<E> observer) {
            observers.add(observer);
        }

        public void removeObserver(SetObserver<E> observer) {
            observers.remove(observer);
        }

        private void notifyElementAdded(E element) {
            for (SetObserver<E> observer : observers) {
                observer.added(this, element);
            }
        }

        @Override
        public boolean add(E element) {
            boolean added = super.add(element);

            if (added) {
                notifyElementAdded(element);
            }
            return added;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean result = false;

            for (E element : c) {
                result |= add(element);
            }
            return result;
        }
    }
}
