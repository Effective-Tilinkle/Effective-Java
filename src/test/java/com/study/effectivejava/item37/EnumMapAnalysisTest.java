package com.study.effectivejava.item37;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumMapAnalysisTest {
    @Test
    void testTransitionMap() {
        EnumMapAnalysis.Phase.Transition from = EnumMapAnalysis.Phase.Transition.from(EnumMapAnalysis.Phase.SOLID, EnumMapAnalysis.Phase.LIQUID);
        assertEquals(EnumMapAnalysis.Phase.Transition.MELT, from);
    }
}