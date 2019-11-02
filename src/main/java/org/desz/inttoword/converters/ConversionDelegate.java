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
import org.desz.inttoword.results.WordResult;
import org.desz.inttoword.results.WordResult.WordResultBuilder;
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
	 * Function funcHunConv.
	 */
	private BiFunction<String, IntWordMapping, String> funcHunConv = (x, y) -> {
		return hundredthConverter.hundredthToWord(x, y).orElse(EMPTY);
	};

	/**
	 * 
	 * @param n the integer.
	 * @return the word for n including units.
	 * @throws AppConversionException
	 */

	public String convertIntToWord(Integer n, ProvLang pl) throws AppConversionException {

		n = requireNonNull(n, "Integer parameter required to be non-null");
		final ProvLang provLang = requireNonNull(pl);
		if (provLang.equals(ProvLang.EMPTY))
			throw new AppConversionException();
		final List<String> numUnits = asList(nf.format(n).split(","));

		DeDecorator deDecorator = null;
		final int sz = numUnits.size();
		// save last element of numUnits..
		final int prmLastHun = Integer.parseInt(numUnits.get(numUnits.size() - 1));

		// singleton IntWordMapping per ProvLang.
		final IntWordMapping intToWordMapping = getInstance().getMapForProvLang(provLang);
		// check input, n, is zero.
		if (sz == 1 & prmLastHun == 0)
			return intToWordMapping.wordForNum(0).toLowerCase();
		// convert each hundredth to word.
		final Map<Integer, String> wordMap = range(0, sz).boxed()
				.collect(toMap(identity(), i -> funcHunConv.apply(numUnits.get(i), intToWordMapping)));

		WordResultBuilder wordBuilder = WordResult.builder();

		// build with billion, million.. added.
		switch (sz) {
		case 1:
			// result returned.
			if (provLang.equals(ProvLang.DE) & n > 20) {
				WordResult wordResult = wordBuilder.hund(wordMap.get(0)).build();
				wordResult = new DeDecorator(wordResult).rearrangeHundredthRule();
				wordResult = new DeDecorator(wordResult).spaceWithEmptyRule();
				return new DeDecorator(wordResult).pluraliseOneRule(prmLastHun).getHund();

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
		final WordResult wordResult = wordBuilder.build();
		// decorate DE word.
		if (provLang.equals(ProvLang.DE)) {
			WordResultBuilder deBuilder = WordResult.builder();
			deBuilder = !isEmpty(wordResult.getBill()) ? deBuilder.bill(wordResult.getBill()) : deBuilder;
			deBuilder = !isEmpty(wordResult.getMill()) ? deBuilder.mill(wordResult.getMill()) : deBuilder;
			deBuilder = !isEmpty(wordResult.getThou()) ? deBuilder.thou(wordResult.getThou()) : deBuilder;
			deBuilder = !isEmpty(wordResult.getHund()) ? deBuilder.hund(wordMap.get(sz - 1)) : deBuilder;

			deDecorator = new DeDecorator(deBuilder.build());
			WordResult deRes = deDecorator.pluraliseUnitRule();
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.pluraliseOneRule(prmLastHun);
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.rearrangeHundredthRule();
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.combineThouHundRule();
			// trim, multi to single whitespace.
			String k = procWordResult(deRes);
			return normalizeSpace(k);

		}

		return normalizeSpace(procWordResult(wordResult));

	}

//TODO add another rule to append space to mill if thou not null.
	private String procWordResult(WordResult res) {
		StringBuilder sb = new StringBuilder();
		sb = !isNull(res.getBill()) ? sb.append(res.getBill() + SPC.val()) : sb;
		sb = !isNull(res.getMill()) ? sb.append(res.getMill() + SPC.val()) : sb;
		sb = !isNull(res.getThou()) ? sb.append(res.getThou() + SPC.val()) : sb;
		sb = !isNull(res.getHund()) ? sb.append(res.getHund()) : sb;

		return sb.toString();
	}

}
