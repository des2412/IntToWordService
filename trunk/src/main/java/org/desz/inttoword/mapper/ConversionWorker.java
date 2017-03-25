package org.desz.inttoword.mapper;

import static org.desz.inttoword.language.LanguageRepository.Def.SPACE;
import static org.desz.inttoword.language.ProvLangFactory.getInstance;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.LanguageRepository.DeFormat;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.NumericalLangMapping;
import org.desz.inttoword.output.DeWordBuilderDecorator;
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
	 * Builds the word but does not add the unit.
	 * 
	 * @param numericalLangMap
	 *            the ILangProvider for PROV_LANG.
	 * @param prm
	 *            the String representing the number.
	 * @return the word in target PROV_LANG.
	 */
	private String doConversion(String prm,
			NumericalLangMapping numericalLangMap) {
		prm = Objects.requireNonNull(prm);

		final int n = Integer.parseInt(prm);
		final String key = String.valueOf(n);
		final String word = numericalLangMap.getIntToWordMap().get(key);
		if (!Objects.isNull(word)) {
			return word.toLowerCase();
		}
		int nmod = n % 100;
		final String hun = numericalLangMap.getIntToWordMap()
				.get(String.valueOf(n / 100));
		// if true it's whole hundreds
		if (nmod == 0) {
			return hun.toLowerCase() + numericalLangMap.getHund();

		}

		StringBuilder sb = new StringBuilder();
		// build non whole hundreds..

		if (!Objects.isNull(
				numericalLangMap.getIntToWordMap().get(String.valueOf(nmod)))) {
			if (numericalLangMap.getBilln().equals(DeFormat.BILLS.val())) {
				sb.append(hun.toLowerCase() + numericalLangMap.getHund()
						+ numericalLangMap.getIntToWordMap()
								.get(String.valueOf(nmod)).toLowerCase());
			} else {
				sb.append(hun.toLowerCase() + numericalLangMap.getHund()
						+ SPACE.val() + numericalLangMap.getAnd()
						+ numericalLangMap.getIntToWordMap()
								.get(String.valueOf(nmod)).toLowerCase());
			}

			// deal with XXXX DE numbering.

			return sb.toString();
		}

		// nmod % 10 > 0)
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20
		if (inRange(n)) {// 0 to 99.

			sb.append(numericalLangMap.getIntToWordMap().get(String.valueOf(k))
					.toLowerCase() + SPACE.val()
					+ numericalLangMap.getIntToWordMap()
							.get(String.valueOf(nmod)).toLowerCase());
			return sb.toString();

		}

		if (numericalLangMap.getBilln().contains(DeFormat.BILLS.val()))
			sb.append(hun.toLowerCase() + numericalLangMap.getHund()
					+ numericalLangMap.getIntToWordMap()
							.get(String.valueOf(nmod)).toLowerCase()
					+ numericalLangMap.getAnd()
					+ numericalLangMap.getIntToWordMap().get(String.valueOf(k))
							.toLowerCase());
		else
			sb.append(hun.toLowerCase() + numericalLangMap.getHund()
					+ SPACE.val() + numericalLangMap.getAnd()
					+ numericalLangMap.getIntToWordMap().get(String.valueOf(k))
							.toLowerCase()
					+ SPACE.val() + numericalLangMap.getIntToWordMap()
							.get(String.valueOf(nmod)).toLowerCase());

		return sb.toString();
	}

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
		final String fmt = NumberFormat.getIntegerInstance(Locale.UK).format(n);
		// split formatted String into list
		final List<String> numUnits = Arrays.asList(fmt.split(","));
		DeWordBuilderDecorator deWordDecorator = null;
		// save last int.
		int last = Integer.parseInt(numUnits.get(numUnits.size() - 1));
		final List<String> words = new ArrayList<String>();
		// singleton NumericalLangMapping per ProvLang.
		final NumericalLangMapping numericalLangMap = getInstance()
				.numericMap(provLang);
		// stream list.
		numUnits.stream().filter(Objects::nonNull).forEach(s -> {
			words.add(doConversion(s, numericalLangMap));
		});
		final int sz = words.size();
		final WordResult.Builder builder = new WordResult.Builder();

		if (sz == 1) {
			if (provLang.equals(ProvLang.DE)) {
				WordResult deRes = builder.withHund(words.get(sz - 1)).build();
				// decorate
				deWordDecorator = new DeWordBuilderDecorator(
						builder.withHund(words.get(sz - 1)).build());

				return deWordDecorator.pluraliseValueOfOneRule(deRes, last)
						.toString();

			}
			return words.get(0);
		}

		if (sz == 4) {
			builder.withBill(words.get(0) + numericalLangMap.getBilln());
			builder.withMill(words.get(1) + numericalLangMap.getMilln());
			builder.withThou(words.get(2) + numericalLangMap.getThoud());
		}
		if (sz == 3) {
			builder.withMill(words.get(0) + numericalLangMap.getMilln());
			builder.withThou(words.get(1) + numericalLangMap.getThoud());

		}
		if (sz == 2)
			builder.withThou(words.get(0) + numericalLangMap.getThoud());

		if (inRange(last)) // between 1 and 99.
		{

			builder.withHund(numericalLangMap.getAnd() + words.get(sz - 1));

		} else {
			builder.withHund(words.get(sz - 1));
		}

		final WordResult res = builder.build();
		if (provLang.equals(ProvLang.DE)) {
			WordResult.Builder deBuilder = new WordResult.Builder();
			if (Objects.nonNull(res.getBill()))
				deBuilder.withBill(res.getBill().trim());
			if (Objects.nonNull(res.getMill()))
				deBuilder.withMill(res.getMill().trim());
			if (Objects.nonNull(res.getThou()))
				deBuilder.withThou(res.getThou());
			if (Objects.nonNull(res.getHund()))
				deBuilder.withHund(words.get(sz - 1));
			// decorate
			deWordDecorator = new DeWordBuilderDecorator(deBuilder.build());
			WordResult deRes = deWordDecorator.pluraliseUnitRule();
			deWordDecorator = new DeWordBuilderDecorator(deRes);
			deRes = deWordDecorator.pluraliseValueOfOneRule(deRes, last);
			deWordDecorator = new DeWordBuilderDecorator(deRes);

			if (n < 1000000)
				deRes = deWordDecorator.replaceSpaceWithEmptyRule();

			deRes = deWordDecorator.combineThousandsAndHundreds();

			return StringUtils.normalizeSpace(deRes.toString());

		}

		return res.toString();

	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	private boolean inRange(int i) {
		return IntStream.range(1, 100).boxed().collect(Collectors.toSet())
				.contains(i);
	}

}
