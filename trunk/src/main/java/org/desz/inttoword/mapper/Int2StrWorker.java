package org.desz.inttoword.mapper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.desz.inttoword.language.ILangProvider;
import static org.desz.inttoword.language.LanguageRepository.Def.*;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import static org.desz.inttoword.language.ProvLangFactory.getInstance;
import org.springframework.stereotype.Component;

/**
 * @author des Converts integer to corresponding word format in ProvLang
 * 
 */
@Component
public class Int2StrWorker {
	protected final Logger log = Logger.getLogger(Int2StrWorker.class.getName());

	/**
	 * 
	 * @param provLangFac
	 *            the ILangProvider for PROV_LANG.
	 * @param prm.
	 * @return the word in target PROV_LANG.
	 */
	private String doConversion(String prm, ILangProvider provLangFac) {
		prm = Objects.requireNonNull(prm);

		StringBuilder sb = new StringBuilder();
		final int n = Integer.parseInt(prm);
		final String key = String.valueOf(n);
		final String word = provLangFac.getWord(key);
		if (!Objects.isNull(word)) {
			sb.append(word.toLowerCase());
			return sb.toString();
		}
		int nmod = n % 100;
		final String hun = provLangFac.getWord(String.valueOf(n / 100));
		// 100..900
		if (nmod == 0) {
			sb.append(hun.toLowerCase() + provLangFac.getHundred());
			return sb.toString();
		}
		// affirm (xyz) where x = 1..9; y = 1..9; z = 0. e.g., 910, 920..
		if (!Objects.isNull(provLangFac.getWord(String.valueOf(nmod)))) {
			sb.append(hun.toLowerCase() + provLangFac.getHundred() + SPACE.val() + provLangFac.getAnd()
					+ provLangFac.getWord(String.valueOf(nmod)).toLowerCase());
			return sb.toString();
		}
		// nmod % 10 > 0)
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20
		if (inRange(n)) {// 0 to 99.

			sb.append(provLangFac.getWord(String.valueOf(k)).toLowerCase() + SPACE.val()
					+ provLangFac.getWord(String.valueOf(nmod)).toLowerCase());
			return sb.toString();

		}

		sb.append(hun.toLowerCase() + provLangFac.getHundred() + SPACE.val() + provLangFac.getAnd()
				+ provLangFac.getWord(String.valueOf(k)).toLowerCase() + SPACE.val()
				+ provLangFac.getWord(String.valueOf(nmod)).toLowerCase());

		return sb.toString();
	}

	/**
	 * 
	 * @param n
	 *            the integer.
	 * @return the word for n including units.
	 */

	public String convertIntToWord(Integer n, ProvLang provLang) {

		n = Objects.requireNonNull(n, "requires non-null parameter");
		final String fmt = NumberFormat.getIntegerInstance(Locale.UK).format(n);
		// split formatted String into list
		final List<String> list = Arrays.asList(fmt.split(","));
		// save last int.
		int last = Integer.parseInt(list.get(list.size() - 1));
		final List<String> words = new ArrayList<String>();
		// singeleton for language, provLang.
		final ILangProvider provLangFac = getInstance().factoryForProvLang(provLang);
		// convert each member of list.
		list.stream().filter(Objects::nonNull).forEach(s -> {
			words.add(doConversion(s, provLangFac));
		});
		final int sz = words.size();

		if (sz == 1)
			return words.get(0);

		final List<String> units = provLangFac.unitsList();

		// determine start index of units.
		final int startIdx = units.size() - sz;
		final List<String> sub = units.subList(startIdx, units.size());
		StringBuilder sb = new StringBuilder();
		int k = 0;
		for (final String s : words) {
			// ignore zero
			if (s.equals(provLangFac.getWord("0").toLowerCase())) {
				k++;
				continue;
			}
			final String str = s + sub.get(k) + SPACE.val();

			if (++k == sz & inRange(last)) // between 1 and 99.
				sb.append(provLangFac.getAnd() + str.trim());
			else
				sb.append(str);

		}

		return sb.toString().trim();

	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	private boolean inRange(int i) {
		return IntStream.range(1, 100).boxed().collect(Collectors.toSet()).contains(i);
	}

}
