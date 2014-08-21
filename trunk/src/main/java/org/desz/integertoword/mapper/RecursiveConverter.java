package org.desz.integertoword.mapper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.integertoword.content.ContentContainer.DEF;
import org.desz.integertoword.content.ContentContainer.PROV_LANG;
import org.desz.integertoword.language.ILanguageSupport;
import org.desz.integertoword.language.ProvLangWordFactory;

/**
 * @author des Converts integer (0-Integer.MAX_VALUE) to corresponding word
 *         format in PROV_LANG
 * 
 */
public class RecursiveConverter {
	protected final Logger LOGGER = Logger.getLogger(RecursiveConverter.class
			.getName());
	private ILanguageSupport provLangSupp;
	private static Map<PROV_LANG, ILanguageSupport> LANG_SUP_CACHE = Collections
			.synchronizedMap(new HashMap<PROV_LANG, ILanguageSupport>());

	/**
	 * Cache ln
	 * 
	 * @param ln
	 */
	public RecursiveConverter(PROV_LANG ln) {

		if (!(LANG_SUP_CACHE.containsKey(ln))) {
			// cache provLangSupp to enhance performance
			provLangSupp = new ProvLangWordFactory(ln);
			LANG_SUP_CACHE.put(ln, provLangSupp);
		} else
			provLangSupp = LANG_SUP_CACHE.get(ln);

	}

	public String convert(Integer i) {
		//
		if (i < 20)
			return provLangSupp.getWord(String.valueOf(i));
		if (i < 100)
			return provLangSupp.getWord(String.valueOf(i / 10))
					+ ((i % 10 > 0) ? " " + convert(i % 10) : "");
		if (i < 1000)
			return provLangSupp.getWord(String.valueOf(i / 100))
					+ provLangSupp.getHunUnit()
					+ ((i % 100 > 0) ? provLangSupp.getAnd() + convert(i % 100)
							: "");
		if (i < 1000000)
			return convert(i / 1000) + " Thousand "
					+ ((i % 1000 > 0) ? " " + convert(i % 1000) : "");
		return convert(i / 1000000) + " Million "
				+ ((i % 1000000 > 0) ? " " + convert(i % 1000000) : "");
	}

	/**
	 * input: Integer [0 - Integer.MAX_VALUE] function: converts to word
	 * recursively. Number is UK formatted to the n components (n = 1, 4) where
	 * each component [1 - 999].
	 * 
	 * @param sb
	 * @param n
	 * @return
	 */
	public String convert(StringBuilder sb, int n) {
		// check if word can be retrieved from provLangSupp..
		final String str = String.valueOf(n);
		if (provLangSupp.containsWord(str)) {
			sb.append(provLangSupp.getWord(str).toLowerCase());
			return sb.toString();
		}

		String[] arr = null;
		String fmt = NumberFormat.getIntegerInstance(Locale.UK).format(n);
		int len = fmt.length() - fmt.replaceAll(DEF.NUM_SEP.val(), "").length();

		List<IntWithUnit> list = new ArrayList<IntWithUnit>();

		switch (len) {

		case 0:
			int nmod = n;
			String hun = String.valueOf(fmt.charAt(0));
			nmod %= 100;
			// 1..9 hundred
			if (nmod == 0) {
				sb.append(provLangSupp.getWord(String.valueOf(n / 100))
						.toLowerCase() + provLangSupp.getHunUnit());
				break;
			}
			// nmod > 0 check whether nmod contained in provLangSupp..
			if (provLangSupp.containsWord(String.valueOf(nmod))) {
				sb.append(provLangSupp.getWord(String.valueOf(n / 100))
						.toLowerCase()
						+ provLangSupp.getHunUnit()
						+ provLangSupp.getAnd()
						+ provLangSupp.getWord(String.valueOf(nmod))
								.toLowerCase());
				break;
			}
			// ..no! Calculation required for the decimal (i.e., nmod % 10 not
			// 0)
			int k = nmod;// e.g., nmod = 23
			nmod %= 10;// ..nmod = 3
			k -= nmod; // .. k == 20
			if (n <= 100) {// < 100

				sb.append(provLangSupp.getWord(String.valueOf(k)).toLowerCase()
						+ DEF.SPACE.val()
						+ provLangSupp.getWord(String.valueOf(nmod))
								.toLowerCase());
				break;

			}

			sb.append(provLangSupp.getWord(String.valueOf(n / 100))
					.toLowerCase()
					+ provLangSupp.getHunUnit()
					+ provLangSupp.getAnd()
					+ provLangSupp.getWord(String.valueOf(k)).toLowerCase()
					+ DEF.SPACE.val()
					+ provLangSupp.getWord(String.valueOf(nmod)).toLowerCase());

			break;

		case 1:// ..otherwise calculate recursively
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(provLangSupp.getThouUnit());
			if (Integer.valueOf(arr[1]) > 0) {

				if (Integer.valueOf(arr[1]) < 100)
					sb.append(this.provLangSupp.getAnd());
				else
					sb.append(DEF.SPACE.val());

				list.add(new IntWithUnit(Integer.valueOf(arr[1]), "", ""));
				sb.append(buildWithUnit(list));

			}

			break;

		case 2:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(provLangSupp.getMillUnit() + DEF.SPACE.val());

			list.add(
					0,
					new IntWithUnit(Integer.valueOf(arr[1]), provLangSupp
							.getThouUnit(), provLangSupp.getAnd()));

			list.add(1, new IntWithUnit(Integer.valueOf(arr[2]), "",
					provLangSupp.getAnd()));
			sb.append(buildWithUnit(list));
			break;
		case 3:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(provLangSupp.getBillUnit() + DEF.SPACE.val());

			list.add(
					0,
					new IntWithUnit(Integer.valueOf(arr[1]), provLangSupp
							.getMillUnit(), provLangSupp.getAnd()));

			list.add(
					1,
					new IntWithUnit(Integer.valueOf(arr[2]), provLangSupp
							.getThouUnit(), provLangSupp.getAnd()));

			list.add(2, new IntWithUnit(Integer.valueOf(arr[3]), "", ""));
			sb.append(buildWithUnit(list));

		}

		return sb.toString().trim();

	}

	/**
	 * 
	 * @param list
	 * @return Word with unit
	 */
	private String buildWithUnit(List<IntWithUnit> list) {
		StringBuilder sb = new StringBuilder();
		for (IntWithUnit it : list) {
			if (it.getI() > 0) {
				if (it.getI() < 100)
					sb.append(it.getAnd());
				convert(sb, it.getI());
				if (it.getUnit() != "")
					sb.append(it.getUnit() + DEF.SPACE.val());

			}

		}

		return sb.toString().trim();
	}

}
