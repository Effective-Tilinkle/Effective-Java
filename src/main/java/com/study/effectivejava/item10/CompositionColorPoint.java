package com.study.effectivejava.item10;

import java.util.Objects;

public class CompositionColorPoint {
	private CompositionPoint point;
	private Color color;

	public CompositionColorPoint(int x, int y, Color color) {
		this.point = new CompositionPoint(x, y);
		this.color = Objects.requireNonNull(color);
	}

	public CompositionPoint asPoint() {
		return this.point;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CompositionColorPoint)) {
			return false;
		}
		CompositionColorPoint cp = (CompositionColorPoint) o;
		return this.point.equals(cp) && this.color.equals(cp.color);
	}
}
