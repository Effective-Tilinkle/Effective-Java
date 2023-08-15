package com.study.effectivejava.item81;

import org.springframework.util.StopWatch;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class Temp {
    public static void main(String[] args) throws InterruptedException {
//        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
//
//        concurrentHashMap.put("key", "value3");
//
//
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start("putIfAbsent");
//        for (int i = 0; i < 10000; i++) {
//            concurrentHashMap.putIfAbsent("key", "value");
//        }
//        stopWatch.stop();
//
//        stopWatch.start("그냥 get");
//        for (int i = 0; i < 10000; i++) {
//            concurrentHashMap.get("key");
//        }
//        stopWatch.stop();
//
//
//        System.out.println(stopWatch.prettyPrint());
//
//        testReentrantLock();
//        testBlockingDequeTake();

        NotifyTest notifyTest = new NotifyTest();
        Thread thread1 = new Thread(() -> notifyTest.callWait());
        Thread thread2 = new Thread(() -> notifyTest.callWait());
        Thread thread3 = new Thread(() -> notifyTest.callWait());
        thread1.start();
        thread2.start();
        thread3.start();

        Thread.sleep(2000);
        notifyTest.callNotifyAll();

        while(true) {
            System.out.println(thread1.getName() +" : "+thread1.getState());
            System.out.println(thread2.getName() +" : "+thread2.getState());
            System.out.println(thread3.getName() +" : "+thread3.getState());

            Thread.sleep(1000);
            if(!thread1.isAlive() && !thread2.isAlive() && !thread3.isAlive()) {
                break;
            }
        }
//        notifyTest.callNotify();
//        new Thread(() -> notifyTest.callWait()).start();
    }

    static void testReentrantLock() {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try{
            reentrantLock.lock();
            System.out.println("hi");
        } finally {
            reentrantLock.unlock();
        }

    }

    static void testBlockingDequeTake() throws InterruptedException {
        BlockingDeque blockingDeque = new LinkedBlockingDeque();

        Thread t = new Thread(() -> {
            try {
                blockingDeque.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();

        Thread.sleep(1000);
        System.out.println(t.getState());

        try {
            blockingDeque.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class NotifyTest {
        Object obj = new Object();
        void callWait() {
            synchronized (obj) {
                System.out.println(Thread.currentThread().getName()+" : wait시작");
                try {
                    obj.wait();
                    System.out.println(Thread.currentThread().getName()+" : 깼음");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        void callNotifyAll() {
            synchronized (obj) {
                obj.notifyAll();
            }

        }

        void callNotify() {
            synchronized (obj) {
                obj.notify();
            }
        }
    }

}
