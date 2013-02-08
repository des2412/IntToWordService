package org.desz.mapper.helper;

import java.math.BigInteger;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.enums.EnumHolder;
import org.desz.numbertoword.enums.EnumHolder.DEF_FMT;
import org.desz.numbertoword.exceptions.IntToWordExc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

public class IntToWordDelegate {

	public static final Range<Integer> TEEN_RANGE = Ranges.closed(0, 19);
	public static final Range<Integer> DEC_RANGE = Ranges.closed(0, 99);

	public static String getDecimalString(ILanguageSupport sup, Integer num)
			throws IntToWordExc {

		Preconditions.checkNotNull(sup);

		try {
			Preconditions.checkNotNull(num);
		} catch (NullPointerException e) {
			// LOGGER.info(enumLanguageSupport.getNullInput());
			throw new IntToWordExc(sup.getNullInput());
		}

		StringBuilder sb = new StringBuilder();

		String s = String.valueOf(num);
		switch (s.length()) {
		case 1:
			return sup.getIntToWordMap().get(s);

		case 2:

			if (num % 10 == 0 | TEEN_RANGE.contains(num)) {
				return sup.getIntToWordMap().get(s);
			}

			String ten = sup.getIntToWordMap().get(
					String.valueOf(s.charAt(0)) + "0");

			sb.append(ten + EnumHolder.DEF_FMT.SPACE.val());

			String dec = sup.getIntToWordMap().get(String.valueOf(s.charAt(1)));
			sb.append(dec.toLowerCase());
			return sb.toString();
		default:
			throw new IntToWordExc();

		}

	}

	public static String calcWord(ILanguageSupport enumLanguageSupport, BigInteger val) throws IntToWordExc {

		String str = String.valueOf(val);
		char s = Character.valueOf(str.charAt(0));
		if (enumLanguageSupport.getWord(str) != null) {
			return enumLanguageSupport.getWord(str);
		}
		if (val.intValue() % 100 == 0) {// whole hundreds

			return enumLanguageSupport.getWord(String.valueOf(s))
					+ DEF_FMT.SPACE.val() + enumLanguageSupport.getHunUnit();
		} else {
			StringBuilder sb = new StringBuilder();
			if (val.intValue() > 100) {

				sb.append(str.charAt(1));
				sb.append(str.charAt(2));
				return enumLanguageSupport.getWord(String.valueOf(s))
						+ DEF_FMT.SPACE.val()
						+ enumLanguageSupport.getHunUnit()
						+ enumLanguageSupport.getAnd()
						+ IntToWordDelegate
								.getDecimalString(enumLanguageSupport,
										new Integer(sb.toString()));
			} else {
				// < 100
				if (val.intValue() >= 10) {
					sb.append(str.charAt(0));
					sb.append(str.charAt(1));
					int v = new Integer(sb.toString());

					return IntToWordDelegate.getDecimalString(
							enumLanguageSupport, v);

				}

			}
		}
		return null;
	}
}
