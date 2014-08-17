package org.desz.random.generator;

import java.util.Random;

public class RandomGenerator {

	public static Integer randomInt() {

		Random r = new Random();

		Random rMod = new Random();

		int k = r.nextInt(Integer.MAX_VALUE) + 1;
		int mod = rMod.nextInt(100000);
		if (mod % 2 == 0)
			return k %= mod;

		return k;
	}

}
