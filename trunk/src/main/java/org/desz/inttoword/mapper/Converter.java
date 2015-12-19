package org.desz.inttoword.mapper;

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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.content.LangContent.DEF;
import org.desz.inttoword.content.LangContent.PROV_LANG;
import org.desz.inttoword.language.ILangProvider;
import org.desz.inttoword.language.ProvLangFac;

/**
 * @author des Converts integer to corresponding word format in PROV_LANG
 * 
 */
public class Converter {
	protected final Logger log = Logger.getLogger(Converter.class.getName());
	private ILangProvider provLn;
	private static Map<PROV_LANG, ILangProvider> LANG_SUP_CACHE = Collections
			.synchronizedMap(new HashMap<PROV_LANG, ILangProvider>());

	/**
	 * construct and cache.
	 * 
	 * @param ln
	 *            the provisioned language.
	 */
	public Converter(PROV_LANG ln) {

		if (!(LANG_SUP_CACHE.containsKey(ln))) {
			provLn = new ProvLangFac(ln);
			LANG_SUP_CACHE.put(ln, provLn);
		} else
			provLn = LANG_SUP_CACHE.get(ln);

	}

	/**
	 * 
	 * @param prm
	 * @return
	 */
	private String doConversion(String prm) {
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
	 *            the integer.
	 * @return the String for n.
	 */

	public String funcIntToString(Integer n) {
		n = Objects.requireNonNull(n, "converter requires non-null parameter");
		final String fmt = NumberFormat.getIntegerInstance(Locale.UK).format(n);
		// split to list
		List<String> lst = Arrays.asList(fmt.split(","));
		// save last int.
		int last = Integer.parseInt(lst.get(lst.size() - 1));
		final List<String> _strm = new ArrayList<String>();

		// stream into _strm.
		lst.stream().filter(Objects::nonNull).forEach(s -> {
			_strm.add(doConversion(s));
		});
		final int sz = _strm.size();
		// LOGGER.info("Number of elements:" + _strm.size());
		if (sz == 1)
			return _strm.get(0);

		List<String> units = Arrays.asList(provLn.getBillUnit(), provLn.getMillUnit(), provLn.getThouUnit(),
				StringUtils.EMPTY);

		// calc. the start index
		final int startIdx = units.size() - sz;
		// LOGGER.info("Start iteration index:" + startIdx);

		List<String> sub = units.subList(startIdx, units.size());
		StringBuilder sb = new StringBuilder();
		int k = 0;
		for (final String s : _strm) {
			// ignore zero
			if (s.equals(provLn.getWord("0").toLowerCase())) {
				k++;
				continue;
			}
			final String str = s + sub.get(k) + DEF.SPACE.val();
			k++;

			if (k == _strm.size()) {
				log.info("final int:" + last);
				if (intInRange(last))
					sb.append(provLn.getAnd() + str.trim());
				else
					sb.append(str);
			} else
				sb.append(str);

		}

		return sb.toString().trim();

	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	private boolean intInRange(int i) {
		return IntStream.range(1, 100).boxed().collect(Collectors.toSet()).contains(i);
	}

}
