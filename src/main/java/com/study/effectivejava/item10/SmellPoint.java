package com.study.effectivejava.item10;

public class SmellPoint extends Point {
	private final Smell smell;

	public SmellPoint(int x, int y, Smell smell) {
		super(x, y);
		this.smell = smell;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point))
			return false;

		if (!(o instanceof SmellPoint))
			return o.equals(this);

		return super.equals(o) && this.smell == ((SmellPoint) o).smell;
	}
}
