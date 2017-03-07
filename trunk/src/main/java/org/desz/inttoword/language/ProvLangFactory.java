package org.desz.inttoword.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.desz.inttoword.language.LanguageRepository.DeFormat;
import org.desz.inttoword.language.LanguageRepository.DeIntWordPair;
import org.desz.inttoword.language.LanguageRepository.Def;
import org.desz.inttoword.language.LanguageRepository.FrFormat;
import org.desz.inttoword.language.LanguageRepository.FrIntWordPair;
import org.desz.inttoword.language.LanguageRepository.NlFormat;
import org.desz.inttoword.language.LanguageRepository.NlIntWordPair;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.LanguageRepository.UkIntWordPair;
import org.desz.inttoword.mapper.ConversionWorker;

/**
 * Defines Strings for number constants for a ProvLang.
 * 
 * @see ConversionWorker
 * 
 * @author des
 * 
 */
public final class ProvLangFactory implements ILangProvider {

	private Map<ProvLang, NumericalLangMapping> unitsMap = new HashMap<ProvLang, NumericalLangMapping>();

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
	public NumericalLangMapping factoryForProvLang(final ProvLang provLang) {

		NumericalLangMapping.Builder builder = new NumericalLangMapping.Builder();
		switch (provLang) {
		case UK:

			builder.withBilln(Def.BILLS.val());
			builder.withMilln(Def.MILLS.val());
			builder.withThoud(Def.THOUS.val());
			builder.withHund(Def.HUNS.val());
			builder.withAnd(Def.AND.val());
			builder.withIntToWordMap(Stream.of(UkIntWordPair.values())
					.collect(Collectors.toMap(UkIntWordPair::getNum, UkIntWordPair::getWord)));

			unitsMap.put(ProvLang.UK, builder.build());

			break;
		case FR:

			builder.withBilln(FrFormat.BILLS.val());
			builder.withMilln(FrFormat.MILLS.val());
			builder.withThoud(FrFormat.THOUS.val());
			builder.withHund(FrFormat.HUNS.val());
			builder.withAnd(FrFormat.AND.val());
			builder.withIntToWordMap(Stream.of(FrIntWordPair.values())
					.collect(Collectors.toMap(FrIntWordPair::getNum, FrIntWordPair::getWord)));
			unitsMap.put(ProvLang.FR, builder.build());

			break;

		case DE:

			builder.withBilln(DeFormat.BILLS.val());
			builder.withMilln(DeFormat.MILLS.val());
			builder.withThoud(DeFormat.THOUS.val());
			builder.withHund(DeFormat.HUNS.val());
			builder.withAnd(DeFormat.AND.val());
			builder.withIntToWordMap(Stream.of(DeIntWordPair.values())
					.collect(Collectors.toMap(DeIntWordPair::getNum, DeIntWordPair::getWord)));
			unitsMap.put(ProvLang.DE, builder.build());
			break;

		case NL:

			builder.withBilln(NlFormat.BILLS.val());
			builder.withMilln(NlFormat.MILLS.val());
			builder.withThoud(NlFormat.THOUS.val());
			builder.withHund(NlFormat.HUNS.val());
			builder.withAnd(NlFormat.AND.val());
			builder.withIntToWordMap(Stream.of(NlIntWordPair.values())
					.collect(Collectors.toMap(NlIntWordPair::getNum, NlIntWordPair::getWord)));
			unitsMap.put(ProvLang.NL, builder.build());

			break;

		default:
			break;

		}
		return unitsMap.get(provLang);
	}

}
