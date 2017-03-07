package org.desz.inttoword.mapper;

import static org.desz.inttoword.language.LanguageRepository.Def.SPACE;
import static org.desz.inttoword.language.ProvLangFactory.getInstance;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.desz.inttoword.language.LanguageRepository.DeFormat;
import org.desz.inttoword.language.LanguageRepository.DeIntWordPair;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.NumericalLangMapping;
import org.springframework.stereotype.Component;

/**
 * @author des Converts integer to corresponding word format in ProvLang
 * 
 */
@Component
public class ConversionWorker {
	protected final Logger log = Logger.getLogger(ConversionWorker.class.getName());

	/**
	 * Builds the word but does not add the unit.
	 * 
	 * @param provLangFac
	 *            the ILangProvider for PROV_LANG.
	 * @param prm
	 *            the String representing the number.
	 * @return the word in target PROV_LANG.
	 */
	private String doConversion(String prm, NumericalLangMapping provLangFac) {
		prm = Objects.requireNonNull(prm);

		final int n = Integer.parseInt(prm);
		final String key = String.valueOf(n);
		final String word = provLangFac.getIntToWordMap().get(key);
		if (!Objects.isNull(word)) {
			return word.toLowerCase();
		}
		int nmod = n % 100;
		final String hun = provLangFac.getIntToWordMap().get(String.valueOf(n / 100));
		// determine whole hundreds
		if (nmod == 0) {
			return hun.toLowerCase() + provLangFac.getHund();

		}

		StringBuilder sb = new StringBuilder();
		// build non whole hundreds..
		if (!Objects.isNull(provLangFac.getIntToWordMap().get(String.valueOf(nmod)))) {
			if (provLangFac.getBilln().contains(DeFormat.BILLS.val())) {
				sb.append(
						provLangFac.getHund() + provLangFac.getIntToWordMap().get(String.valueOf(nmod)).toLowerCase());
			} else {
				sb.append(hun.toLowerCase() + provLangFac.getHund() + SPACE.val() + provLangFac.getAnd()
						+ provLangFac.getIntToWordMap().get(String.valueOf(nmod)).toLowerCase());
			}
			String res = sb.toString();
			if (res.endsWith(DeIntWordPair.ONE.getWord()))
				res += "s";
			return res;
		}
		// nmod % 10 > 0)
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20
		if (inRange(n)) {// 0 to 99.

			sb.append(provLangFac.getIntToWordMap().get(String.valueOf(k)).toLowerCase() + SPACE.val()
					+ provLangFac.getIntToWordMap().get(String.valueOf(nmod)).toLowerCase());
			return sb.toString();

		}

		if (provLangFac.getBilln().contains(DeFormat.BILLS.val()))
			sb.append(hun.toLowerCase() + provLangFac.getHund()
					+ provLangFac.getIntToWordMap().get(String.valueOf(nmod)).toLowerCase() + provLangFac.getAnd()
					+ provLangFac.getIntToWordMap().get(String.valueOf(k)).toLowerCase());
		else
			sb.append(hun.toLowerCase() + provLangFac.getHund() + SPACE.val() + provLangFac.getAnd()
					+ provLangFac.getIntToWordMap().get(String.valueOf(k)).toLowerCase() + SPACE.val()
					+ provLangFac.getIntToWordMap().get(String.valueOf(nmod)).toLowerCase());

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
		final List<String> numUnits = Arrays.asList(fmt.split(","));
		// save last int.
		int last = Integer.parseInt(numUnits.get(numUnits.size() - 1));
		final List<String> words = new ArrayList<String>();
		// singleton for provLang.
		final NumericalLangMapping provLangFac = getInstance().factoryForProvLang(provLang);
		// stream list.
		numUnits.stream().filter(Objects::nonNull).forEach(s -> {
			words.add(doConversion(s, provLangFac));
		});
		final int sz = words.size();

		if (sz == 1)
			return words.get(0);

		WordResult.Builder builder = new WordResult.Builder();

		if (sz == 4) {
			builder.withBill(words.get(0) + provLangFac.getBilln());
			builder.withMill(words.get(1) + provLangFac.getMilln());
			builder.withThou(words.get(2) + provLangFac.getThoud());
		}
		if (sz == 3) {
			builder.withMill(words.get(0) + provLangFac.getMilln());
			builder.withThou(words.get(1) + provLangFac.getThoud());
		}
		if (sz == 2)
			builder.withThou(words.get(0) + provLangFac.getThoud());

		if (inRange(last)) // between 1 and 99.

			builder.withHund(provLangFac.getAnd() + words.get(sz - 1));
		else
			builder.withHund(words.get(sz - 1));

		return builder.build().toString();

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
