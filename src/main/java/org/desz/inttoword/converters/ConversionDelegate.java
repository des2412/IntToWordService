package org.desz.inttoword.converters;

import static java.util.Arrays.asList;
import static java.util.Locale.UK;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.desz.inttoword.language.Punct.SPC;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
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

	private IHundConverter hundredthConverter;

	/**
	 * 
	 * @param hundredthConverter the IHundConverter.
	 */
	@Autowired
	public ConversionDelegate(IHundConverter hundredthConverter) {
		this.hundredthConverter = hundredthConverter;
	}

	/**
	 * Function fnhunConv.
	 */
	private BiFunction<String, IntWordMapping, String> fnhunConv = (x, y) -> {
		return hundredthConverter.toWordForLang(x, y).orElse(EMPTY);
	};

	/**
	 * 
	 * @param n the integer.
	 * @return the word for n including units.
	 * @throws AppConversionException
	 */

	public String convertIntToWord(Integer n, ProvLang pl) throws AppConversionException {

		n = requireNonNull(n, "Integer parameter required");
		final ProvLang provLang = requireNonNull(pl);
		if (provLang.equals(ProvLang.EMPTY))
			throw new AppConversionException();
		final List<String> numUnits = asList(nf.format(n).split(","));

		DeDecorator deDecorator = null;
		final int sz = numUnits.size();
		// save last element of numUnits.
		final int prmLastHun = Integer.parseInt(numUnits.get(numUnits.size() - 1));

		final IntWordMapping intToWordMapping = getInstance().getMapForProvLang(provLang);
		// check input, n, is zero.
		if (sz == 1 & prmLastHun == 0)
			return intToWordMapping.wordForNum(0).toLowerCase();
		// map each hundredth.
		final Map<Integer, String> wordMap = range(0, sz).boxed()
				.collect(toMap(identity(), i -> fnhunConv.apply(numUnits.get(i), intToWordMapping)));

		WordBuilder wordBuilder = Word.builder();

		// build with billion, million.. added.
		switch (sz) {
		case 1:
			// result returned.
			if (provLang.equals(ProvLang.DE) & n > 20) {
				Word wordResult = wordBuilder.hund(wordMap.get(0)).build();
				wordResult = new DeDecorator(wordResult).reArrangeHundredthRule();
				wordResult = new DeDecorator(wordResult).spaceWithEmptyRule();
				return new DeDecorator(wordResult).pluraliseRule(prmLastHun).getHund();

			}
			return wordMap.get(0);
		case 4:

			wordBuilder.bill(wordMap.get(0) + intToWordMapping.getBilln());

			wordBuilder = !isEmpty(wordMap.get(1)) ? wordBuilder.mill(wordMap.get(1) + intToWordMapping.getMilln())
					: wordBuilder;

			wordBuilder = !isEmpty(wordMap.get(1)) ? wordBuilder.thou(wordMap.get(2) + intToWordMapping.getThoud())
					: wordBuilder;

			break;

		case 3:
			wordBuilder.mill(wordMap.get(0) + intToWordMapping.getMilln());

			wordBuilder = !isEmpty(wordMap.get(1)) ? wordBuilder.thou(wordMap.get(1) + intToWordMapping.getThoud())
					: wordBuilder;

			break;

		case 2:
			wordBuilder.thou(wordMap.get(0) + intToWordMapping.getThoud());
			break;
		default:
			break;
		}
		wordBuilder = IHundConverter.inRange(prmLastHun)
				? wordBuilder.hund(intToWordMapping.getAnd() + wordMap.get(sz - 1))
				: wordBuilder.hund(wordMap.get(sz - 1));

		// wordResult output for non DE case.
		final Word wordResult = wordBuilder.build();
		// decorate DE word.
		if (provLang.equals(ProvLang.DE)) {
			WordBuilder deBuilder = Word.builder();
			deBuilder = !isEmpty(wordResult.getBill()) ? deBuilder.bill(wordResult.getBill()) : deBuilder;
			deBuilder = !isEmpty(wordResult.getMill()) ? deBuilder.mill(wordResult.getMill()) : deBuilder;
			deBuilder = !isEmpty(wordResult.getThou()) ? deBuilder.thou(wordResult.getThou()) : deBuilder;
			deBuilder = !isEmpty(wordResult.getHund()) ? deBuilder.hund(wordMap.get(sz - 1)) : deBuilder;

			deDecorator = new DeDecorator(deBuilder.build());
			Word deWord = deDecorator.pluraliseUnitRule();
			deDecorator = new DeDecorator(deWord);
			deWord = deDecorator.pluraliseRule(prmLastHun);
			deDecorator = new DeDecorator(deWord);
			deWord = deDecorator.reArrangeHundredthRule();
			deDecorator = new DeDecorator(deWord);
			deWord = deDecorator.combineThouHundRule();

			return normalizeSpace(procWordResult(deWord));

		}

		return normalizeSpace(procWordResult(wordResult));

	}

//TODO add another rule to append space to mill if thou not null.
	private String procWordResult(Word res) {
		StringBuilder sb = new StringBuilder();
		sb = !isNull(res.getBill()) ? sb.append(res.getBill() + SPC.val()) : sb;
		sb = !isNull(res.getMill()) ? sb.append(res.getMill() + SPC.val()) : sb;
		sb = !isNull(res.getThou()) ? sb.append(res.getThou() + SPC.val()) : sb;
		sb = !isNull(res.getHund()) ? sb.append(res.getHund()) : sb;

		return sb.toString();
	}

}
