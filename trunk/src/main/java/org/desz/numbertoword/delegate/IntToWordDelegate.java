package org.desz.numbertoword.delegate;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.DEF;
import org.desz.numbertoword.exceptions.IntToWordExc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

/**
 * Acts as a delegate for IntToWord. It calculates the word for number [0-999]
 * range
 * 
 * @author des
 * 
 */
public final class IntToWordDelegate {

	protected transient final static Logger LOGGER = Logger
			.getLogger(IntToWordDelegate.class.getName());

	/**
	 * 
	 * @param enumLanguageSupport
	 * @param num
	 * @return
	 * @throws IntToWordExc
	 *             if number range violated
	 */
	public static String calcWord(ILanguageSupport enumLanguageSupport,
			BigInteger num) throws IntToWordExc {

		Range<Integer> range = Ranges.closed(0, 999);

		try {
			Preconditions.checkNotNull(enumLanguageSupport);
		} catch (NullPointerException e) {
			throw new IntToWordExc("Language Support required");
		}

		try {
			Preconditions.checkNotNull(num);
		} catch (NullPointerException e) {
			// LOGGER.info(enumLanguageSupport.getNullInput());
			throw new IntToWordExc(enumLanguageSupport.getNullInput());
		}

		if (range.lowerEndpoint().compareTo(num.intValue()) > 0) {
			throw new IntToWordExc(enumLanguageSupport.getNegativeInput());
		}
		if (range.upperEndpoint().compareTo(num.intValue()) < 0) {
			throw new IntToWordExc(enumLanguageSupport.getInvalidInput());
		}

		String str = String.valueOf(num);

		if (enumLanguageSupport.getWord(str) != null) {
			// LOGGER.info("Direct return");
			return enumLanguageSupport.getWord(str);
		}

		final char s = Character.valueOf(str.charAt(0));
		if (num.intValue() % 100 == 0) {// integral hundreds

			return enumLanguageSupport.getWord(String.valueOf(s))
					+ enumLanguageSupport.getHunUnit();
		} else {
			StringBuilder sb = new StringBuilder();
			String res = null;
			if (num.intValue() > 100) {

				res = enumLanguageSupport.getWord(String.valueOf(s))
						+ enumLanguageSupport.getHunUnit()
						+ enumLanguageSupport.getAnd();

				sb.append(str.charAt(1));
				sb.append(str.charAt(2));

				int n = Integer.parseInt(sb.toString());

				if (enumLanguageSupport.getWord(String.valueOf(n)) != null) {
					return res + enumLanguageSupport.getWord(String.valueOf(n));
				} else {

					res += enumLanguageSupport.getWord(str.charAt(1) + "0")
							+ DEF.SPACE.val()
							+ enumLanguageSupport.getWord(String.valueOf(str
									.charAt(2)));

				}

				return res;

			} else {

				res = enumLanguageSupport.getWord(str.charAt(0) + "0")
						+ DEF.SPACE.val()
						+ enumLanguageSupport.getWord(String.valueOf(str
								.charAt(1)));

				return res;

			}
		}
	}
}
