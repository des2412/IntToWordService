package org.desz.inttoword.converters;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.desz.inttoword.language.ProvLang.DE;

import java.util.List;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.results.Word;
import org.desz.inttoword.results.Word.WordBuilder;

public class LongToWordBuilder implements ILongToWordBuilder {

	private static HundredthConverter hundredthConverter = HundredthConverter.getInstance();

	private String processHun(String s, String and, boolean ipl, int k) {
		List<String> l = asList(s.split(SPACE));
		StringBuilder sb = new StringBuilder();
		switch (l.size()) {
		case 1:
			return s;
		case 2:// add und
			sb = ipl ? sb.append(l.get(1) + and + l.get(0)) : sb.append(l.get(1) + l.get(0));
			// remove und.
			sb = (k < 20) ? new StringBuilder().append(l.get(0) + l.get(1).substring(3, l.get(1).length())) : sb;

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
		final boolean isDe = intWordMapping.getId().equals(DE.name());

		final int val = Integer.parseInt(numbers.get(0));

		num = (isDe & val % 10 != 0) ? processHun(num, intWordMapping.getAnd(), val % 100 > 20, val % 100) : num;

		if (!isEmpty(num)) {
			switch (sz) {
			case 7:
				wordBuilder = isDe ? wordBuilder.quint(num + SPACE + capitalize(intWordMapping.getQuintn().strip()))
						: wordBuilder.quint(num + SPACE + intWordMapping.getQuintn().strip());
				break;
			case 6:
				wordBuilder = isDe ? wordBuilder.quadr(num + SPACE + capitalize(intWordMapping.getQuadrn().strip()))
						: wordBuilder.quadr(num + SPACE + intWordMapping.getQuadrn().strip());
				break;
			case 5:
				wordBuilder = isDe ? wordBuilder.trill(num + SPACE + capitalize(intWordMapping.getTrilln().strip()))
						: wordBuilder.trill(num + SPACE + intWordMapping.getTrilln().strip());
				break;
			case 4:
				wordBuilder = isDe ? wordBuilder.bill(num + SPACE + capitalize(intWordMapping.getBilln().strip()))
						: wordBuilder.bill(num + SPACE + intWordMapping.getBilln().strip());
				break;
			case 3:
				wordBuilder = isDe ? wordBuilder.mill(num + SPACE + capitalize(intWordMapping.getMilln().strip()))
						: wordBuilder.mill(num + SPACE + intWordMapping.getMilln().strip());
				break;
			case 2:
				wordBuilder = isDe ? wordBuilder.thou(num + SPACE + capitalize(intWordMapping.getThoud().strip()))
						: wordBuilder.thou(num + SPACE + intWordMapping.getThoud().strip());
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
