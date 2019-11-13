package org.desz.inttoword.converters;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.results.Word;
import org.desz.inttoword.results.Word.WordBuilder;

public class LongToWordBuilder implements ILongToWordBuilder {

	private static HundredthConverter hundredthConverter = HundredthConverter.getInstance();

	/**
	 * tail recursion.
	 */
	@Override
	public Word buildWord(List<String> numbers, WordBuilder wordBuilder, IntWordMapping intWordMapping) {
		final int sz = numbers.size();

		// get zero element index and convert
		final String num = hundredthConverter.toWordForLang(numbers.get(0), intWordMapping).orElse(EMPTY);

		if (!StringUtils.isEmpty(num)) {
			switch (sz) {
			case 7:
				wordBuilder.quint(num + intWordMapping.getQuintn());
				break;
			case 6:
				wordBuilder.quadr(num + intWordMapping.getQuadrn());
				break;
			case 5:
				wordBuilder.trill(num + intWordMapping.getTrilln());
				break;
			case 4:
				wordBuilder.bill(num + intWordMapping.getBilln());
				break;
			case 3:
				wordBuilder.mill(num + intWordMapping.getMilln());
				break;
			case 2:
				wordBuilder.thou(num + intWordMapping.getThoud());
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
