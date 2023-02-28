package com.study.effectivejava.item11;

public class PhoneNumberNoHashCode {
	private final int first;
	private final int medium;
	private final int last;

	public PhoneNumberNoHashCode(int first, int medium, int last) {
		this.first = first;
		this.medium = medium;
		this.last = last;
	}
}
