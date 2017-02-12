package org.desz.inttoword.languages.error;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.language.LanguageRepository.DeError;
import org.desz.inttoword.language.LanguageRepository.FrError;
import org.desz.inttoword.language.LanguageRepository.NlError;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.LanguageRepository.UkError;

public class ErrorFactory {

	private static Map<ProvLang, Map<String, String>> PROV_LANG_ERR_CACHE = Collections
			.synchronizedMap(new HashMap<ProvLang, Map<String, String>>());

	private void cacheLanguageErrors(ProvLang provLang) {

		if ((PROV_LANG_ERR_CACHE.containsKey(provLang)))
			return;

		Map<String, String> errs = new HashMap<String, String>();
		switch (provLang) {
		case UK:
			errs = Stream.of(UkError.values()).collect(Collectors.toMap(UkError::name, UkError::getError));
			PROV_LANG_ERR_CACHE.put(ProvLang.UK, errs);

			break;
		case FR:
			errs = Stream.of(FrError.values()).collect(Collectors.toMap(FrError::name, FrError::getError));
			PROV_LANG_ERR_CACHE.put(ProvLang.FR, errs);

			break;

		case DE:
			errs = Stream.of(DeError.values()).collect(Collectors.toMap(DeError::name, DeError::getError));
			PROV_LANG_ERR_CACHE.put(ProvLang.DE, errs);

			break;

		case NL:
			errs = Stream.of(NlError.values()).collect(Collectors.toMap(NlError::name, NlError::getError));
			PROV_LANG_ERR_CACHE.put(ProvLang.NL, errs);

			break;

		default:
			break;

		}

	}

	/**
	 * 
	 * @param provLang
	 * @param key
	 * @return
	 */
	public String getErrorForProvLang(ProvLang provLang, String key) {
		if (provLang.equals(ProvLang.EMPTY))
			return StringUtils.EMPTY;

		if (!(PROV_LANG_ERR_CACHE.containsKey(provLang)))
			cacheLanguageErrors(provLang);

		List<Entry<String, String>> lst = PROV_LANG_ERR_CACHE.get(provLang).entrySet().stream()
				.filter(s -> s.getKey().equals(key)).collect(Collectors.toList());

		String s = lst.get(0).getValue();
		return s;
	}

}
