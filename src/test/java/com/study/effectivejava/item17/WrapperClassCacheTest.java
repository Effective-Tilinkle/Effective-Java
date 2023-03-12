package com.study.effectivejava.item17;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class WrapperClassCacheTest {
    @Test
    void Integer클래스는_마이너스127_에서_128까지는_캐싱된다() {
        Integer integer = Integer.valueOf(10);
        Integer integer2 = Integer.valueOf(10);
        Integer integer3 = new Integer(10);

        assertTrue(integer == integer2);
        assertFalse(integer == integer3);

        Integer integer4 = Integer.valueOf(128);
        Integer integer5 = Integer.valueOf(128);

        assertFalse(integer4 == integer5);
    }
}