package com.study.effectivejava.item36;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static com.study.effectivejava.item36.EnumSetAnalysis.Style.*;
import static com.study.effectivejava.item36.EnumSetAnalysis.Style.ITALIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EnumSetAnalysisTest {

    @Test
    void testEnumSet() {
        EnumSet<EnumSetAnalysis.Style> enumSet = EnumSet.of(BOLD, ITALIC);
        System.out.println(enumSet);

        enumSet.retainAll(EnumSet.of(BOLD)); // 중복된 BOLD만 남음
        assertThat(enumSet)
                .hasSize(1)
                .containsOnly(BOLD);


        enumSet.retainAll(EnumSet.of(UNDERLINE, ITALIC));
        assertThat(enumSet)
                .isEmpty(); // 중복되는게 없으니, 암것도없음
    }
}