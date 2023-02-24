package com.study.effectivejava.item6;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegExPerformance {
    @Test
    void test() {
        String regEx = "(?:https?:\\/\\/)?(?:www\\.)?youtu.be\\/([-\\w]+)";
        Pattern compile = Pattern.compile(regEx);
        String target1 = "https://www.youtu.be/-ZClicWm0zM";

        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < 10000; i++) {
            assertTrue(compile.matcher(target1).matches());
        }
        sw.stop();
        sw.start();
        for (int i = 0; i < 10000; i++) {
            assertTrue(target1.matches(regEx));
        }
        sw.stop();
        System.out.println(sw.prettyPrint());
    }
}
