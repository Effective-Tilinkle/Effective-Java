package com.study.effectivejava.item8;

import org.junit.jupiter.api.Test;

/**
 * 참조 : https://yangbongsoo.tistory.com/8
 */
public class FinalizerAttackTest {

	/**
	 * try 블럭에서 새로운 AttackVulnerable 객체 생성을 시도한다.
	 * value가 -1이기 때문에 Exception이 발생하고 catch 블록으로 온다.
	 * System.gc 와 System.runFinalization 호출은 vm이 gc를 실행하고 일부 finalizer를 실행하도록 한다.
	 * 값이 잘못된 Vulnerable 객체가 만들어진다.
	 * 	- 왜 Vulnerable value가 -1가 아니라 0일까?
	 * 		- Vulnerable 생성자에서 인자 검사전까지는 value 할당을 하지 않기 때문이다
	 *  	- 그래서 value는 초기값 0이다
	 */
	@Test
	public void 클래스가_subclassed된_경우_취약점이_발생() {
		AttackVulnerable attackVulnerable = null;
		try {
			attackVulnerable = new AttackVulnerable(-1);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.gc();
		System.runFinalization();
		if (attackVulnerable.vulnerable != null) {
			System.out.println("Vulnerable object " + attackVulnerable.vulnerable + " created!");
		}
	}
}
