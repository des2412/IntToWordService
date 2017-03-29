package org.desz.inttoword.mapper;

import static org.desz.inttoword.language.ProvLangFactory.getInstance;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.NumericalLangMapping;
import org.desz.inttoword.output.DeDecorator;
import org.desz.inttoword.output.WordResult;
import org.springframework.stereotype.Component;

/**
 * @author des Converts integer to corresponding word format in ProvLang
 * 
 */
@Component
public class ConversionWorker {
	protected final Logger log = Logger
			.getLogger(ConversionWorker.class.getName());

	/**
	 * funcHunConv.
	 */
	BiFunction<String, NumericalLangMapping, String> funcHunConv = (x, y) -> {
		return CentIntConverter.hundredToWord(x, y);
	};
	/**
	 * 
	 * @param n
	 *            the integer.
	 * @return the word for n including units.
	 * @throws AppConversionException
	 */

	public String convertIntToWord(Integer n, ProvLang provLang)
			throws AppConversionException {

		n = Objects.requireNonNull(n, "Integer parameter required");
		if (provLang.equals(ProvLang.EMPTY))
			throw new AppConversionException();
		final List<String> numUnits = Arrays.asList(NumberFormat
				.getIntegerInstance(Locale.UK).format(n).split(","));
		DeDecorator deDecorator = null;
		// save last element of numUnits.
		final int last = Integer.valueOf(numUnits.get(numUnits.size() - 1));
		// singleton NumericalLangMapping per ProvLang.
		final NumericalLangMapping langMap = getInstance().numericMap(provLang);

		// convert every hundreth to word. Collect into Map, wordMap.
		final Map<Integer, String> wordMap = IntStream.range(0, numUnits.size())
				.boxed().collect(Collectors.toMap(Function.identity(),
						i -> funcHunConv.apply(numUnits.get(i), langMap)));

		final int sz = wordMap.size();
		final WordResult.Builder wordBuilder = new WordResult.Builder();

		if (sz == 1) {
			if (provLang.equals(ProvLang.DE)) {
				WordResult deRes = wordBuilder.withHund(wordMap.get(0)).build();
				// apply DeDecorator.
				deDecorator = new DeDecorator(
						wordBuilder.withHund(wordMap.get(0)).build());

				return deDecorator.pluraliseOneRule(deRes,
						Integer.valueOf(wordMap.get(0))).toString();

			}
			return wordMap.get(0);
		}

		if (sz == 4) {
			wordBuilder.withBill(wordMap.get(0) + langMap.getBilln())
					.withMill(wordMap.get(1) + langMap.getMilln())
					.withThou(wordMap.get(2) + langMap.getThoud());
		}
		if (sz == 3) {
			wordBuilder.withMill(wordMap.get(0) + langMap.getMilln())
					.withThou(wordMap.get(1) + langMap.getThoud());

		}
		if (sz == 2)
			wordBuilder.withThou(wordMap.get(0) + langMap.getThoud());

		// process the final part of formattedNumber
		if (CentIntConverter.inRange(last)) {
			// 1 to 99 -> prepend with AND.
			wordBuilder.withHund(langMap.getAnd() + wordMap.get(sz - 1));

		} else {
			wordBuilder.withHund(wordMap.get(sz - 1));
		}
		// wordResult is output.
		final WordResult wordResult = wordBuilder.build();
		// apply decorator to DE.
		if (provLang.equals(ProvLang.DE)) {
			WordResult.Builder deBuilder = new WordResult.Builder();
			if (Objects.nonNull(wordResult.getBill()))
				deBuilder.withBill(wordResult.getBill().trim());
			if (Objects.nonNull(wordResult.getMill()))
				deBuilder.withMill(wordResult.getMill().trim());
			if (Objects.nonNull(wordResult.getThou()))
				deBuilder.withThou(wordResult.getThou());
			if (Objects.nonNull(wordResult.getHund()))
				deBuilder.withHund(wordMap.get(sz - 1));
			deDecorator = new DeDecorator(deBuilder.build());
			WordResult deRes = deDecorator.pluraliseUnitRule();
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.pluraliseOneRule(deRes, last);
			deDecorator = new DeDecorator(deRes);

			if (n < 1000000)
				deRes = deDecorator.replaceSpaceWithEmptyRule();

			deRes = deDecorator.combineThouHundRule();
			// remove additional whitespace.
			return StringUtils.normalizeSpace(deRes.toString());

		}

		return wordResult.toString();

	}

}
