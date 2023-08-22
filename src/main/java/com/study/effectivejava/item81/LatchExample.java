package com.study.effectivejava.item81;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;

public class LatchExample {
    public static void main(String[] args) {

    }

    static long time(Executor executor, int concurrency, Runnable runnable) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown();
                try {
                    start.await();
                    runnable.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 요게 왜 필요한지 좀더 살펴보자..
                } finally {
                    done.countDown();
                }

            });
        }


        ready.await();
        long startNanos = System.nanoTime();
        start.countDown();
        done.await();

        return System.nanoTime() - startNanos;
    }

}
