/**
 * 
 */
package org.desz.inttoword.converters;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.desz.inttoword.language.Punct.SPC;

import java.util.Optional;

import org.desz.inttoword.language.IntWordMapping;
import org.springframework.stereotype.Component;

/**
 * @author des
 *
 */
@Component
public class HundredthConverter implements IHundConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.desz.inttoword.converters.IHundConverter#mapToWord(java.lang.String,
	 * org.desz.inttoword.language.IntWordMapping)
	 */
	@Override
	public Optional<String> toWordForLang(String number, IntWordMapping intWordMapping) {
		number = requireNonNull(number);
		intWordMapping = requireNonNull(intWordMapping);
		if (number.length() > 3) {
			return empty();
		}
		final int n = Integer.parseInt(number);
		if (n == 0)
			return Optional.empty();
		final String word = intWordMapping.wordForNum(n);
		if (!word.equals(EMPTY))
			return of(word.toLowerCase());

		String hun = (intWordMapping.wordForNum(n / 100) + intWordMapping.getHund()).toLowerCase();

		if (n % 100 == 0)
			return of(hun.toLowerCase());

		hun = (n < 100) ? EMPTY : hun + SPC.val() + intWordMapping.getAnd();

		// build non whole hundreds..

		int nmod = n % 100;
		final String rem = intWordMapping.wordForNum(nmod).toLowerCase();
		if (!isEmpty(rem))
			return of(hun + rem);// e.g., n = 110, 120,..990.

		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k = 20

		return of(
				hun + intWordMapping.wordForNum(k).toLowerCase() + SPC.val() + intWordMapping.wordForNum(nmod).toLowerCase());
	}

}
