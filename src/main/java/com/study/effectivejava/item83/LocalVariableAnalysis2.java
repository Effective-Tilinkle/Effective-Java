package com.study.effectivejava.item83;

import org.springframework.util.StopWatch;

public class LocalVariableAnalysis2 {

    public static void main(String[] args) {
//        InfiniteLoopWithLocalVar infiniteLoopWithLocalVar = new InfiniteLoopWithLocalVar();
//
//        new Thread(() -> {
//            infiniteLoopWithLocalVar.start();
//        }).start();
//
//        try {
//            Thread.sleep(2000);
//            infiniteLoopWithLocalVar.stop();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        InfiniteLoopWithoutLocalVar infiniteLoopWithoutLocalVar = new InfiniteLoopWithoutLocalVar();

        new Thread(() -> {
            infiniteLoopWithoutLocalVar.start();
        }).start();

        try {
            Thread.sleep(2000);
            infiniteLoopWithoutLocalVar.stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class InfiniteLoopWithLocalVar {
        private volatile Flag flag = new Flag();

        public void start() {
            Flag localFlag = flag;
            while (localFlag.isStopped) {

            }
        }

        public void stop() {
            flag.isStopped = true;
        }
    }

    static class InfiniteLoopWithoutLocalVar {
        private volatile Flag flag = new Flag();

        public void start() {

            while (!flag.isStopped) {

            }
        }

        public void stop() {
            flag.isStopped = true;
        }
    }

    static class Flag {
        boolean isStopped;
    }
}
