package com.study.effectivejava.item62;

public class ThreadLocalAnalysis {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
        stringThreadLocal.set("hi");

        ThreadLocal<String> stringThreadLocal2 = new ThreadLocal<>();
        stringThreadLocal2.set("hi2");

        System.out.println(stringThreadLocal.get());
        System.out.println(stringThreadLocal2.get());

        Thread t = new Thread(() -> {
            stringThreadLocal.set("hi3");
            System.out.println(stringThreadLocal.get());
        });
        t.start();
        t.join();

        System.out.println("다시 : " + stringThreadLocal.get());

        /*

        // ThreadLocal 내부

        public void set(T value) {
            Thread t = Thread.currentThread();
            ThreadLocalMap map = getMap(t);
            if (map != null) {
                map.set(this, value); // key로 ThreadLocal 자신을 셋팅
            } else {
                createMap(t, value);
            }
        }

        ThreadLocalMap getMap(Thread t) {
            return t.threadLocals;
        }

        void createMap(Thread t, T firstValue) {
            t.threadLocals = new ThreadLocalMap(this, firstValue);
        }
        */
    }
}
