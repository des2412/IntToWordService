package org.desz.inttoword.converters;

import java.util.Optional;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;
import org.desz.inttoword.language.IntWordMapping;

/*
 * converts hundredth to word.
 */
@FunctionalInterface
public interface IHundConverter {

	/**
	 * 
	 * @param number      the number.
	 * @param intWordMapping the IntWordMapping.
	 * @return the word for the hundredth specified by langMapping.
	 */
	Optional<String> toWordForLang(String number, IntWordMapping intWordMapping);

	static boolean inRange(int i) {
		return range(1, 100).boxed().collect(toSet()).contains(i);
	}

}