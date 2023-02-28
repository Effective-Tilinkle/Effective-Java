package com.study.effectivejava.item10;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class LiskovSubstitution {

	@Test
	public void 리스코프_치환원칙_위배(){
		/*
			Point.onUnitCircle 메서드의 contains 메서드에서 Set 내의 element 들에 대한 equals 비교 수행
			equals 메서드 첫번째 if 문에서 걸림
			ColorPoint 객체가 파라미터로 전달되어 null 은 아니지만,
			두번째 조건식인 o.getClass() 에서 ColorPoint.class 가 도출되고 this.getClass() 에서는 Point.class 가 도출되게 된다.
			위 조건식 때문에 ColorPoint 클래스는 Point 클래스로서 equals 메서드 내에서 활용되지 못했기 때문에 리스코프 치환원칙에 위배되는 코드
		*/
		LiskovColorPoint cp = new LiskovColorPoint(1, 0, Color.RED);
		assertThat(LiskovPoint.onUnitCircle(cp)).isFalse();
	}
}