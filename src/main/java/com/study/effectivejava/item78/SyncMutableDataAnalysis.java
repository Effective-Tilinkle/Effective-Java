package com.study.effectivejava.item78;

import java.util.concurrent.atomic.AtomicInteger;

public class SyncMutableDataAnalysis {

    // ConcurrentHashMap은 어떻게 데이터 동기화하나
        /*
        // ConcurrentHashMap 내부

            // 값을 저장할때는 synchronized 사용
            final V putVal(K key, V value, boolean onlyIfAbsent) {
                if (key == null || value == null) throw new NullPointerException();
                int hash = spread(key.hashCode());
                int binCount = 0;
                for (Node<K,V>[] tab = table;;) {
                    Node<K,V> f; int n, i, fh; K fk; V fv;
                    if (tab == null || (n = tab.length) == 0)
                        tab = initTable();
                    else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                        if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value)))
                            break;                   // no lock when adding to empty bin
                    }
                    else if ((fh = f.hash) == MOVED)
                        tab = helpTransfer(tab, f);
                    else if (onlyIfAbsent // check first node without acquiring lock
                             && fh == hash
                             && ((fk = f.key) == key || (fk != null && key.equals(fk)))
                             && (fv = f.val) != null)
                        return fv;
                    else {
                        V oldVal = null;
                        synchronized (f) {      // 동일한 Node에 데이터를 put하는 경우만 동기화 되도록
                            if (tabAt(tab, i) == f) {
                                if (fh >= 0) {
                                    binCount = 1;
                                    for (Node<K,V> e = f;; ++binCount) {
                                        K ek;
                                        if (e.hash == hash &&
                                            ((ek = e.key) == key ||
                                             (ek != null && key.equals(ek)))) {

                        // ...
             }

            // 값을 읽어올때는 volatile을 사용
            static class Node<K,V> implements Map.Entry<K,V> {
                final int hash;
                final K key;
                volatile V val;             // key는 동일하나 해당 값이 변경될 수 있고, 그 값을 가져올때 동기화가 필요하니 volatile 사용
                volatile Node<K,V> next;    // HashMap 특성상, Hash값 같으면, 연결List로 구성되니, 해당 부분또한 값을 읽어올때 동기화가 필요하여 volatile 사용
                // ...
            }

         */



    // AtomicInteger는 어떻게 데이터를 동기화하나
    /*
    compareAndSwap 방식을 사용

    compareAndSwap관련 내용 chatGPT 답변
    => 비교-교환(compare-and-swap, CAS) 연산은 동시성 제어를 위한 중요한 원자적 연산입니다. CAS는 원자적으로 값을 읽고, 비교하고, 그 값이 기대하는 값과 일치하는 경우에만 새로운 값으로 업데이트하는 연산을 제공합니다.
    ### CAS의 작동 원리:
    CAS 연산은 대상 메모리 위치의 현재 값을 가져와 그 값이 예상 값과 같은지 확인합니다. 만약 값이 일치하면, 새로운 값으로 업데이트를 시도합니다. 이 모든 과정은 원자적으로 이루어져, 이 연산을 수행하는 동안 다른 스레드가 해당 메모리 위치를 변경할 수 없습니다.

    ### CAS의 장점:
    1. **비-블로킹:** CAS는 락(lock)을 사용하지 않으므로, 한 스레드가 연산을 수행하는 동안 다른 스레드가 블로킹되지 않습니다. 이는 높은 동시성을 필요로 하는 시스템에서 중요한 이점입니다.
    2. **스핀-잠금 해결:** 한 스레드가 연산을 수행하려 할 때, 다른 스레드가 락을 소유하고 있지 않더라도 스핀-잠금이 발생할 수 있습니다. CAS를 사용하면 이 문제를 해결할 수 있습니다.

    ### CAS의 단점:
    1. **ABA 문제:** 값 A를 가진 메모리 위치가 먼저 값 B로 변경되었다가 다시 값 A로 변경되는 경우, CAS 연산은 값이 변경되지 않았다고 판단할 수 있습니다. 이를 ABA 문제라고 합니다. 이 문제는 '버전 번호'나 '보조 태그' 등을 사용하여 해결할 수 있습니다.
    2. **일관성 실패:** CAS 연산이 성공하기 위해서는 여러 스레드가 동시에 같은 메모리 위치를 업데이트하지 않아야 합니다. 그러나 여러 스레드가 동시에 같은 메모리 위치를 업데이트하려고 시도하면, 한 스레드의 CAS 연산이 성공할 때까지 다른 모든 스레드의 CAS 연산이 실패합니다. 이는 '라이브-락' 문제를 일으킬 수 있습니다.

    따라서, CAS는 동시성 제어에 있어 중요한 도구이지만, 그 사용은 신중해야 합니다. 이러한 원자적 연산은 성능과 데드락 문제를 효과적으로 관리할 수 있지만, 위에서 언급한 단점을 고려해야 합니다.

    => AtomicInteger에서 incremetAndGet과 같은 증가만하는 경우는 ABA가 일어나지않는다.. 다시 A로 돌아갈일이없으니..
    => 라이브락은 프로세스나 스레드가 다른 스레드의 작입이 끝나기를 기다리면서 영원히 대기 상태에 머무는 현상..
      - 데드락은은 스레드나 프로세스가 서로 자원을 얻지못해 영원히 대기상태인데, 라이브락은 스레드나 프로세스가 실행상태에 있지만, 유용한 작업을 수행하지 못하고 계속해서 상태를 변경하는것.. (CAS에서는 계속 CAS연산이 실패하면서 루프를 타는..)
    => 백오프 알고리즘은 라이브락을 완화하기위한 방법으로, 재시도전에 일정시간 기다리는 방식
    => 스핀-잠금이란, 동시성 제어를 위한 한가지 기법으로, 특정 자원에 대한 접근이 가능해질때까지(즉, 락이 해제될떄까지) 계속해서 시도(회전)하는 방식. 이를 바쁜대기(busy-wait)이라고도 함.
      - 잠금해제까지 CPU시간을 계속 소비하기때문에 다른 작업을 수행못함. 그래서 보통 잠금이 매우 짧은 시간만 유지되는 경우 사용하는 방법..
     */

