package org.desz.inttoword.converters;

import static java.util.Arrays.asList;
import static java.util.Locale.UK;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.desz.inttoword.factory.ProvLangFactory.getInstance;

import java.text.NumberFormat;
import java.util.List;
import java.util.logging.Logger;

import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.results.DeDecorator;
import org.desz.inttoword.results.Word;
import org.springframework.stereotype.Component;

/**
 * @author des Converts integer to corresponding word format in ProvLang
 * 
 */
@Component
public class ConversionDelegate {
	protected final Logger log = Logger.getLogger(ConversionDelegate.class.getName());
	private static final NumberFormat nf = NumberFormat.getIntegerInstance(UK);
	private final ILongToWordBuilder wordBuilder;

	public ConversionDelegate() {
		wordBuilder = (j, k, l) -> (new LongToWordBuilder().buildWord(j, k, l));
	}

	/**
	 * 
	 * @param n  the long.
	 * @param pl the ProvLang.
	 * @return the word for n.
	 * @throws AppConversionException
	 */

	public String convertIntToWord(Long n, ProvLang pl) throws AppConversionException {

		final ProvLang provLang = requireNonNull(pl);
		if (provLang.equals(ProvLang.EMPTY))
			throw new AppConversionException();
		final List<String> numbers = asList(nf.format(n).split(","));

		final int sz = numbers.size();

		final IntWordMapping intWordMapping = getInstance().getMapForProvLang(provLang);

		// return result if n < 1000 and contained by IntToWordMapping.
		if (sz == 1) {
			final int val = n.intValue();
			final String s = intWordMapping.containsMapping(val) ? intWordMapping.wordForNum(val) : EMPTY;
			if (!isBlank(s))
				return s.toLowerCase();
		}

		final Word word = wordBuilder.buildWord(numbers, Word.builder(), intWordMapping);

		// apply rules to 'decorate' DE word.
		Word deWord = null;
		if (provLang.equals(ProvLang.DE)) {
			DeDecorator deDecorator = new DeDecorator(word.toBuilder().build());
			deWord = deDecorator.pluraliseUnitRule();
			deDecorator = new DeDecorator(deWord);
			deWord = deDecorator.pluraliseRule(Integer.parseInt(numbers.get(numbers.size() - 1)));
			deDecorator = new DeDecorator(deWord);

			deWord = nonNull(deWord.getHund()) ? deWord.toBuilder().hund(removeSpace(deWord.getHund())).build()
					: deWord;

			deDecorator = new DeDecorator(deWord);
			deWord = nonNull(deWord.getThou()) ? deDecorator.combineThouHundRule() : deWord;

		}

		return isNull(deWord) ? normalizeSpace(stringifyWord(word)) : normalizeSpace(stringifyWord(deWord));

	}

	private String removeSpace(String word) {

		return word.replaceAll(SPACE, EMPTY);
	}

	private String stringifyWord(Word word) {
		StringBuilder sb = new StringBuilder();
		sb = !isNull(word.getQuint()) ? sb.append(word.getQuint() + SPACE) : sb;
		sb = !isNull(word.getQuadr()) ? sb.append(word.getQuadr() + SPACE) : sb;
		sb = !isNull(word.getTrill()) ? sb.append(word.getTrill() + SPACE) : sb;
		sb = !isNull(word.getBill()) ? sb.append(word.getBill() + SPACE) : sb;
		sb = !isNull(word.getMill()) ? sb.append(word.getMill() + SPACE) : sb;
		sb = !isNull(word.getThou()) ? sb.append(word.getThou() + SPACE) : sb;
		sb = !isNull(word.getHund()) ? sb.append(word.getHund()) : sb;

		return sb.toString();
	}

}
