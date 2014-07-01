/**
 * 
 */
package org.desz.numbertoword.mapper;

import java.text.NumberFormat;
import java.util.Locale;

import org.desz.language.EnumLanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.DEF;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;

/**
 * @author des Converts integer (0-Integer.MAX_VALUE) to corresponding word
 *         format in PROV_LANG
 * 
 */
public class RecursiveIntToWord {

	private EnumLanguageSupport langSupp;

	public RecursiveIntToWord(PROV_LANG ln) {

		langSupp = new EnumLanguageSupport(ln);

	}

	/**
	 * converts number to word recursively. Number is formatted to discrete
	 * components. Each component is in range 0-999.
	 * 
	 * @param sb
	 * @param n range 0 - Integer.MAX_VALUE
	 * @return String containing language specific word.
	 */
	public String convert(StringBuilder sb, int n) {
		//TODO update validation of n.
		// check if can be retrieved using langSupp..
		if (langSupp.containsWord(String.valueOf(n))) {
			sb.append(langSupp.getWord(String.valueOf(n)).toLowerCase());
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
			// is modHun available using langSupp..
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
			wrdBdr = format(sb, Integer.valueOf(arr[1]));
			if (wrdBdr.isConv()) {
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
				wrdBdr = format(sb, Integer.valueOf(arr[j]));
				if (wrdBdr.isConv()) {
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
				wrdBdr = format(sb, Integer.valueOf(arr[j]));
				if (wrdBdr.isConv()) {
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
	 * builds word and indicates whether to progress with the recursion
	 * 
	 * @see WordBuilder
	 * 
	 * @param sb
	 * @param i
	 * @return
	 */

	private WordBuilder format(StringBuilder sb, int i) {
		StringBuilder sbCpy = new StringBuilder(sb.toString().trim());

		if (i > 0) {
			if (i < 100) {

				sbCpy.append(langSupp.getAnd());
			} else {
				sbCpy.append(DEF.SPACE.val());
			}
			return new WordBuilder(sbCpy, true);
		}
		return new WordBuilder(sb, false);
	}

	private class WordBuilder {
		public WordBuilder(StringBuilder sb, boolean bol) {
			super();
			this.sb = sb;
			this.conv = bol;
		}

		private final StringBuilder sb;
		private final boolean conv;

		public StringBuilder getSb() {
			return sb;
		}

		public boolean isConv() {
			return conv;
		}

	}
}