    public static void main(String[] args) throws InterruptedException {
        InfiniteLoop_synchronized infiniteLoop = new InfiniteLoop_synchronized();
        Thread t = new Thread(() -> {
            infiniteLoop.start();
        });
        t.start();

        Thread.sleep(1000);
        infiniteLoop.stop();

    }

    static class InfiniteLoop_volatile {
        volatile boolean isStop = false;
        int i = 0;
        public void start() {
            while(!isStop) {
                i++;
            }
        }

        public void stop() {
            this.isStop = true;
        }
    }

    static class InfiniteLoop_synchronized {
        boolean isStop = false;
        int i = 0;
        public void start() {
            while(!isStopped()) {
                i++;
            }
        }

        public synchronized void stop() { // synchronized 키워드를 혹여나, 여기만 적용하게된다면 정상 동작안함. 실제로 isStopped의 synchronized키워드를 통해서 가시성을 확보하는것이기에..
            this.isStop = true;
        }

        public synchronized boolean isStopped() {
            return this.isStop;
        }
    }

    static class InfiniteLoop_스레드sleep {
        boolean isStop = false;
        int i = 0;
        public void start() {
            while(!isStop) {
                i++;

                try {
                    Thread.sleep(100);
                    /*
                    chatGPT 해설
                    이 현상은 Thread.sleep() 메소드가 JVM과 운영체제의 스레드 스케줄링에 영향을 미치기 때문에 발생합니다. Thread.sleep() 메소드를 호출하면 현재 스레드는 지정된 시간 동안 일시정지되며, 이 시간 동안 다른 스레드가 실행될 수 있습니다. 이러한 컨텍스트 스위칭(Context Switching) 과정에서, JVM 또는 운영체제는 CPU 캐시를 메인 메모리와 동기화할 수 있습니다. 이렇게 되면, 한 스레드에서 변경한 변수의 값이 다른 스레드에서 보여질 수 있게 됩니다.
                    그러나 이는 volatile 키워드를 사용하는 것과는 다르게, JVM 또는 운영체제의 구체적인 구현에 따라 달라질 수 있습니다. 즉, Thread.sleep() 메소드를 사용하는 것만으로 변수의 가시성을 항상 보장할 수는 없습니다.
                    따라서 변수의 가시성을 보장하기 위해서는 volatile 키워드를 사용하는 것이 좋습니다. volatile 키워드는 Java 메모리 모델에 의해 명확히 정의되어 있으며, 이는 JVM의 구현에 관계없이 동일하게 적용됩니다.
                     */

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public void stop() {
            this.isStop = true;
        }
    }


    /*
    chatGPT 를 통한 volatile 관련 궁금내용 정리
    Q. volatile 키워드와 시스템메모리, cpu간의 관계
    => volatile 키워드의 역할을 이해하려면 CPU 캐시와 메인 메모리 간의 동작 방식에 대해 이해해야 합니다.
    오늘날의 대부분의 컴퓨터 아키텍처에서 CPU는 자체 캐시 메모리를 가지고 있습니다. 이 캐시 메모리는 CPU가 필요로 하는 데이터를 빠르게 제공하기 위한 것입니다. CPU가 데이터를 읽거나 쓸 때, 이 작업은 캐시 메모리에서 수행되며, 이 캐시는 주기적으로 메인 메모리와 동기화됩니다. 이러한 방식은 CPU의 성능을 크게 향상시키지만, 멀티스레드 환경에서는 문제가 될 수 있습니다.
    멀티스레드 환경에서, 각 스레드는 일반적으로 자신만의 CPU 캐시를 가집니다. 따라서, 하나의 스레드가 변수를 변경하면 이 변경 사항은 해당 스레드의 CPU 캐시에만 반영되고, 이는 즉시 다른 스레드의 CPU 캐시에는 반영되지 않습니다. 그 결과, 다른 스레드는 해당 변수의 최신 값을 보지 못하게 됩니다.
    이런 문제를 해결하기 위해 Java는 volatile 키워드를 제공합니다. 변수가 volatile로 선언되면, 해당 변수에 대한 모든 읽기와 쓰기 작업은 CPU 캐시를 거치지 않고 직접 메인 메모리를 통해 수행됩니다. 이를 통해, 하나의 스레드가 volatile 변수를 변경하면, 이 변경 사항은 즉시 모든 스레드에게 보이게 됩니다. 이는 모든 스레드가 항상 해당 변수의 최신 값을 볼 수 있음을 보장합니다.
    그러나, volatile 키워드는 이러한 가시성 문제만 해결하며, 복합 연산에 대한 원자성은 보장하지 않습니다. 즉, 여러 단계로 이루어진 연산이 중간에 다른 스레드에 의해 방해받지 않도록 하려면 추가적인 동기화 메커니즘(예: synchronized)이 필요합니다.

    Q. cpu가 싱글코어일때는?
    => 싱글 코어 CPU에서는 한 번에 하나의 스레드만이 실행될 수 있으므로, 일반적으로 volatile 키워드가 필요 없을 수 있습니다. 싱글 코어 CPU에서 여러 스레드가 '동시에' 실행되는 것은 운영 체제의 컨텍스트 스위칭(Context Switching) 덕분입니다. 이는 실제로는 스레드들이 빠르게 전환되며 실행되는 것이지, 동시에 실행되는 것이 아닙니다.
    그러나 이것은 스레드 스케줄링, JVM 구현, 그리고 Java 메모리 모델과 같은 여러 요소에 따라 달라질 수 있습니다. 특히, Java 메모리 모델은 volatile 변수에 대한 동작을 명확히 정의하고 있으며, 이는 싱글 코어 또는 멀티 코어 CPU에 관계없이 동일하게 적용됩니다.
    따라서, 실제로는 싱글 코어 CPU에서도 volatile 키워드가 필요한 경우가 있을 수 있습니다. 예를 들어, JVM이 최적화를 통해 변수의 읽기와 쓰기를 재정렬하는 경우, volatile 키워드는 이러한 재정렬을 방지하여 변수에 대한 읽기와 쓰기의 순서를 보장합니다.
    또한, Java 프로그램이 여러 코어를 가진 시스템에서 실행될 가능성이 있는 경우, volatile 키워드를 사용하여 프로그램이 올바르게 동작하도록 만드는 것이 좋습니다. 이는 코드의 이식성을 보장하는 중요한 방법입니다.

    Q. volatile키워드를 사용하는것과 JVM 최적화를 위한 재정렬에 대해서 좀더 알려줘
    => Java의 volatile 키워드는 Java 메모리 모델에 의해 정의되며, 이는 스레드 간의 통신에 대한 세부 사항을 정의합니다. 특히, volatile 키워드는 변수에 대한 읽기와 쓰기가 원자적(atomic)으로 발생하며, 이 작업이 항상 메인 메모리에서 수행된다는 것을 보장합니다. 또한 volatile 변수에 대한 읽기와 쓰기는 항상 순차적으로 수행되며, 이는 JVM이 이러한 작업을 재정렬하지 못하게 합니다.
    JVM 최적화 중 하나인 '재정렬'에 대해 설명하면, JVM은 실행 성능을 향상시키기 위해 다양한 최적화 기법을 사용합니다. 그 중 하나가 명령어 재정렬입니다. 이는 코드의 순서를 변경하여 CPU 파이프라이닝 등의 기법을 더 효과적으로 활용하고, CPU 캐시 히트율을 향상시키는 등의 이점을 제공합니다.
    그러나, 이러한 최적화가 멀티스레드 프로그램에서는 문제를 일으킬 수 있습니다. 예를 들어, 한 스레드가 변수 a와 b를 순차적으로 작성하고, 다른 스레드가 그 변수들을 순차적으로 읽는다고 가정해봅시다. JVM이 명령어 재정렬 최적화를 적용하여 변수 b의 쓰기가 a의 쓰기보다 먼저 발생하도록 변경하면, 읽는 스레드는 a와 b의 값을 예상치 못한 순서로 볼 수 있습니다.
    volatile 키워드는 이러한 문제를 해결합니다. volatile로 선언된 변수는 JVM이 해당 변수에 대한 읽기와 쓰기 작업의 순서를 변경할 수 없습니다. 따라서, 한 스레드가 volatile 변수를 순차적으로 작성하면, 다른 스레드는 그 변수들을 작성된 순서대로 보게 됩니다. 이는 멀티스레드 프로그램에서 변수의 일관성을 유지하는 데 중요합니다.
     */

}
