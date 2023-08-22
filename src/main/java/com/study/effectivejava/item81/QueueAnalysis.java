package com.study.effectivejava.item81;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class QueueAnalysis {
    public static void main(String[] args) throws InterruptedException {
        Queue<String> queue = new LinkedBlockingDeque<>(1);
//        System.out.println(queue.add("a"));
//        System.out.println(queue.add("b")); // 큐에 사이즈 없으면 예외발생
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b")); // 큐에 사이즈 없으면 그냥 false 리턴

        testBlockingDequeTake();
    }

    static void testBlockingDequeTake() throws InterruptedException {
        BlockingDeque blockingDeque = new LinkedBlockingDeque();

        Thread t = new Thread(() -> {
            try {
                blockingDeque.take(); // 해당 스레드는 여기서 WAITING이 된다.. queue에 데이터를 넣고 꺠워야 활성화
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
}
