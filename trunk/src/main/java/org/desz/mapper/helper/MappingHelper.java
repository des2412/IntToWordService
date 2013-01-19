package org.desz.mapper.helper;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.enums.EnumHolder;
import org.desz.numbertoword.exceptions.IntToWordExc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

public class MappingHelper {

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
}
