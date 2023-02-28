package com.study.effectivejava.item10;

import java.util.Objects;

public class CaseInsensitiveString {
	private final String s;

	public CaseInsensitiveString(String s) {
		this.s = Objects.requireNonNull(s);
	}

	// 대칭성 위배
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CaseInsensitiveString)
			return s.equalsIgnoreCase(((CaseInsensitiveString) obj).s);

		// 한 방향으로만 작동한다.
		if (obj instanceof String)
			return s.equalsIgnoreCase((String) obj);

		return false;
	}
}
