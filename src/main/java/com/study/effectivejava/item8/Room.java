package com.study.effectivejava.item8;

import java.lang.ref.Cleaner;

public class Room implements AutoCloseable {

	private static final Cleaner cleaner = Cleaner.create();

	private static class State implements Runnable {

		int numJunkPiles;

		State(int numJunkPiles) {
			this.numJunkPiles = numJunkPiles;
		}

		// 자원 회수 시, close 메서드 > cleanable.close() 메서드 호출 시, 호출
		// 클린 작업 수행
		@Override
		public void run() {
			System.out.println("방 청소");
			numJunkPiles = 0;
		}
	}

	private final State state;

	private final Cleaner.Cleanable cleanable;

	public Room(int numJunkPiles) {
		state = new State(numJunkPiles);
		cleanable = cleaner.register(this, state);
	}

	// 자원 회수 시, 호출
	@Override
	public void close() throws Exception {
		cleanable.clean();
	}
}
