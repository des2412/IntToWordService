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
	 * 
	 * @param sb
	 * @param n
	 * @return
	 */
	public String convert(StringBuilder sb, int n) {

		// check if in langSupp..
		if (langSupp.containsWord(String.valueOf(n))) {
			sb.append(langSupp.getWord(String.valueOf(n)).toLowerCase());
			return sb.toString();
		}

		// ..otherwise calculate recursively
		String[] arr = null;
		String fmt = NumberFormat.getIntegerInstance(Locale.UK).format(n);

		int len = fmt.length() - fmt.replaceAll(",", "").length();
		switch (len) {

		case 0:
			int modHun = n;
			String hun = String.valueOf(fmt.charAt(0));
			final int modDiv = 100;
			modHun %= modDiv;
			if (modHun == 0) {
				sb.append(langSupp.getWord(hun).toLowerCase()
						+ langSupp.getHunUnit());
				break;
			}

			if (langSupp.containsWord(String.valueOf(modHun))) {
				sb.append(langSupp.getWord(hun).toLowerCase()
						+ langSupp.getHunUnit()
						+ langSupp.getAnd()
						+ langSupp.getWord(String.valueOf(modHun))
								.toLowerCase());
				break;
			}
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
			dec = langSupp.getWord(String.valueOf(k)).toLowerCase()
					+ DEF.SPACE.val()
					+ langSupp.getWord(String.valueOf(modHun)).toLowerCase();
			sb.append(dec);

			break;

		case 1:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(langSupp.getThouUnit());
			if (Integer.valueOf(arr[1]) > 0) {
				if (Integer.valueOf(arr[1]) < 100) {
					sb.append(langSupp.getAnd());
				} else {
					sb.append(DEF.SPACE.val());
				}
				convert(sb, Integer.valueOf(arr[1]));
			}
			break;

		case 2:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(langSupp.getMillUnit() + DEF.SPACE.val());
			if (Integer.valueOf(arr[1]) > 0) {
				convert(sb, Integer.valueOf(arr[1]));
				sb.append(langSupp.getThouUnit());
			}
			if (Integer.valueOf(arr[2]) > 0) {
				if (Integer.valueOf(arr[2]) < 100) {
					sb = new StringBuilder(sb.toString().trim());
					sb.append(langSupp.getAnd());
				} else {
					sb.append(DEF.SPACE.val());
				}
				convert(sb, Integer.valueOf(arr[2]));
			}

			break;
		case 3:
			arr = fmt.split(",", ++len);
			convert(sb, Integer.valueOf(arr[0]));
			sb.append(langSupp.getBillUnit());
			if (Integer.valueOf(arr[1]) > 0) {
				if (Integer.valueOf(arr[1]) < 100) {
					sb = new StringBuilder(sb.toString().trim());
					sb.append(langSupp.getAnd());
				} else {
					sb.append(DEF.SPACE.val());

				}
				convert(sb, Integer.valueOf(arr[1]));
				sb.append(langSupp.getMillUnit());
			}
			if (Integer.valueOf(arr[2]) > 0) {
				if (Integer.valueOf(arr[2]) < 100) {
					sb = new StringBuilder(sb.toString().trim());
					sb.append(langSupp.getAnd());
				} else {
					sb.append(DEF.SPACE.val());
				}
				convert(sb, Integer.valueOf(arr[2]));
				sb.append(langSupp.getThouUnit());
			}
			if (Integer.valueOf(arr[3]) > 0) {
				if (Integer.valueOf(arr[3]) < 100) {
					sb = new StringBuilder(sb.toString().trim());
					sb.append(langSupp.getAnd());
				} else {
					sb.append(DEF.SPACE.val());
				}
				convert(sb, Integer.valueOf(arr[3]));
			}

		}

		return sb.toString().trim();

	}
}
