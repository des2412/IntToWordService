package org.desz.random.generator;

import org.junit.Test;

public class TestRandomGenerator {

	// RandomGenerator gen;
	@Test
	public final void test() {
		int i = 0;
		for (; i < 100;) {
			int n = RandomGenerator.randomInt();
			System.out.println("[" + i + "]number digits in random:"
					+ String.valueOf(n).length());
			i++;
		}
	}

}
