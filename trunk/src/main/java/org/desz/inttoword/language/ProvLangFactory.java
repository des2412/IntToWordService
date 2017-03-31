package org.desz.inttoword.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.desz.inttoword.language.LanguageRepository.DeUnit;
import org.desz.inttoword.converter.ConversionDelegate;
import org.desz.inttoword.language.LanguageRepository.DeIntWordPair;
import org.desz.inttoword.language.LanguageRepository.DefUnit;
import org.desz.inttoword.language.LanguageRepository.FrUnit;
import org.desz.inttoword.language.LanguageRepository.FrIntWordPair;
import org.desz.inttoword.language.LanguageRepository.NlUnit;
import org.desz.inttoword.language.LanguageRepository.NlIntWordPair;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.LanguageRepository.UkIntWordPair;

/**
 * Defines Strings for number constants for a ProvLang.
 * 
 * @see ConversionDelegate
 * 
 * @author des
 * 
 */
public final class ProvLangFactory implements ILangProvider {

	private Map<ProvLang, NumericalLangMapping> langMap = new HashMap<ProvLang, NumericalLangMapping>();

	private static ILangProvider langProvider;

	/**
	 * enforce singleton contract.
	 */
	private ProvLangFactory() {
	}

	/**
	 * 
	 * @return singleton instance.
	 */
	public static ILangProvider getInstance() {
		if (Objects.isNull(langProvider))
			langProvider = new ProvLangFactory();
		return langProvider;
	}

	@Override
	public NumericalLangMapping numericMap(final ProvLang provLang) {

		NumericalLangMapping.Builder builder = new NumericalLangMapping.Builder();
		switch (provLang) {
		case UK:

			builder.withBilln(DefUnit.BILLS.val());
			builder.withMilln(DefUnit.MILLS.val());
			builder.withThoud(DefUnit.THOUS.val());
			builder.withHund(DefUnit.HUNS.val());
			builder.withAnd(DefUnit.AND.val());
			builder.withIntToWordMap(Stream.of(UkIntWordPair.values())
					.collect(Collectors.toMap(UkIntWordPair::getNum, UkIntWordPair::getWord)));

			langMap.put(ProvLang.UK, builder.build());

			break;
		case FR:

			builder.withBilln(FrUnit.BILLS.val());
			builder.withMilln(FrUnit.MILLS.val());
			builder.withThoud(FrUnit.THOUS.val());
			builder.withHund(FrUnit.HUNS.val());
			builder.withAnd(FrUnit.AND.val());
			builder.withIntToWordMap(Stream.of(FrIntWordPair.values())
					.collect(Collectors.toMap(FrIntWordPair::getNum, FrIntWordPair::getWord)));
			langMap.put(ProvLang.FR, builder.build());

			break;

		case DE:

			builder.withBilln(DeUnit.BILLS.val());
			builder.withMilln(DeUnit.MILLS.val());
			builder.withThoud(DeUnit.THOUS.val());
			builder.withHund(DeUnit.HUNS.val());
			builder.withAnd(DeUnit.AND.val());
			builder.withIntToWordMap(Stream.of(DeIntWordPair.values())
					.collect(Collectors.toMap(DeIntWordPair::getNum, DeIntWordPair::getWord)));
			langMap.put(ProvLang.DE, builder.build());
			break;

		case NL:

			builder.withBilln(NlUnit.BILLS.val());
			builder.withMilln(NlUnit.MILLS.val());
			builder.withThoud(NlUnit.THOUS.val());
			builder.withHund(NlUnit.HUNS.val());
			builder.withAnd(NlUnit.AND.val());
			builder.withIntToWordMap(Stream.of(NlIntWordPair.values())
					.collect(Collectors.toMap(NlIntWordPair::getNum, NlIntWordPair::getWord)));
			langMap.put(ProvLang.NL, builder.build());

			break;

		default:
			break;

		}
		return langMap.get(provLang);
	}

}
