package com.study.effectivejava.item81;

public class ThreadNotifyTest {
    public static void main(String[] args) throws InterruptedException {

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
