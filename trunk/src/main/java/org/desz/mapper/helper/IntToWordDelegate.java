package org.desz.mapper.helper;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.DEF;
import org.desz.numbertoword.exceptions.IntToWordExc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

public final class IntToWordDelegate {

	public static final Range<Integer> TO_TEEN_RANGE = Ranges.closed(0, 19);

	protected transient final static Logger LOGGER = Logger
			.getLogger(IntToWordDelegate.class.getName());

	/**
	 * 
	 * @param enumLanguageSupport
	 * @param num
	 * @return
	 * @throws IntToWordExc
	 */
	public static String calcWord(ILanguageSupport enumLanguageSupport,
			BigInteger num) throws IntToWordExc {

		try {
			Preconditions.checkNotNull(enumLanguageSupport);
		} catch (NullPointerException e) {
			throw new IntToWordExc("Null Language Support");
		}

		try {
			Preconditions.checkNotNull(num);
		} catch (NullPointerException e) {
			// LOGGER.info(enumLanguageSupport.getNullInput());
			throw new IntToWordExc(enumLanguageSupport.getNullInput());
		}

		String str = String.valueOf(num);

		if (enumLanguageSupport.getWord(str) != null) {
			LOGGER.info("Direct return");
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
					LOGGER.info("int val:" + n);
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
