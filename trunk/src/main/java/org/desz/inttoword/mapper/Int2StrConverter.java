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

import org.desz.inttoword.language.ILangProvider;
import org.desz.inttoword.language.LanguageRepository.Def;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.ProvLangFactory;
import org.springframework.stereotype.Component;

/**
 * @author des Converts integer to corresponding word format in ProvLang
 * 
 */
@Component
public class Int2StrConverter {
	protected final Logger log = Logger.getLogger(Int2StrConverter.class.getName());
	private ILangProvider provLangFac;
	private static Map<ProvLang, ILangProvider> PROV_LANG_CACHE = Collections
			.synchronizedMap(new HashMap<ProvLang, ILangProvider>());

	/**
	 * construct ProvLangFactory if not cached.
	 * 
	 * @param ln
	 *            the provisioned language.
	 */
	private void assignProvLangFactoryFromCache(ProvLang ln) {

		if (!(PROV_LANG_CACHE.containsKey(ln)))
			PROV_LANG_CACHE.put(ln, new ProvLangFactory(ln));

		provLangFac = PROV_LANG_CACHE.get(ln);

	}

	/**
	 * 
	 * @param prm.
	 * @return the word with units
	 */
	private String doConversion(String prm) {
		prm = Objects.requireNonNull(prm);

		StringBuilder sb = new StringBuilder();
		final int n = Integer.parseInt(prm);
		final String key = String.valueOf(n);
		if (provLangFac.containsWord(key)) {
			sb.append(provLangFac.getWord(key).toLowerCase());
			return sb.toString();
		}
		int nmod = n % 100;
		final String hun = provLangFac.getWord(String.valueOf(n / 100));
		// 100..900
		if (nmod == 0) {
			sb.append(hun.toLowerCase() + provLangFac.getHunUnit());
			return sb.toString();
		}
		// nmod mapped directly
		if (provLangFac.containsWord(String.valueOf(nmod))) {
			sb.append(hun.toLowerCase() + provLangFac.getHunUnit() + Def.SPACE.val() + provLangFac.getAnd()
					+ provLangFac.getWord(String.valueOf(nmod)).toLowerCase());
			return sb.toString();
		}
		// nmod % 10 > 0)
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20
		if (inRange(n)) {

			sb.append(provLangFac.getWord(String.valueOf(k)).toLowerCase() + Def.SPACE.val()
					+ provLangFac.getWord(String.valueOf(nmod)).toLowerCase());
			return sb.toString();

		}

		sb.append(hun.toLowerCase() + provLangFac.getHunUnit() + Def.SPACE.val() + provLangFac.getAnd()
				+ provLangFac.getWord(String.valueOf(k)).toLowerCase() + Def.SPACE.val()
				+ provLangFac.getWord(String.valueOf(nmod)).toLowerCase());

		return sb.toString();
	}

	/**
	 * 
	 * @param n
	 *            the integer.
	 * @return the word for n.
	 */

	public String funcIntToString(Integer n, ProvLang provLang) {
		assignProvLangFactoryFromCache(provLang);
		n = Objects.requireNonNull(n, "requires non-null parameter");
		final String fmt = NumberFormat.getIntegerInstance(Locale.UK).format(n);
		// split to list
		final List<String> lst = Arrays.asList(fmt.split(","));
		// save last int.
		int last = Integer.parseInt(lst.get(lst.size() - 1));
		final List<String> _strm = new ArrayList<String>();

		// stream into _strm.
		lst.stream().filter(Objects::nonNull).forEach(s -> {
			_strm.add(doConversion(s));
		});
		final int sz = _strm.size();

		if (sz == 1)
			return _strm.get(0);

		List<String> units = provLangFac.unitsList();

		// calc. the start index
		final int startIdx = units.size() - sz;
		final List<String> sub = units.subList(startIdx, units.size());
		StringBuilder sb = new StringBuilder();
		int k = 0;
		for (final String s : _strm) {
			// ignore zero
			if (s.equals(provLangFac.getWord("0").toLowerCase())) {
				k++;
				continue;
			}
			final String str = s + sub.get(k) + Def.SPACE.val();
			k++;

			if (k == sz & inRange(last)) // between 1 and 99 inclusive.
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
