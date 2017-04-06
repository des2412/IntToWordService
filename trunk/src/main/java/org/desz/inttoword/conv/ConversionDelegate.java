package org.desz.inttoword.conv;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;

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
import org.desz.inttoword.exceptions.ConversionParameterException;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.output.DeDecorator;
import org.desz.inttoword.output.WordResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author des Converts integer to corresponding word format in ProvLang
 * 
 */
@Component
public class ConversionDelegate {
	protected final Logger log = Logger
			.getLogger(ConversionDelegate.class.getName());

	private CenturionConverter<?> centurionConverter;

	/**
	 * 
	 * @param centurionConverter
	 *            the parameterised instance.
	 */
	@Autowired
	public ConversionDelegate(CenturionConverter<?> centurionConverter) {
		this.centurionConverter = centurionConverter;
	}

	/**
	 * TODO: Deduce type of centurionConverter at Runtime.
	 * 
	 * Method is required to throw Exception from funcHunConverter.
	 *
	 * @throws ConversionParameterException
	 */
	private void throwExc() throws ConversionParameterException {
		throw new ConversionParameterException();
	}
	/**
	 * Function funcHunConv.
	 */
	private BiFunction<String, IntWordMapping, String> funcHunConv = (x, y) -> {
		String res = null;
		try {
			res = centurionConverter.hundrethToWord(x, y);

		} catch (Exception e) {
			try {
				throwExc();
			} catch (ConversionParameterException e1) {
			}
		}
		return res;
	};

	/**
	 * 
	 * @param n
	 *            the integer.
	 * @return the word for n including units.
	 * @throws AppConversionException
	 */

	public String convertIntToWord(Integer n, ProvLang p_provLang)
			throws AppConversionException {

		n = Objects.requireNonNull(n,
				"Integer parameter required to be non-null");
		final ProvLang provLang = Objects.requireNonNull(p_provLang);
		if (provLang.equals(ProvLang.EMPTY))
			throw new AppConversionException();
		final List<String> numUnits = Arrays.asList(NumberFormat
				.getIntegerInstance(Locale.UK).format(n).split(","));
		DeDecorator deDecorator = null;
		final int sz = numUnits.size();
		// save last element of numUnits..
		final int prmLastHun = Integer
				.parseInt(numUnits.get(numUnits.size() - 1));
		// singleton IntWordMapping per ProvLang.
		final IntWordMapping langMap = getInstance()
				.getMapForProvLang(provLang);
		// convert each hundreth to word.
		final Map<Integer, String> wordMap = IntStream.range(0, sz).boxed()
				.collect(Collectors.toMap(Function.identity(),
						i -> funcHunConv.apply(numUnits.get(i), langMap)));

		final WordResult.Builder wordBuilder = new WordResult.Builder();

		// build with UNIT added to each part if applicable.
		switch (sz) {
			case 1 :
				// result returned.
				if (provLang.equals(ProvLang.DE)) {
					WordResult deRes = wordBuilder.withHund(wordMap.get(0))
							.build();
					deRes = new DeDecorator(deRes).restructureHundrethRule();
					return new DeDecorator(deRes).pluraliseOneRule(prmLastHun)
							.toString();

				}
				return wordMap.get(0);
			case 4 :

				wordBuilder.withBill(wordMap.get(0) + langMap.getBilln())
						.withMill(wordMap.get(1) + langMap.getMilln())
						.withThou(wordMap.get(2) + langMap.getThoud());
				break;

			case 3 :
				wordBuilder.withMill(wordMap.get(0) + langMap.getMilln())
						.withThou(wordMap.get(1) + langMap.getThoud());

				break;

			case 2 :
				wordBuilder.withThou(wordMap.get(0) + langMap.getThoud());
				break;
			default :
				break;
		}
		if (CenturionConverter.inRange(prmLastHun))
			// 1 to 99 -> prepend with AND.
			wordBuilder.withHund(langMap.getAnd() + wordMap.get(sz - 1));

		else
			wordBuilder.withHund(wordMap.get(sz - 1));

		// wordResult output for non DE case.
		final WordResult wordResult = wordBuilder.build();
		// decorate DE word.
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
			deRes = deDecorator.pluraliseOneRule(prmLastHun);
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.restructureHundrethRule();
			deDecorator = new DeDecorator(deRes);
			deRes = deDecorator.combineThouHundRule();
			// trim, multi to single whitespace.
			return StringUtils.normalizeSpace(deRes.toString());

		}

		return wordResult.toString();

	}

}
