/**
 * 
 */
package org.desz.inttoword.conv;

import static org.desz.inttoword.language.Punct.SPC;

import java.util.Objects;

import org.desz.inttoword.exceptions.ConversionParameterException;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLangFactoryParts.DeUnit;

/**
 * @author des
 *
 */
public class CenturionConverterImpl
		implements
			CenturionConverter<ConversionParameterException> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.desz.inttoword.conv.CenturionConverter#mapToWord(java.lang.String,
	 * org.desz.inttoword.language.IntWordMapping)
	 */
	@Override
	public String hundrethToWord(String number, IntWordMapping langMapping)
			throws ConversionParameterException {
		number = Objects.requireNonNull(number);
		// sanity check number. This method is only able to deal with hundreths
		if (number.length() > 3) {
			throw new ConversionParameterException();
		}
		final int n = Integer.parseInt(number);
		final String key = String.valueOf(n);
		final String word = langMapping.intWords().get(key);
		if (!Objects.isNull(word)) {
			return word.toLowerCase();
		}
		int nmod = n % 100;
		final String hun = langMapping.intWords().get(String.valueOf(n / 100));
		// if true it's whole hundreds
		if (nmod == 0) {
			return hun.toLowerCase() + langMapping.getHund();

		}

		StringBuilder sb = new StringBuilder();
		// build non whole hundreds..

		if (!Objects.isNull(langMapping.intWords().get(String.valueOf(nmod)))) {
			if (langMapping.getBilln().equals(DeUnit.BILLS.val())) {
				sb.append(hun.toLowerCase() + langMapping.getHund()
						+ langMapping.intWords().get(String.valueOf(nmod))
								.toLowerCase());
			} else {
				sb.append(hun.toLowerCase() + langMapping.getHund() + SPC.val()
						+ langMapping.getAnd() + langMapping.intWords()
								.get(String.valueOf(nmod)).toLowerCase());
			}

			return sb.toString();
		}

		// nmod % 10 > 0)
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20
		if (CenturionConverter.inRange(n)) {// 0 to 99.

			sb.append(
					langMapping.intWords().get(String.valueOf(k)).toLowerCase()
							+ SPC.val() + langMapping.intWords()
									.get(String.valueOf(nmod)).toLowerCase());
			return sb.toString();

		}

		if (langMapping.getBilln().contains(DeUnit.BILLS.val()))
			sb.append(hun.toLowerCase() + langMapping.getHund()
					+ langMapping.intWords().get(String.valueOf(nmod))
							.toLowerCase()
					+ langMapping.getAnd() + langMapping.intWords()
							.get(String.valueOf(k)).toLowerCase());
		else
			sb.append(hun.toLowerCase() + langMapping.getHund() + SPC.val()
					+ langMapping.getAnd()
					+ langMapping.intWords().get(String.valueOf(k))
							.toLowerCase()
					+ SPC.val() + langMapping.intWords()
							.get(String.valueOf(nmod)).toLowerCase());

		return sb.toString();
	}

}
