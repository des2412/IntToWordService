package org.desz.random.generator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class RandomGenerator {

	private final static List<Integer> c = Lists.newArrayList(1, 5, 10, 50, 100, 500,
			1000, 10000, 100000, 1000000, 10000000, 1000000000);

	public static Integer randomInt() {

		Random r = new Random(System.currentTimeMillis());

		int k = r.nextInt(Integer.MAX_VALUE) + 1;
		Collections.shuffle(c);
		Random rdmSel = new Random();
		int j = rdmSel.nextInt(c.size());

		return k / c.get(j);
	}

}
