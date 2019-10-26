/**
 * 
 */
package org.desz.inttoword.converters;

import static org.desz.inttoword.language.Punct.SPC;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
		// contract specifies hundreths maximum.
		if (number.length() > 3) {
			return Optional.empty();
		}
		final int n = Integer.parseInt(number);
		final String word = langMapping.wordForNum(n);
		if (!word.equals(StringUtils.EMPTY))
			return Optional.of(word.toLowerCase());

		String hun = (langMapping.wordForNum(n / 100) + langMapping.getHund()).toLowerCase();

		if (n % 100 == 0)
			return Optional.of(hun.toLowerCase());

		if (n < 100)
			hun = StringUtils.EMPTY;
		else
			hun += SPC.val() + langMapping.getAnd();

		// build non whole hundreds..

		int nmod = n % 100;
		final String rem = langMapping.wordForNum(nmod).toLowerCase();
		if (!rem.equals(StringUtils.EMPTY))
			return Optional.of(hun + rem);// e.g., 110,
											// 120..190.

		// otherwise more work to do..,
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20

		// the last possible value.
		return Optional.of(
				hun + langMapping.wordForNum(k).toLowerCase() + SPC.val() + langMapping.wordForNum(nmod).toLowerCase());
	}

}
