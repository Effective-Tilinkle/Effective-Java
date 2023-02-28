package com.study.effectivejava.item10;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Symmetry {

	@Test
	public void 대칭성_위배() {
		CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
		String s = "polish";

		assertThat(cis.equals(s)).isTrue();
		assertThat(s.equals(cis)).isFalse();
	}

	@Test
	public void 대칭성_위배2() {
		List<CaseInsensitiveString> list = new ArrayList<>();
		List<String> strList = new ArrayList<>();

		list.add(new CaseInsensitiveString("Polish"));
		strList.add("polish");
		String s = "polish";

		assertThat(list.contains(s)).isFalse();
		assertThat(strList.contains(s)).isTrue();
	}
}
