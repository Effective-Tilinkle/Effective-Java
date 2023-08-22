package com.study.effectivejava.item80;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceAnalysis {
    public static void main(String[] args) throws InterruptedException {
        Callable<String> sleep3 = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("3초짜리 작업완료");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "sleep3";
        };

        Callable<String> sleep5 = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("5초짜리 작업완료");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "sleep5";
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            String result = executorService.invokeAny(List.of(sleep3, sleep5));
            System.out.println(result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
//        executorService.awaitTermination(3,TimeUnit.SECONDS);
    }
}
