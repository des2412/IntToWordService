package org.desz.inttoword.converters;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.results.Word;
import org.desz.inttoword.results.Word.WordBuilder;

public class LongToWordBuilder implements ILongToWordBuilder {

	private static HundredthConverter hundredthConverter = HundredthConverter.getInstance();

	private String rearrangeHun(String s) {
		List<String> l = asList(s.split(SPACE));
		StringBuilder sb = new StringBuilder();
		switch (l.size()) {
		case 1:
			return s;
		case 2:// add und
			sb.append(l.get(1) + "und" + l.get(0));
			break;
		case 3:
			sb.append(l.get(0) + l.get(2) + l.get(1));
			break;

		}

		return sb.toString();
	}

	/**
	 * tail recursion.
	 */
	@Override
	public Word buildWord(List<String> numbers, WordBuilder wordBuilder, IntWordMapping intWordMapping) {
		final int sz = numbers.size();

		// get zero element index and convert
		String num = hundredthConverter.toWordForLang(numbers.get(0), intWordMapping).orElse(EMPTY);

		if (intWordMapping.getAnd().equalsIgnoreCase("und"))
			num = Integer.parseInt(numbers.get(0)) % 10 != 0 ? rearrangeHun(num) : num;

		if (!isEmpty(num)) {
			switch (sz) {
			case 7:
				wordBuilder.quint(num + SPACE + capitalize(intWordMapping.getQuintn()));
				break;
			case 6:
				wordBuilder.quadr(num + SPACE + capitalize(intWordMapping.getQuadrn().strip()));
				break;
			case 5:
				wordBuilder.trill(num + SPACE + capitalize(intWordMapping.getTrilln().strip().toLowerCase()));
				break;
			case 4:
				wordBuilder.bill(num + SPACE + capitalize(intWordMapping.getBilln().strip().toLowerCase()));
				break;
			case 3:
				String s = intWordMapping.getMilln().strip();
				wordBuilder.mill(num + SPACE + capitalize(intWordMapping.getMilln().strip()));
				break;
			case 2:
				wordBuilder.thou(num + SPACE + capitalize(intWordMapping.getThoud().strip().toLowerCase()));
				break;

			case 1:
				wordBuilder.hund(num);
				break;
			}
		}
		return sz == 1 ? wordBuilder.build()
				: buildWord(numbers.subList(1, numbers.size()), wordBuilder, intWordMapping);

	}

}
