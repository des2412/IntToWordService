package org.desz.inttoword.language;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.language.LanguageRepository.Def;
import org.desz.inttoword.language.LanguageRepository.DeError;
import org.desz.inttoword.language.LanguageRepository.DeFormat;
import org.desz.inttoword.language.LanguageRepository.DeNumberWordPair;
import org.desz.inttoword.language.LanguageRepository.FrError;
import org.desz.inttoword.language.LanguageRepository.FrFormat;
import org.desz.inttoword.language.LanguageRepository.FrNumberWordPair;
import org.desz.inttoword.language.LanguageRepository.NlError;
import org.desz.inttoword.language.LanguageRepository.NlFormat;
import org.desz.inttoword.language.LanguageRepository.NlNumberWordPair;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.LanguageRepository.UkError;
import org.desz.inttoword.language.LanguageRepository.UkNumberWordPair;
import org.desz.inttoword.mapper.Int2StrConverter;

import com.google.common.collect.ImmutableMap;

/**
 * Defines Strings for number constants for a ProvLang.
 * 
 * @see Int2StrConverter
 * 
 * @author des
 * 
 */
public final class ProvLangFactory implements ILangProvider {

	private String millUnit;
	private String thouUnit;
	private String hunUnit;
	private String and;
	private String billUnit;
	private ImmutableMap<String, String> wordIntMapping;
	private ImmutableMap<ProvLang, Map<String, String>> provLnToErrMap;

	/**
	 * 
	 * @param provLang
	 *            ProvLang
	 */
	public ProvLangFactory(final ProvLang provLang) {

		Map<String, String> intToWordMap = new HashMap<String, String>();
		Map<String, String> errs = new HashMap<String, String>();
		Map<ProvLang, Map<String, String>> errMap = new HashMap<ProvLang, Map<String, String>>();
		switch (provLang) {
		case UK:
			errs = Stream.of(UkError.values()).collect(Collectors.toMap(UkError::name, UkError::getError));
			errMap.put(ProvLang.UK, errs);
			this.billUnit = Def.BILLS.val();
			this.millUnit = Def.MILLS.val();
			this.thouUnit = Def.THOUS.val();
			this.hunUnit = Def.HUNS.val();
			this.and = Def.AND.val();
			// populate Map of int to word for UK English
			for (UkNumberWordPair intToWord : UkNumberWordPair.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());
			break;
		case FR:
			errs = Stream.of(FrError.values()).collect(Collectors.toMap(FrError::name, FrError::getError));
			errMap.put(ProvLang.FR, errs);
			this.billUnit = FrFormat.BILLS.val();
			this.millUnit = FrFormat.MILLS.val();
			this.thouUnit = FrFormat.THOUS.val();
			this.hunUnit = FrFormat.HUNS.val();
			this.and = FrFormat.AND.val();

			for (FrNumberWordPair intToWord : FrNumberWordPair.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		case DE:
			errs = Stream.of(DeError.values()).collect(Collectors.toMap(DeError::name, DeError::getError));
			errMap.put(ProvLang.DE, errs);
			this.billUnit = DeFormat.BILLS.val();
			this.millUnit = DeFormat.MILLS.val();
			this.thouUnit = DeFormat.THOUS.val();
			this.hunUnit = DeFormat.HUNS.val();
			this.and = DeFormat.AND.val();
			for (DeNumberWordPair intToWord : DeNumberWordPair.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		case NL:
			errs = Stream.of(NlError.values()).collect(Collectors.toMap(NlError::name, NlError::getError));
			errMap.put(ProvLang.NL, errs);
			this.billUnit = NlFormat.BILLS.val();
			this.millUnit = NlFormat.MILLS.val();
			this.thouUnit = NlFormat.THOUS.val();
			this.hunUnit = NlFormat.HUNS.val();
			this.and = NlFormat.AND.val();
			for (NlNumberWordPair intToWord : NlNumberWordPair.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		default:
			break;

		}

		wordIntMapping = new ImmutableMap.Builder<String, String>().putAll(intToWordMap).build();
		provLnToErrMap = new ImmutableMap.Builder<ProvLang, Map<String, String>>().putAll(errMap).build();
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
	public boolean containsWord(String num) {
		return wordIntMapping.containsKey(num);
	}

	@Override
	public String getHunUnit() {
		return hunUnit;
	}

	@Override
	public String getMillUnit() {
		return millUnit;
	}

	@Override
	public String getThouUnit() {
		return thouUnit;
	}

	@Override
	public String getAnd() {
		return and;
	}

	@Override
	public String getBillUnit() {
		return billUnit;
	}

	@Override
	public List<String> unitsList() {
		return Arrays.asList(getBillUnit(), getMillUnit(), getThouUnit(), StringUtils.EMPTY);
	}

	@Override
	public String getErrorForProvLang(ProvLang provLang, String key) {
		if (provLang.equals(ProvLang.EMPTY))
			return StringUtils.EMPTY;
		Set<Entry<String, String>> ents = provLnToErrMap.get(provLang).entrySet();

		List<Entry<String, String>> lst = ents.stream().filter(s -> s.getKey().equals(key))
				.collect(Collectors.toList());

		String s = lst.get(0).getValue();
		return s;
	}

}
