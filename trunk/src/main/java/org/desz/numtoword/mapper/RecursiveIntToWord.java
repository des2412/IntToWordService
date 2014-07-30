/**
 * 
 */
package org.desz.numtoword.mapper;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.desz.language.ILanguageSupport;
import org.desz.language.LanguageSupport;
import org.desz.numtoword.cms.ContentContainer.DEF;
import org.desz.numtoword.cms.ContentContainer.PROV_LANG;

/**
 * @author des Converts integer (0-Integer.MAX_VALUE) to corresponding word
 *         format in PROV_LANG
 * 
 */
public class RecursiveIntToWord {

	private ILanguageSupport langSupp;
	private static Map<PROV_LANG, ILanguageSupport> _cache = Collections
			.synchronizedMap(new HashMap<PROV_LANG, ILanguageSupport>());

	public RecursiveIntToWord(PROV_LANG ln) {

		if (!(_cache.containsKey(ln))) {
			// cache langSupp
			langSupp = new LanguageSupport(ln);
			_cache.put(ln, langSupp);
		} else
			langSupp = _cache.get(ln);

	}

	/**
	 * converts number to word recursively. Number is UK formatted to the
	 * discrete components where each component [1 - 999].
	 * 
	 * @param word
	 * @param n
	 * @return String containing language specific word.
	 */
	public String convert(StringBuilder sb, int n) {
		// check if can be retrieved using langSupp..
		final String str = String.valueOf(n);
		if (langSupp.containsWord(str)) {
			sb.append(langSupp.getWord(str).toLowerCase());
			return sb.toString();
		}

		// ..otherwise calculate recursively
		String[] arr = null;
		String fmt = NumberFormat.getIntegerInstance(Locale.UK).format(n);

		int len = fmt.length() - fmt.replaceAll(",", "").length();
		WordBuilder wrdBdr = null;
		switch (len) {

		case 0:
			int modHun = n;
			String hun = String.valueOf(fmt.charAt(0));
			final int modDiv = 100;
			modHun %= modDiv;
			// 1..9 hundred
			if (modHun == 0) {
				sb.append(langSupp.getWord(hun).toLowerCase()
						+ langSupp.getHunUnit());
				break;
			}
			// check whether modHun contained in langSupp..
			if (langSupp.containsWord(String.valueOf(modHun))) {
				sb.append(langSupp.getWord(hun).toLowerCase()
						+ langSupp.getHunUnit()
						+ langSupp.getAnd()
						+ langSupp.getWord(String.valueOf(modHun))
								.toLowerCase());
				break;
			}
			// ..no! Calculation required
			String dec = null;
			int k = modHun;
			modHun %= 10;
			k -= modHun;
			if (n >= 100) {
				dec = langSupp.getWord(String.valueOf(k)) + DEF.SPACE.val()
						+ langSupp.getWord(String.valueOf(modHun));

				sb.append(langSupp.getWord(hun).toLowerCase()
						+ langSupp.getHunUnit() + langSupp.getAnd()
						+ dec.toLowerCase());
				break;
			}
			// n is less than 100
			dec = langSupp.getWord(String.valueOf(k)).toLowerCase()
					+ DEF.SPACE.val()
					+ langSupp.getWord(String.valueOf(modHun)).toLowerCase();
			sb.append(dec);

			break;

		case 1:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(langSupp.getThouUnit());
			wrdBdr = buildWord(sb, Integer.valueOf(arr[1]));
			if (wrdBdr.unFinished()) {
				sb = wrdBdr.getSb();
				convert(sb, Integer.valueOf(arr[1]));

			}
			break;

		case 2:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(langSupp.getMillUnit() + DEF.SPACE.val());

			// loop from arr[1].. performing conversion if arr[n] > 0
			for (int j = 1; j < arr.length; j++) {
				wrdBdr = buildWord(sb, Integer.valueOf(arr[j]));
				if (wrdBdr.unFinished()) {
					sb = wrdBdr.getSb();
					convert(sb, Integer.valueOf(arr[j]));
					if (j == 1)

						sb.append(langSupp.getThouUnit());
				}
			}

			break;
		case 3:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(langSupp.getBillUnit());

			for (int j = 1; j < arr.length; j++) {
				wrdBdr = buildWord(sb, Integer.valueOf(arr[j]));
				if (wrdBdr.unFinished()) {
					sb = wrdBdr.getSb();
					convert(sb, Integer.valueOf(arr[j]));
					if (j == 1)

						sb.append(langSupp.getMillUnit());
					if (j == 2)

						sb.append(langSupp.getThouUnit());

				}
			}

		}

		return sb.toString().trim();

	}

	/**
	 * builds word and indicates whether to progress with the recursion based on
	 * param i being greater than 0 which if so directs another recursion
	 * 
	 * @see WordBuilder
	 * 
	 * @param word
	 * @param i
	 * @return
	 */

	private WordBuilder buildWord(StringBuilder sb, int i) {
		StringBuilder sbCpy = new StringBuilder(sb.toString().trim());

		if (i > 0) {
			if (i < 100) {

				sbCpy.append(langSupp.getAnd());
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
