package com.study.effectivejava.item81;

import org.springframework.util.StopWatch;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        // 그냥 get으로 가져오는것과 putIfAbsent로 데이터 가져오는것의 성능차이
        // => get이 더 빠르다.. putIfAbsent에는 get보다 리턴까지 if문을통해 검사하는게 좀더 많긴하니..

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

        concurrentHashMap.put("key", "value3");


        StopWatch stopWatch = new StopWatch();

        stopWatch.start("putIfAbsent");
        for (int i = 0; i < 1_000_000; i++) {
            concurrentHashMap.putIfAbsent("key", "value");
        }
        stopWatch.stop();

        stopWatch.start("그냥 get");
        for (int i = 0; i < 1_000_000; i++) {
            concurrentHashMap.get("key");
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }
}
