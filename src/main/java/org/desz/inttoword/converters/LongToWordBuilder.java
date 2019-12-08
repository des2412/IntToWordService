package org.desz.inttoword.converters;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.desz.inttoword.language.ProvLang.DE;

import java.util.List;

import org.desz.inttoword.language.NumberWordMapping;
import org.desz.inttoword.results.Word;
import org.desz.inttoword.results.Word.WordBuilder;

public class LongToWordBuilder {

	private final IHundConverter hundredthConverter;

	public LongToWordBuilder() {
		hundredthConverter = (j, k) -> (new HundredthConverter().toWordForLang(j, k));
	}

	// add or remove 'und' for DE word.
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
	public Word buildWord(List<String> numbers, WordBuilder wordBuilder, NumberWordMapping intWordMapping) {
		final int sz = numbers.size();

		// get zero element index and convert
		String num = hundredthConverter.toWordForLang(numbers.get(0), intWordMapping).orElse(EMPTY);
		final int val = Integer.parseInt(numbers.get(0));

		if (!isEmpty(num)) {
			num = (intWordMapping.getId().equals(DE.name()))
					? processDeHun(num, intWordMapping.getAnd(), val % 100 > 20)
					: num;

			switch (sz) {
			case 7:
				wordBuilder.quint(num + SPACE + intWordMapping.getQuintn());
				break;
			case 6:
				wordBuilder.quadr(num + SPACE + intWordMapping.getQuadrn());
				break;
			case 5:
				wordBuilder.trill(num + SPACE + intWordMapping.getTrilln());
				break;
			case 4:
				wordBuilder.bill(num + SPACE + intWordMapping.getBilln());
				break;
			case 3:
				wordBuilder.mill(num + SPACE + intWordMapping.getMilln());
				break;
			case 2:
				wordBuilder.thou(num + SPACE + intWordMapping.getThoud());
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
