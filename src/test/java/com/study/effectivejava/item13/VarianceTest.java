package com.study.effectivejava.item13;

import org.junit.jupiter.api.Test;

public class VarianceTest {
    @Test
    void 공변_배열() {
        Sub[] subs = new Sub[10];

        Super[] supers = subs;
    }

    static class Super {

    }

    static class Sub extends Super {

    }
}
