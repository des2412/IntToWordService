package org.desz.inttoword.converters;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;

import java.text.NumberFormat;
import static java.util.Arrays.asList;
import java.util.List;
import static java.util.Locale.UK;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.nonNull;
import java.util.function.BiFunction;
import static java.util.function.Function.identity;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.results.DeDecorator;
import org.desz.inttoword.results.WordResult;
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
		// convert each hundredth to word.
		final Map<Integer, String> wordMap = range(0, sz).boxed()
				.collect(toMap(identity(), i -> funcHunConv.apply(numUnits.get(i), intToWordMapping)));

		final WordResult.Builder wordBuilder = new WordResult.Builder();

		// build with UNIT added to each part if applicable.
		switch (sz) {
		case 1:
			// result returned.
			if (provLang.equals(ProvLang.DE) & n > 20) {
				WordResult wordResult = wordBuilder.withHund(wordMap.get(0)).build();
				wordResult = new DeDecorator(wordResult).rearrangeHundredthRule();
				wordResult = new DeDecorator(wordResult).spaceWithEmptyRule();
				return new DeDecorator(wordResult).pluraliseOneRule(prmLastHun).toString();

			}
			return wordMap.get(0);
		case 4:

			wordBuilder.withBill(wordMap.get(0) + intToWordMapping.getBilln())
					.withMill(wordMap.get(1) + intToWordMapping.getMilln())
					.withThou(wordMap.get(2) + intToWordMapping.getThoud());
			break;

		case 3:
			wordBuilder.withMill(wordMap.get(0) + intToWordMapping.getMilln())
					.withThou(wordMap.get(1) + intToWordMapping.getThoud());

			break;

		case 2:
			wordBuilder.withThou(wordMap.get(0) + intToWordMapping.getThoud());
			break;
		default:
			break;
		}
		if (IHundConverter.inRange(prmLastHun))
			// 1 to 99 -> prepend with AND.
			wordBuilder.withHund(intToWordMapping.getAnd() + wordMap.get(sz - 1));

		else
			wordBuilder.withHund(wordMap.get(sz - 1));

		// wordResult output for non DE case.
		final WordResult wordResult = wordBuilder.build();
		// decorate DE word.
		if (provLang.equals(ProvLang.DE)) {
			WordResult.Builder deBuilder = new WordResult.Builder();
			if (nonNull(wordResult.getBill()))
				deBuilder.withBill(wordResult.getBill().trim());
			if (nonNull(wordResult.getMill()))
				deBuilder.withMill(wordResult.getMill().trim());
			if (nonNull(wordResult.getThou()))
				deBuilder.withThou(wordResult.getThou());
			if (nonNull(wordResult.getHund()))
				deBuilder.withHund(wordMap.get(sz - 1));
			deDecorator = new DeDecorator(deBuilder.build());
			WordResult deRes = deDecorator.pluraliseUnitRule();
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.pluraliseOneRule(prmLastHun);
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.rearrangeHundredthRule();
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.combineThouHundRule();
			// trim, multi to single whitespace.
			return normalizeSpace(deRes.toString());

		}

		return wordResult.toString();

	}

}
