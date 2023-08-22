package com.study.effectivejava.item81;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static void main(String[] args) {
        testReentrantLock();
    }

    static void testReentrantLock() {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock(); // 락이잡혔음
        try{
            reentrantLock.lock(); // 기존 락을 잡고 있는 대상이기에 재진입 가능 (Reentrant lock)
            System.out.println("hi");
        } finally {
            reentrantLock.unlock();
        }

    }
}
