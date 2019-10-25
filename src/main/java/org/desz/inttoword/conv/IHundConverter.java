package org.desz.inttoword.conv;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.desz.inttoword.language.IntWordMapping;
/*
 * responsible for converting hundreths to word.
 * parameterised with Exception subtype.
 */
@FunctionalInterface
public interface IHundConverter {

	/**
	 * 
	 * @param number
	 *            the number.
	 * @param langMapping
	 * @return the word for the hundreth
	 */
	Optional<String> hundrethToWord(String number, IntWordMapping langMapping);

	static boolean inRange(int i) {
		return IntStream.range(1, 100).boxed().collect(Collectors.toSet())
				.contains(i);
	}

}
