package org.desz.random.generator;

import java.util.logging.Logger;

import org.junit.Ignore;
import org.junit.Test;

public class TestRandomGenerator {

	private final static Logger LOGGER = Logger
			.getLogger(TestRandomGenerator.class.getName());

	@Test
	@Ignore
	public final void test() {
		int i = 0;
		for (; i < 100;) {
			int n = RandomGenerator.randomInt();
			LOGGER.info("[" + i + "]number digits in random:"
					+ String.valueOf(n).length());
			i++;
		}
	}

}
