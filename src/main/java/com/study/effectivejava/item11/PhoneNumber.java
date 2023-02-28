package com.study.effectivejava.item11;

import java.util.Objects;

public class PhoneNumber {
	private final int first;
	private final int medium;
	private final int last;

	public PhoneNumber(int first, int medium, int last) {
		this.first = first;
		this.medium = medium;
		this.last = last;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PhoneNumber that = (PhoneNumber) o;
		return first == that.first && medium == that.medium && last == that.last;
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, medium, last);
	}
}
