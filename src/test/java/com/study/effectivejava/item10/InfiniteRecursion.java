package com.study.effectivejava.item10;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class InfiniteRecursion {

	@Test
	public void 무한_재귀_발생() {
		Point a = new ColorPoint(1, 2, Color.RED);
		Point b = new SmellPoint(1, 2, Smell.SPICY);

		// 무한 재귀 발생
		assertThat(a.equals(b)).isTrue();
	}
}
