package com.study.effectivejava.item8;

public class AttackVulnerable extends Vulnerable {
	static Vulnerable vulnerable;

	public AttackVulnerable(int value) {
		super(value);
	}

	public void finalize() {
		vulnerable = this;
	}
}
