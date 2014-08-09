package org.desz.integertoword.mapper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.desz.integertoword.content.ContentContainer.DEF;
import org.desz.integertoword.content.ContentContainer.PROV_LANG;
import org.desz.language.ILanguageSupport;
import org.desz.language.ProvLangWordFactory;

/**
 * @author des Converts integer (0-Integer.MAX_VALUE) to corresponding word
 *         format in PROV_LANG
 * 
 */
public class RecursiveIntToWord {
	protected final Logger LOGGER = Logger.getLogger(getClass());
	private ILanguageSupport provLangSupp;
	private static Map<PROV_LANG, ILanguageSupport> _cache = Collections
			.synchronizedMap(new HashMap<PROV_LANG, ILanguageSupport>());

	public RecursiveIntToWord(PROV_LANG ln) {

		if (!(_cache.containsKey(ln))) {
			// cache provLangSupp to enhance performance
			provLangSupp = new ProvLangWordFactory(ln);
			_cache.put(ln, provLangSupp);
		} else
			provLangSupp = _cache.get(ln);

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
		WordBuilder wrdBdr = null;

		List<IntWithUnit> list = new ArrayList<IntWithUnit>();
		switch (len) {

		case 0:
			int nmod = n;
			String hun = String.valueOf(fmt.charAt(0));
			//final int modDiv = 100;
			nmod %= 100;
			// 1..9 hundred
			if (nmod == 0) {
				sb.append(provLangSupp.getWord(hun).toLowerCase()
						+ provLangSupp.getHunUnit());
				break;
			}
			// check whether modHun contained in provLangSupp..
			if (provLangSupp.containsWord(String.valueOf(nmod))) {
				sb.append(provLangSupp.getWord(hun).toLowerCase()
						+ provLangSupp.getHunUnit()
						+ provLangSupp.getAnd()
						+ provLangSupp.getWord(String.valueOf(nmod))
								.toLowerCase());
				break;
			}
			// ..no! Calculation required
			String dec = null;
			int k = nmod;//ie., modhum = 23
			nmod %= 10;// ..modHun = 3
			k -= nmod; // .. k == 20
			if (n >= 100) {
				// 
				dec = provLangSupp.getWord(String.valueOf(k)) + DEF.SPACE.val()
						+ provLangSupp.getWord(String.valueOf(nmod));

				sb.append(provLangSupp.getWord(hun).toLowerCase()
						+ provLangSupp.getHunUnit() + provLangSupp.getAnd()
						+ dec.toLowerCase());
				break;
			}
			// n < 100
			dec = provLangSupp.getWord(String.valueOf(k)).toLowerCase()
					+ DEF.SPACE.val()
					+ provLangSupp.getWord(String.valueOf(nmod))
							.toLowerCase();
			sb.append(dec);

			break;

		case 1:// ..otherwise calculate recursively
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(provLangSupp.getThouUnit());
			wrdBdr = appendToWord(sb, Integer.valueOf(arr[1]));
			if (wrdBdr.unFinished()) {
				sb = wrdBdr.getSb();
				convert(sb, Integer.valueOf(arr[1]));

			}
			break;

		case 2:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(provLangSupp.getMillUnit() + DEF.SPACE.val());

			list.add(new IntWithUnit(Integer.valueOf(arr[1]), provLangSupp
					.getThouUnit()));

			list.add(new IntWithUnit(Integer.valueOf(arr[2]), null));
			sb.append(buildWithUnit(list));
			break;
		case 3:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(provLangSupp.getBillUnit() + DEF.SPACE.val());

			list.add(new IntWithUnit(Integer.valueOf(arr[1]), provLangSupp
					.getMillUnit()));

			list.add(new IntWithUnit(Integer.valueOf(arr[2]), provLangSupp
					.getThouUnit()));

			list.add(new IntWithUnit(Integer.valueOf(arr[3]), null));
			sb.append(buildWithUnit(list));

		}

		return sb.toString().trim();

	}

	/**
	 * Simple convenience class for DRY conformance. Associates int to be
	 * converted with the unit to append
	 *
	 */
	class IntWithUnit {
		private final int i;
		private String unit;

		/**
		 * @param i
		 *            int that will be converted to word
		 * @param unit
		 *            [billion, million, thousand]
		 */
		public IntWithUnit(int i, String unit) {
			this.i = i;
			this.unit = unit;
		}

		public int getI() {
			return i;
		}

		public String getUnit() {
			return unit;
		}

	}

	/**
	 * 
	 * @param list
	 * @return Word with unit
	 */
	private String buildWithUnit(List<IntWithUnit> list) {
		WordBuilder wrdBdr = null;
		StringBuilder sb = new StringBuilder();
		for (IntWithUnit it : list) {
			wrdBdr = appendToWord(sb, it.getI());
			if (wrdBdr.unFinished()) {
				sb = wrdBdr.getSb();
				convert(sb, it.getI());
				if (it.getUnit() != null)
					sb.append(it.getUnit());

			}
		}

		return sb.toString().trim();
	}

	/**
	 * appends space or and to word and indicates whether to progress with the
	 * recursion based on param i being greater than 0 which if so directs
	 * another recursion
	 * 
	 * @see WordBuilder
	 * 
	 * @param word
	 * @param i
	 * @return
	 */

	private WordBuilder appendToWord(StringBuilder sb, int i) {
		StringBuilder sbCpy = new StringBuilder(sb.toString().trim());

		if (i > 0) {
			if (i < 100) {

				sbCpy.append(provLangSupp.getAnd());
			} else {
				sbCpy.append(DEF.SPACE.val());
			}
			return new WordBuilder(sbCpy, true);
		}
		// false means recursion is finished
		return new WordBuilder(sb, false);
	}

	private class WordBuilder {
		public WordBuilder(StringBuilder word, boolean unFinished) {
			super();
			this.word = word;
			this.unFinished = unFinished;
		}

		private final StringBuilder word;
		private final boolean unFinished;

		public StringBuilder getSb() {
			return word;
		}

		public boolean unFinished() {
			return unFinished;
		}

	}
}
