package com.study.effectivejava.item10;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Composition {

	@Test
	public void 대칭성() {
		CompositionColorPoint a = new CompositionColorPoint(1, 2, Color.RED);
		CompositionPoint b = new CompositionPoint(1, 2);

		assertThat(a.asPoint().equals(b)).isTrue();
		assertThat(b.equals(a.asPoint())).isTrue();
	}

	@Test
	public void 추이성() {
		CompositionColorPoint a = new CompositionColorPoint(1, 2, Color.RED);
		CompositionPoint b = new CompositionPoint(1, 2);
		CompositionColorPoint c = new CompositionColorPoint(1, 2, Color.WHITE);

		assertThat(a.asPoint().equals(b)).isTrue();
		assertThat(b.equals(c.asPoint())).isTrue();
		assertThat(c.asPoint().equals(a.asPoint())).isTrue();
	}

	@Test
	public void 리스코프_치환원칙() {
		CompositionColorPoint cp = new CompositionColorPoint(1, 0, Color.RED);
		assertThat(CompositionPoint.onUnitCircle(cp.asPoint())).isTrue();
	}
}
