package com.study.effectivejava.item81;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadStateTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            RestTemplate restTemplate = new RestTemplate();
            System.out.println("api call (restTemplate) start");
            String result = restTemplate.getForEntity("http://localhost:8888/get", String.class).getBody();
            System.out.println("api (restTemplate) return value : " + result);

            System.out.println("queue.poll start");
            BlockingQueue<String> queue = new LinkedBlockingQueue<>();
            try {
                queue.poll(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("queue.poll end");

            System.out.println("sleep start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sleep end");

            System.out.println("sync start");
            Sync sync = new Sync();
            sync.createNewThreadAndCallDoSomething();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sync.doSomethingWithSync(); // 동기화 블럭안에 들어가서 기다리는 중에는 BLOCKED
            System.out.println("sync end");

            System.out.println("api call (webclient) start");
            WebClient webClient = WebClient.builder().build();
            String r = webClient.get()
                    .uri("http://localhost:8888/get")
                    .exchangeToMono(res -> res.bodyToMono(String.class))
                    .block();
            System.out.println("api (webclient) return value : " + r);


            System.out.println("socket test start");
            new Thread(() -> createServerSocket()).start();
            try {
                Socket socket = new Socket("localhost", 9999);
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    bufferedWriter.write("hello\n");
                    bufferedWriter.flush();

                    String world = bufferedReader.readLine(); // 여기서 데이터를 읽기위해서 block되는데, Thread의 상태는 Runnable로 나온다. 실제로 os 상에서는 waiting 하는 동작이라도 vm에서는 Runnable로 나올 수 있다
                    /*
                    public static final Thread.State RUNNABLE
                    Thread state for a runnable thread. A thread in the runnable state is executing in the Java virtual machine but it may be waiting for other resources from the operating system such as processor.

                    참고 : https://stackoverflow.com/questions/48968984/java-threads-state-when-performing-i-o-operations
                          https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.State.html#RUNNABLE
                     */
                    if (world.equals("world")) {
                        System.out.println("socket test end");
                        socket.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        });

        t.start();

        while (true) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Thread state : " + t.getState());

            if (!t.isAlive()) {
                break;
            }
        }
    }

    private static void createServerSocket() {
        ServerSocket s = null;
        try {
            s = new ServerSocket(9999);
            Socket accept = s.accept();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()))) {

                String hello = bufferedReader.readLine();
                if (hello.equals("hello")) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bufferedWriter.write("world\n");
                    bufferedWriter.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {s.close();} catch (IOException e) {e.printStackTrace();}
        }
    }

    static class Sync {
        Object obj = new Object();

        public void doSomethingWithSync() {
            synchronized (obj) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void createNewThreadAndCallDoSomething() {
            new Thread(() -> {
                doSomethingWithSync();
            }).start();
        }
    }

}
