package com.study.effectivejava.item19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HookAnalysis {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));

        List<String> sub = strings.subList(2, 4);
        sub.clear();

        System.out.println(strings.size());

        /*
        성능을 위해 hook 을 잘 활용하자.. removeRange는 hook 메서드인데, 이는 성능위해서 만들어진 내부에서만 사용하는 메서드

        // AbstractList 내부
        public void clear() {
            removeRange(0, size());
        }

        protected void removeRange(int fromIndex, int toIndex) {
            ListIterator<E> it = listIterator(fromIndex); // listIterator를 구하고,
            for (int i=0, n=toIndex-fromIndex; i<n; i++) {  // 계속 fromIndex와 toIndex까지 계속 지우게됨
                it.next();
                it.remove();                  // ListIterator의 remove 시간복잡도가 O(N)이면, 루프안에 루프가 되므로 시간복잡도는 O(N^2)
            }
        }

          => 결국 removeRange는 clear 메서드 호출시 성능향상을 위해서 만들어놓은것.. 즉, 성능개선이 필요하다면 removeRange를 적절하게 하위클래스에서 오버라이딩 하라!
                그에 대한 예시가 아래 ArrayList 내부의 removeRange
        간략하게 설명하면, ArrayList에서 SubList를 반환하였을때, 해당 Sublist로 clear를 호출하면 오버라이딩된 removeRange가 수행된다(참고로 subList를 clear 수행하면 원본 ArrayList에도 데이터 날아감)
        만약 subList.claer 호출시 SubList에 removeRange를 별도로 오버라이딩하지않았다면, ListIterator의 remove가 호출될것인데, 이렇게되면 계속해서 root(원본 ArrayList)의 remove가 수행될것이다. 그렇게되면 배열을 제거하고 계속 빈 공간을 없애기위해 계속 배열을 새로 생성하는구조가 된다. 하지만, 아예 removeRange를 오버라이딩하여, 아래와같이 shiftTailOverGap 메서드를 호출하게되면 단 한번의 배열 복사만 이루어진다..!

        // ArrayList 내부
        protected void removeRange(int fromIndex, int toIndex) {
            if (fromIndex > toIndex) {
                throw new IndexOutOfBoundsException(
                        outOfBoundsMsg(fromIndex, toIndex));
            }
            modCount++;
            shiftTailOverGap(elementData, fromIndex, toIndex);
        }

        private void shiftTailOverGap(Object[] es, int lo, int hi) {
            System.arraycopy(es, hi, es, lo, size - hi);
            for (int to = size, i = (size -= hi - lo); i < to; i++)
                es[i] = null;
        }

        // ArrayList.Sublist 내부
        private static class SubList<E> extends AbstractList<E> implements RandomAccess {
            private final ArrayList<E> root;
            //...

            protected void removeRange(int fromIndex, int toIndex) {  // 위의 removeRange를 재정의.. 물론 it.re
                checkForComodification();
                root.removeRange(offset + fromIndex, offset + toIndex);
                updateSizeAndModCount(fromIndex - toIndex);
            }

            public E remove(int index) {
                Objects.checkIndex(index, size);
                checkForComodification();
                E result = root.remove(offset + index);
                updateSizeAndModCount(-1);
                return result;
            }

            public ListIterator<E> listIterator(int index) {
                checkForComodification();
                rangeCheckForAdd(index);

                return new ListIterator<E>() {
                    int cursor = index;
                    int lastRet = -1;
                    int expectedModCount = SubList.this.modCount;

                    public boolean hasNext() {
                        return cursor != SubList.this.size;
                    }

                    @SuppressWarnings("unchecked")
                    public E next() {
                        checkForComodification();
                        int i = cursor;
                        if (i >= SubList.this.size)
                            throw new NoSuchElementException();
                        Object[] elementData = root.elementData;
                        if (offset + i >= elementData.length)
                            throw new ConcurrentModificationException();
                        cursor = i + 1;
                        return (E) elementData[offset + (lastRet = i)];
                    }

                    public void remove() {
                        if (lastRet < 0)
                            throw new IllegalStateException();
                        checkForComodification();

                        try {
                            SubList.this.remove(lastRet);
                            cursor = lastRet;
                            lastRet = -1;
                            expectedModCount = SubList.this.modCount;
                        } catch (IndexOutOfBoundsException ex) {
                            throw new ConcurrentModificationException();
                        }
                    }

                };
            }
        }

         */
    }
}
