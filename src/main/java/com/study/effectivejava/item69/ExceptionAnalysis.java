package com.study.effectivejava.item69;

import org.springframework.util.StopWatch;

public class ExceptionAnalysis {

    public static void main(String[] args) {
        Range[] range = createRangeArr(100);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("예외잘못쓴것");
        try {
            int i = 0;
            while (true) {
                range[i++].doSomething();
            }
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            // jvm이 아래 구문들을 다 여기로 이동시킴.. 그렇다보니.. ArrayIndexOutOfBoundsException 만들어야하니깐 늦을수 밖에 없지않을까..
        }
        stopWatch.stop();

        range = createRangeArr(100);
        stopWatch.start("정상 루프");
        for (Range ran : range) {
            ran.doSomething();
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }

    private static Range[] createRangeArr(int arrayCnt) {
        Range[] ranges = new Range[arrayCnt];

        for (int i = 0; i < arrayCnt; i++) {
            ranges[i] = new Range();
        }

        return ranges;
    }

    private static class Range {
        public void doSomething() {
        }

    }
}
