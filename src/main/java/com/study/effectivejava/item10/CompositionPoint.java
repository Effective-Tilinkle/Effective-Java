package com.study.effectivejava.item10;

import java.util.Objects;
import java.util.Set;

public class CompositionPoint {
	private final int x;
	private final int y;

	public CompositionPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private static final Set<CompositionPoint> unitCircle = Set.of(
			new CompositionPoint(0, -1),
			new CompositionPoint(0, 1),
			new CompositionPoint(-1, 0),
			new CompositionPoint(1, 0));


	public static boolean onUnitCircle(CompositionPoint p) {
		return unitCircle.contains(p);
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CompositionPoint)) {
			return false;
		}
		CompositionPoint p = (CompositionPoint) o;
		return this.x == p.x && this.y == p.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
