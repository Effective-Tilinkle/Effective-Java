package com.study.effectivejava.item80;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorServiceAnalysisTest {
    Callable<String> sleep3 = () -> {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3초짜리 작업완료");

        return "sleep3";
    };

    Callable<String> sleep5 = () -> {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("5초짜리 작업완료");

        return "sleep5";
    };

    ExecutorService executorService = Executors.newCachedThreadPool();
    @Test
    void testInvokeAll_모든태스크가_완료될때까지_기다린다() {
        try {
            List<Future<String>> futures = executorService.invokeAll(List.of(sleep3, sleep5)); // 여기서 바로 리턴되는게 아닌, 모든 작업이 완료되어야 리턴

            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInvokeAny_하나라도_작업완료되면_나머지_태스크들은_모두_종료된다() {
        try {
            String result = executorService.invokeAny(List.of(sleep3, sleep5));
            System.out.println(result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void teardown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(3,TimeUnit.SECONDS);
    }
}