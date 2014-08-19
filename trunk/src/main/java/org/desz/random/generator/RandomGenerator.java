package org.desz.random.generator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class RandomGenerator {

	private final static List<Integer> c = Lists.newArrayList(1, 5, 10, 50,
			100, 500, 1000, 10000, 100000, 1000000, 10000000, 1000000000);

	/**
	 * achieves a good spread of values (power of 10) determined empirically by
	 * test case.
	 * 
	 * @return
	 */
	public static Integer randomInt() {

		Random r = new Random(System.currentTimeMillis());

		int k = r.nextInt(Integer.MAX_VALUE) + 1;
		Collections.shuffle(c);
		Random rdmSel = new Random();
		// return k divided by randon selected element of c

		return k / c.get(rdmSel.nextInt(c.size()));
	}

}
