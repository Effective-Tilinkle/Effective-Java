package com.study.effectivejava.item8;

import org.junit.jupiter.api.Test;

public class RoomTest {
	@Test
	public void 잘_짜여진_클라이언트_코드() {
		try (Room myRoom = new Room(7)) {
			System.out.println("안녕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("종료");
	}

	@Test
	public void 잘못된_코드() {
		new Room(99);
		System.out.println("아무렴");
	}
}
