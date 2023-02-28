package com.study.effectivejava.item10;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Transitivity {

	@Test
	public void 대칭성_위배() {
		ColorPoint a = new ColorPoint(1, 2, Color.RED);
		Point b = new Point(1, 2);

		// a.equals(b) 의 경우 a는 ColorPoint 클래스이기 떄문에 ColorPoint 클래스에서 재정의된 equals 메서드를 탐
		// 첫번째 if 조건에서 걸리게 됨. b는 Point 지만 ColorPoint 는 아님
		assertThat(a.equals(b)).isFalse();

		// b.equals(a) 의 경우 b는 Point 클래스이기 떄문에 Point 클래스의 equals 메서드를 타게 됨
		// ColorPoint 는 Point 클래스를 상속하고 있기 때문에 첫번째 if 조건을 통과하게 되고, x,y 값을 기준으로만 비교하기 떄문에 값이 참이 된다.
		assertThat(b.equals(a)).isTrue();
	}

	@Test
	public void 추이성_위배() {
		// a.equals(b)는 true, b.equals(c)는 true 만족하지만
		// a.equals(c)는 false 이므로 equals 정의 규약 중 추이성을 위반하는 코드
		ColorPoint a = new ColorPoint(1, 2, Color.RED);
		Point b = new Point(1, 2);
		ColorPoint c = new ColorPoint(1, 2, Color.WHITE);

		assertThat(a.equals(b)).isTrue();
		assertThat(b.equals(c)).isTrue();
		assertThat(c.equals(a)).isFalse();
	}
}
