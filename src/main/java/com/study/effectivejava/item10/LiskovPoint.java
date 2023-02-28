package com.study.effectivejava.item10;

import java.util.Set;

public class LiskovPoint {
	private final int x;
	private final int y;

	public LiskovPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private static final Set<LiskovPoint> unitCircle = Set.of(
			new LiskovPoint(0, -1),
			new LiskovPoint(0, 1),
			new LiskovPoint(-1, 0),
			new LiskovPoint(1, 0));


	public static boolean onUnitCircle(LiskovPoint p) {
		return unitCircle.contains(p);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
		LiskovPoint p = (LiskovPoint) o;
		return this.x == p.x && this.y == p.y;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
