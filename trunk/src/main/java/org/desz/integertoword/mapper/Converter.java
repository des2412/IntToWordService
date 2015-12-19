package org.desz.integertoword.mapper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.desz.integertoword.content.ContentContainer.DEF;
import org.desz.integertoword.content.ContentContainer.PROV_LANG;
import org.desz.integertoword.language.ILanguageSupport;
import org.desz.integertoword.language.ProvLangWordFactory;

/**
 * @author des Converts integer (0-Integer.MAX_VALUE) to corresponding word
 *         format in PROV_LANG
 * 
 */
public class Converter {
	protected final Logger LOGGER = Logger.getLogger(Converter.class.getName());
	private ILanguageSupport provLn;
	private static Map<PROV_LANG, ILanguageSupport> LANG_SUP_CACHE = Collections
			.synchronizedMap(new HashMap<PROV_LANG, ILanguageSupport>());

	/**
	 * Cache ln
	 * 
	 * @param ln
	 */
	public Converter(PROV_LANG ln) {

		if (!(LANG_SUP_CACHE.containsKey(ln))) {
			// cache provLangSupp to enhance performance
			provLn = new ProvLangWordFactory(ln);
			LANG_SUP_CACHE.put(ln, provLn);
		} else
			provLn = LANG_SUP_CACHE.get(ln);

	}

	public String convert(Integer i) {
		//
		if (i < 20)
			return provLn.getWord(String.valueOf(i));
		if (i < 100)
			return provLn.getWord(String.valueOf(i / 10)) + ((i % 10 > 0) ? " " + convert(i % 10) : "");
		if (i < 1000)
			return provLn.getWord(String.valueOf(i / 100)) + provLn.getHunUnit()
					+ ((i % 100 > 0) ? provLn.getAnd() + convert(i % 100) : "");
		if (i < 1000000)
			return convert(i / 1000) + " Thousand " + ((i % 1000 > 0) ? " " + convert(i % 1000) : "");
		return convert(i / 1000000) + " Million " + ((i % 1000000 > 0) ? " " + convert(i % 1000000) : "");
	}

	/**
	 * 
	 * @param prm
	 * @return
	 */
	public String doConversion(String prm) {
		prm = Objects.requireNonNull(prm);

		StringBuilder sb = new StringBuilder();
		final int n = Integer.parseInt(prm);
		final String key = String.valueOf(n);
		if (provLn.containsWord(key)) {
			sb.append(provLn.getWord(key).toLowerCase());
			return sb.toString();
		}
		int nmod = n;
		nmod %= 100;
		final String hun = String.valueOf(n / 100);
		// 1..9 hundred
		if (nmod == 0) {
			sb.append(provLn.getWord(hun).toLowerCase() + provLn.getHunUnit());
			return sb.toString();
		}
		// nmod > 0 check whether nmod contained in provLangSupp..
		if (provLn.containsWord(String.valueOf(nmod))) {
			sb.append(provLn.getWord(hun).toLowerCase() + provLn.getHunUnit() + DEF.SPACE.val() + provLn.getAnd()
					+ provLn.getWord(String.valueOf(nmod)).toLowerCase());
			return sb.toString();
		}
		// ..no! Calculation required for the decimal (i.e., nmod % 10 > 0)
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20
		if (n <= 100) {// < 100

			sb.append(provLn.getWord(String.valueOf(k)).toLowerCase() + DEF.SPACE.val()
					+ provLn.getWord(String.valueOf(nmod)).toLowerCase());
			return sb.toString();

		}

		sb.append(provLn.getWord(hun).toLowerCase() + provLn.getHunUnit() + DEF.SPACE.val() + provLn.getAnd()
				+ provLn.getWord(String.valueOf(k)).toLowerCase() + DEF.SPACE.val()
				+ provLn.getWord(String.valueOf(nmod)).toLowerCase());

		return sb.toString();
	}

	/**
	 * 
	 * @param n
	 * @return
	 */

	public String java8Function(int n) {
		final String fmt = NumberFormat.getIntegerInstance(Locale.UK).format(n);
		// break into a list
		List<String> lst = Arrays.asList(fmt.split(","));
		final List<String> stg2 = new ArrayList<String>();
		// save the last as an int
		int last = Integer.parseInt(lst.get(lst.size() - 1));

		// build-up
		lst.stream().filter(Objects::nonNull).forEach(s -> {
			stg2.add(doConversion(s));
		});
		final int sz = stg2.size();
		LOGGER.info("Number of elements:" + stg2.size());
		if (sz == 1)
			return stg2.get(0);

		List<String> units = Arrays.asList(provLn.getBillUnit(), provLn.getMillUnit(), provLn.getThouUnit(),
				StringUtils.EMPTY);

		// calc. the start index
		final int startIdx = units.size() - sz;
		LOGGER.info("Start iteration index:" + startIdx);

		List<String> sub = units.subList(startIdx, units.size());
		StringBuilder sb = new StringBuilder();
		int k = 0;
		for (String s : stg2) {

			if (s.equals(provLn.getWord("0").toLowerCase())) {
				k++;
				continue;
			}
			final String str = s + sub.get(k) + DEF.SPACE.val();
			k++;

			if (k == stg2.size()) {
				LOGGER.info("final int:" + last);
				if (last < 100 && last > 0)
					sb.append(provLn.getAnd() + str.trim());
				else
					sb.append(str);
			} else
				sb.append(str);// + DEF.SPACE.val());

		}

		return sb.toString().trim();

	}

}
