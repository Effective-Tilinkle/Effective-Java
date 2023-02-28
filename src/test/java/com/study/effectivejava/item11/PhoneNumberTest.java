package com.study.effectivejava.item11;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class PhoneNumberTest {
	@Test
	public void 해시코드_정의하지_않은_경우() {
		// 논리적 동치인 두 객체가 서로 다른 해시코드를 반환하여 get 메서드는 엉뚱한 해시 버킷에 가서 객체를 찾으려 한 것
		Map<PhoneNumberNoHashCode, String> m = new HashMap<>();
		m.put(new PhoneNumberNoHashCode(707, 867, 5309), "TEST");

		assertThat(m.get(new PhoneNumberNoHashCode(707, 867, 5309))).isEqualTo("TEST");
	}

	@Test
	public void 해시코드_정의한_경우() {
		// 해시 코드 정의를 통해 동일한 해시 버킷에서 객체를 찾음
		Map<PhoneNumber, String> m = new HashMap<>();
		m.put(new PhoneNumber(707, 867, 5309), "TEST");

		assertThat(m.get(new PhoneNumber(707, 867, 5309))).isEqualTo("TEST");
	}

	@Test
	public void 동일한_해시코드_정의한_경우() {
		// 해시코드가 동일할 경우
		// 모든 객체에게 똑같은 값만 내어주므로 모든 객체가 해시테이블의 버킷 하나에 담겨 마치 연결리스트처럼 동작한다.
		// 그 결과 평균 수행 시간이 O(1)인 해시테이블이 O(n)으로 느려짐
		Map<PhoneNumberAllEqualHashCode, String> m = new HashMap<>();
		Map<PhoneNumber, String> m2 = new HashMap();

		long beforeTime = System.currentTimeMillis();
		for (int i=0; i<100000; i++) {
			m.put(new PhoneNumberAllEqualHashCode(i, i, i), "TEST");
		}
		long afterTime = System.currentTimeMillis();
		System.out.println("동일한 해시 코드 실행 시간차이(ms) : " + (afterTime - beforeTime));

		long beforeTime2 = System.currentTimeMillis();
		for (int i=0; i<100000; i++) {
			m2.put(new PhoneNumber(i, i, i), "TEST");
		}
		long afterTime2 = System.currentTimeMillis();
		System.out.println("서로 다른 해시 코드 실행 시간차이(ms) : " + (afterTime2 - beforeTime2));
	}
}
