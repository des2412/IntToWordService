/**
 * 
 */
package org.desz.inttoword.converters;

import static org.desz.inttoword.language.Punct.SPC;

import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import static org.apache.commons.lang3.StringUtils.EMPTY;
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
	public Optional<String> hundrethToWord(String number, IntWordMapping langMapping) {
		number = Objects.requireNonNull(number);

		if (number.length() > 3) {
			return empty();
		}
		final int n = Integer.parseInt(number);
		final String word = langMapping.wordForNum(n);
		if (!word.equals(EMPTY))
			return of(word.toLowerCase());

		String hun = (langMapping.wordForNum(n / 100) + langMapping.getHund()).toLowerCase();

		if (n % 100 == 0)
			return of(hun.toLowerCase());

		if (n < 100)
			hun = EMPTY;
		else
			hun += SPC.val() + langMapping.getAnd();

		// build non whole hundreds..

		int nmod = n % 100;
		final String rem = langMapping.wordForNum(nmod).toLowerCase();
		if (!rem.equals(EMPTY))
			return of(hun + rem);// e.g., 110,
									// 120..190.

		// otherwise more work to do..,
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20

		// the last possible value.
		return of(
				hun + langMapping.wordForNum(k).toLowerCase() + SPC.val() + langMapping.wordForNum(nmod).toLowerCase());
	}

}
