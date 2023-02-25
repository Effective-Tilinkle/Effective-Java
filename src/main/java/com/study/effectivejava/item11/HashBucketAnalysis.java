package com.study.effectivejava.item11;

import java.util.HashMap;

public class HashBucketAnalysis {
    public static void main(String[] args) {
        HashMap map = new HashMap();

        /*

        public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {

            //...

            public V get(Object key) {
                Node<K,V> e;
                return (e = getNode(key)) == null ? null : e.value;
            }

            final Node<K,V> getNode(Object key) {
                Node<K,V>[] tab; Node<K,V> first, e; int n, hash; K k;
                if ((tab = table) != null && (n = tab.length) > 0 &&
                        (first = tab[(n - 1) & (hash = hash(key))]) != null) { // 1
                    if (first.hash == hash && // always check first node   // 2
                            ((k = first.key) == key || (key != null && key.equals(k))))
                        return first;
                    if ((e = first.next) != null) {
                        if (first instanceof TreeNode)
                            return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                        do {
                            if (e.hash == hash &&                          // 3
                                    ((k = e.key) == key || (key != null && key.equals(k))))
                                return e;
                        } while ((e = e.next) != null);
                    }
                }
                return null;
            }

            //...
        }

        1. Node<K,V>[] 라는 배열의 각 요소들은 해시 버킷이다. tab[(n - 1) & (hash = hash(key))] 을 통해서 해시 버킷을 찾게된다. 즉, "(n - 1) & (hash = hash(key))" 요게 해시 버킷을 찾는 로직이다. Node<K,V> 는 LinkedList 혹은 트리구조로 구성되어있는데, 이를 통해서 해시버킷이 같을때 해시코드가 같은 대상을 찾게 된다.

        2, 3. 모두 "== hash" 를 통해서 전달받은 인스턴스의 hash 값과 같은지 확인하고있다. 즉, 해시코드가 다른 엔트리끼리는 뒤의 동치성 비교(equals)도 시도하지않게 된다.


         */
    }
}
