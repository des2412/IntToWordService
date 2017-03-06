package org.desz.inttoword.language;

import java.util.Collections;
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

import com.google.common.collect.ImmutableMap;

/**
 * Defines Strings for number constants for a ProvLang.
 * 
 * @see ConversionWorker
 * 
 * @author des
 * 
 */
public final class ProvLangFactory implements ILangProvider {

	private String milln;
	private String thoud;
	private String hund;
	private String and;
	private String billn;
	private ImmutableMap<String, String> wordIntMapping;
	private static Map<ProvLang, ILangProvider> PROV_LANG_CACHE = Collections
			.synchronizedMap(new HashMap<ProvLang, ILangProvider>());

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
	public ILangProvider factoryForProvLang(final ProvLang provLang) {

		if (PROV_LANG_CACHE.containsKey(provLang))
			return PROV_LANG_CACHE.get(provLang);
		// create Map of int to provLang word(s).
		Map<String, String> intToWordMap = new HashMap<String, String>();

		switch (provLang) {
		case UK:
			this.billn = Def.BILLS.val();
			this.milln = Def.MILLS.val();
			this.thoud = Def.THOUS.val();
			this.hund = Def.HUNS.val();
			this.and = Def.AND.val();
			// populate Map of int to word for UK English
			intToWordMap = Stream.of(UkIntWordPair.values())
					.collect(Collectors.toMap(UkIntWordPair::getNum, UkIntWordPair::getWord));
			break;
		case FR:

			this.billn = FrFormat.BILLS.val();
			this.milln = FrFormat.MILLS.val();
			this.thoud = FrFormat.THOUS.val();
			this.hund = FrFormat.HUNS.val();
			this.and = FrFormat.AND.val();

			intToWordMap = Stream.of(FrIntWordPair.values())
					.collect(Collectors.toMap(FrIntWordPair::getNum, FrIntWordPair::getWord));

			break;

		case DE:

			this.billn = DeFormat.BILLS.val();
			this.milln = DeFormat.MILLS.val();
			this.thoud = DeFormat.THOUS.val();
			this.hund = DeFormat.HUNS.val();
			this.and = DeFormat.AND.val();

			intToWordMap = Stream.of(DeIntWordPair.values())
					.collect(Collectors.toMap(DeIntWordPair::getNum, DeIntWordPair::getWord));

			break;

		case NL:

			this.billn = NlFormat.BILLS.val();
			this.milln = NlFormat.MILLS.val();
			this.thoud = NlFormat.THOUS.val();
			this.hund = NlFormat.HUNS.val();
			this.and = NlFormat.AND.val();

			intToWordMap = Stream.of(NlIntWordPair.values())
					.collect(Collectors.toMap(NlIntWordPair::getNum, NlIntWordPair::getWord));

			break;

		default:
			break;

		}
		// immutable list of integer to word for provLang.
		wordIntMapping = new ImmutableMap.Builder<String, String>().putAll(intToWordMap).build();
		// cache this Object keyed by provLang.
		PROV_LANG_CACHE.put(provLang, this);
		return PROV_LANG_CACHE.get(provLang);
	}

	/**
	 * return the word for PROV_LN
	 */
	@Override
	public String getWord(String num) {
		Objects.requireNonNull(num);
		return wordIntMapping.get(num);
	}

	@Override
	public String getHundred() {
		return hund;
	}

	@Override
	public String getMillion() {
		return milln;
	}

	@Override
	public String getThousand() {
		return thoud;
	}

	@Override
	public String getAnd() {
		return and;
	}

	@Override
	public String getBillion() {
		return billn;
	}

}
