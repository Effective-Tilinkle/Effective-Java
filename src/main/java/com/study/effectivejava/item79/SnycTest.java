package com.study.effectivejava.item79;

public class SnycTest {

    public static void main(String[] args) {


        TempTest tempTest = new TempTest();

        new Thread(() -> {
            tempTest.doSomethingElse();
        }).start();

        tempTest.doSomething();



    }


    static class TempTest {
        Object obj = new Object();

        public void doSomething() {
            synchronized (obj) {
                System.out.println("동기화 작업중1");

                doSomethingElse();

            }
        }

        private void doSomethingElse() {
            synchronized (obj) {
                System.out.println("동기화 작업중2");
            }
        }
    }
}
