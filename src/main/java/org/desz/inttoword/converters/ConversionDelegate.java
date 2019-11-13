package org.desz.inttoword.converters;

import static java.util.Arrays.asList;
import static java.util.Locale.UK;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.desz.inttoword.factory.ProvLangFactory.getInstance;

import java.text.NumberFormat;
import java.util.List;
import java.util.logging.Logger;

import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.results.DeDecorator;
import org.desz.inttoword.results.Word;
import org.desz.inttoword.results.Word.WordBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author des Converts integer to corresponding word format in ProvLang
 * 
 */
@Component
public class ConversionDelegate {
	protected final Logger log = Logger.getLogger(ConversionDelegate.class.getName());
	private static final NumberFormat nf = NumberFormat.getIntegerInstance(UK);

	private ILongToWordBuilder functionalWordBuilder;

	/**
	 * 
	 * @param functionalWordBuilder the IHundConverter.
	 */
	@Autowired
	public ConversionDelegate(ILongToWordBuilder functionalWordBuilder) {
		this.functionalWordBuilder = functionalWordBuilder;
	}

	/**
	 * 
	 * @param n  the integer.
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
			if (!s.isBlank())
				return s.toLowerCase().toLowerCase();
		}

		Word word = functionalWordBuilder.buildWord(numbers, Word.builder(), intWordMapping);

		int hun = Integer.parseInt(numbers.get(numbers.size() - 1));

		// build word
		// apply rules to 'decorate' DE word.
		Word deWord = null;
		if (provLang.equals(ProvLang.DE)) {
			WordBuilder deBuilder = Word.builder();
			deBuilder = !isEmpty(word.getQuint()) ? deBuilder.quint(word.getQuint()) : deBuilder;
			deBuilder = !isEmpty(word.getQuadr()) ? deBuilder.quadr(word.getQuadr()) : deBuilder;
			deBuilder = !isEmpty(word.getTrill()) ? deBuilder.trill(word.getTrill()) : deBuilder;
			deBuilder = !isEmpty(word.getBill()) ? deBuilder.bill(word.getBill()) : deBuilder;
			deBuilder = !isEmpty(word.getMill()) ? deBuilder.mill(word.getMill()) : deBuilder;
			deBuilder = !isEmpty(word.getThou()) ? deBuilder.thou(word.getThou()) : deBuilder;
			deBuilder = !isEmpty(word.getHund()) ? deBuilder.hund(word.getHund()) : deBuilder;

			DeDecorator deDecorator = new DeDecorator(deBuilder.build());
			deWord = deDecorator.pluraliseUnitRule();
			deDecorator = new DeDecorator(deWord);
			deWord = deDecorator.pluraliseRule(hun);
			deDecorator = new DeDecorator(deWord);

			deWord = deDecorator.reArrangeHundredthRule();
			if (sz > 1) {
				String s = nonNull(deWord.getHund()) ? removeUnd(deBuilder.build().getHund(), hun) : EMPTY;
				deWord = !isEmpty(s) ? deWord.toBuilder().hund(s).build() : deWord;
			}
			deWord = nonNull(deWord.getQuint()) ? deWord.toBuilder().quint(removeSpace(deWord.getQuint())).build()
					: deWord;
			deWord = nonNull(deWord.getQuadr()) ? deWord.toBuilder().quadr(removeSpace(deWord.getQuadr())).build()
					: deWord;
			deWord = nonNull(deWord.getTrill()) ? deWord.toBuilder().trill(removeSpace(deWord.getTrill())).build()
					: deWord;
			deWord = nonNull(deWord.getBill()) ? deWord.toBuilder().bill(removeSpace(deWord.getBill())).build()
					: deWord;

			deDecorator = new DeDecorator(deWord);
			deWord = deDecorator.combineThouHundRule();

		}

		return isNull(deWord) ? normalizeSpace(processWord(word)) : normalizeSpace(processWord(deWord));

	}

	private String removeSpace(String word) {

		List<String> lst = asList(word.split(SPACE));
		if (lst.size() < 3)
			return word;
		return lst.subList(0, lst.size() - 1).stream().map(x -> x.replaceAll(SPACE, EMPTY)).collect(joining()) + SPACE
				+ lst.get(lst.size() - 1);
	}

	// TODO put this into DeDecorator.
	private String removeUnd(String s, int val) {
		StringBuilder sb = new StringBuilder();
		final List<String> lst = asList(s.split(SPACE));
		final int k = val % 100;
		switch (lst.size()) {
		case 2:
			sb = !(k < 10) ? sb.append(lst.get(0) + lst.get(1)) : sb.append(lst.get(0) + remove(lst.get(1), "und"));
			break;

		}
		return sb.toString();
	}

//TODO add another rule to append space to mill if thou not null.
	private String processWord(Word word) {
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
