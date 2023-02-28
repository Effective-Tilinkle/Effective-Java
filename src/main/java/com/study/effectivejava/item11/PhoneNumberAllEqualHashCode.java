package com.study.effectivejava.item11;

public class PhoneNumberAllEqualHashCode {
	private final int first;
	private final int medium;
	private final int last;

	public PhoneNumberAllEqualHashCode(int first, int medium, int last) {
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
		PhoneNumberAllEqualHashCode that = (PhoneNumberAllEqualHashCode) o;
		return first == that.first && medium == that.medium && last == that.last;
	}

	@Override
	public int hashCode() {
		return 42;
	}
}
