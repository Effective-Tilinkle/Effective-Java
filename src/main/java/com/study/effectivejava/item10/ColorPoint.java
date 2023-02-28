package com.study.effectivejava.item10;

public class ColorPoint extends Point {
	private final Color color;

	public ColorPoint(int x, int y, Color color) {
		super(x, y);
		this.color = color;
	}

		/* 대칭성 위배
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof ColorPoint))
				return false;

			return super.equals(obj) && this.color == ((ColorPoint) obj).color;
		}
		*/

	/* 추이성 위배 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point))
			return false;

		if (!(o instanceof ColorPoint))
			return o.equals(this);

		return super.equals(o) && this.color == ((ColorPoint) o).color;
	}
}
