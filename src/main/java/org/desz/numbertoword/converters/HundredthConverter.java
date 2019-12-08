/**
 * 
 */
package org.desz.numbertoword.converters;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.Optional;

import org.desz.numbertoword.language.NumberWordMapping;

/**
 * @author des
 *
 */
public class HundredthConverter {

	/**
	 * Matches 'specification' for FuncHundConverter.wordForNumber.
	 * 
	 * @param number
	 * @param intWordMapping
	 * @return empty Optional if 0.
	 */
	public Optional<String> wordForHundredth(String number, NumberWordMapping intWordMapping) {
		number = requireNonNull(number);
		intWordMapping = requireNonNull(intWordMapping);
		if (number.length() > 3) {
			return empty();
		}
		final int n = Integer.parseInt(number);
		if (n == 0)
			return Optional.empty();
		final Optional<String> word = intWordMapping.wordForNum(n);
		if (!word.isEmpty())
			return of(word.get().toLowerCase());

		String hun = (intWordMapping.wordForNum(n / 100).orElse(EMPTY) + intWordMapping.getHund()).toLowerCase();

		if (n % 100 == 0)// ie 100..900
			return of(hun);

		hun = (n < 100) ? EMPTY : hun + SPACE + intWordMapping.getAnd();

		// build number with non-zero decimal.

		int nmod = n % 100;
		final String rem = intWordMapping.wordForNum(nmod).orElse(EMPTY);
		if (!rem.isEmpty())
			return of(hun + rem.toLowerCase());// e.g., n = 110, 120,..990.

		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k = 20

		return of(hun + intWordMapping.wordForNum(k).orElse(EMPTY).toLowerCase() + SPACE
				+ intWordMapping.wordForNum(nmod).orElse(EMPTY).toLowerCase());
	}

}
