package org.desz.inttoword.conv;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.desz.inttoword.language.IntWordMapping;
/*
 * responsible for converting hundreths to word.
 * parameterised with Exception subtype.
 */
@FunctionalInterface
public interface CenturionConverter<E extends Exception> {

	/**
	 * 
	 * @param number
	 *            the number.
	 * @param langMapping
	 * @return the word for the hundreth
	 * @throws E
	 *             subclass of Exception.
	 */
	String hundrethToWord(String number, IntWordMapping langMapping) throws E;

	static boolean inRange(int i) {
		return IntStream.range(1, 100).boxed().collect(Collectors.toSet())
				.contains(i);
	}

}
