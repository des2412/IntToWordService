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

public class LongToWordBuilder {

	private final IHundConverter hundredthConverter;

	public LongToWordBuilder() {
		hundredthConverter = (j, k) -> (new HundredthConverter().toWordForLang(j, k));
	}

	private String processDeHun(String s, String and, boolean addAnd) {

		final List<String> l = asList(s.split(SPACE));

		switch (l.size()) {
		case 1:
			return s;
		case 2:// add (if > 20) or remove (< 20) 'und'.
			return addAnd ? l.get(1) + and + l.get(0) : l.get(0) + l.get(1).substring(3);

		case 3:
			return l.get(0) + l.get(2) + l.get(1);

		}

		return null;
	}

	/**
	 * tail recursion using numbers sublist and adding to WordBuilder on each
	 * recursion.
	 */
	public Word buildWord(List<String> numbers, WordBuilder wordBuilder, IntWordMapping intWordMapping) {
		final int sz = numbers.size();

		// get zero element index and convert
		String num = hundredthConverter.toWordForLang(numbers.get(0), intWordMapping).orElse(EMPTY);
		final boolean isDe = intWordMapping.getId().equals(DE.name());

		final int val = Integer.parseInt(numbers.get(0));
		// add or remove 'und' for DE word.
		num = (isDe && val % 10 != 0) ? processDeHun(num, intWordMapping.getAnd(), val % 100 > 20) : num;

		if (!isEmpty(num)) {
			switch (sz) {
			case 7:
				wordBuilder = isDe ? wordBuilder.quint(num + SPACE + capitalize(intWordMapping.getQuintn().trim()))
						: wordBuilder.quint(num + SPACE + intWordMapping.getQuintn().trim());
				break;
			case 6:
				wordBuilder = isDe ? wordBuilder.quadr(num + SPACE + capitalize(intWordMapping.getQuadrn().trim()))
						: wordBuilder.quadr(num + SPACE + intWordMapping.getQuadrn().trim());
				break;
			case 5:
				wordBuilder = isDe ? wordBuilder.trill(num + SPACE + capitalize(intWordMapping.getTrilln().trim()))
						: wordBuilder.trill(num + SPACE + intWordMapping.getTrilln().trim());
				break;
			case 4:
				wordBuilder = isDe ? wordBuilder.bill(num + SPACE + capitalize(intWordMapping.getBilln().trim()))
						: wordBuilder.bill(num + SPACE + intWordMapping.getBilln().trim());
				break;
			case 3:
				wordBuilder = isDe ? wordBuilder.mill(num + SPACE + capitalize(intWordMapping.getMilln().trim()))
						: wordBuilder.mill(num + SPACE + intWordMapping.getMilln().trim());
				break;
			case 2:
				wordBuilder = isDe ? wordBuilder.thou(num + SPACE + capitalize(intWordMapping.getThoud().trim()))
						: wordBuilder.thou(num + SPACE + intWordMapping.getThoud().trim());
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
