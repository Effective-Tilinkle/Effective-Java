package com.study.effectivejava.item10;

public class LiskovColorPoint extends LiskovPoint {
	private final Color color;

	public LiskovColorPoint(int x, int y, Color color) {
		super(x, y);
		this.color = color;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LiskovPoint))
			return false;

		if (!(o instanceof LiskovColorPoint))
			return o.equals(this);

		return super.equals(o) && this.color == ((LiskovColorPoint) o).color;
	}
}
