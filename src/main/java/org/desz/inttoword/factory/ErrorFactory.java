package org.desz.inttoword.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.language.ProvLangValues.DeError;
import org.desz.inttoword.language.ProvLangValues.FrError;
import org.desz.inttoword.language.ProvLangValues.NlError;
import org.desz.inttoword.language.ProvLangValues.UkError;

public class ErrorFactory {

	private static Map<ProvLang, Map<String, String>> PROV_LANG_ERR_CACHE = Collections
			.synchronizedMap(new HashMap<ProvLang, Map<String, String>>());

	private void enCacheErrors(ProvLang provLang) {

		if ((PROV_LANG_ERR_CACHE.containsKey(provLang)))
			return;

		Map<String, String> errs = new HashMap<String, String>();
		switch (provLang) {
			case UK :
				errs = Stream.of(UkError.values()).collect(
						Collectors.toMap(UkError::name, UkError::getError));
				PROV_LANG_ERR_CACHE.put(ProvLang.UK, errs);

				break;
			case FR :
				errs = Stream.of(FrError.values()).collect(
						Collectors.toMap(FrError::name, FrError::getError));
				PROV_LANG_ERR_CACHE.put(ProvLang.FR, errs);

				break;

			case DE :
				errs = Stream.of(DeError.values()).collect(
						Collectors.toMap(DeError::name, DeError::getError));
				PROV_LANG_ERR_CACHE.put(ProvLang.DE, errs);

				break;

			case NL :
				errs = Stream.of(NlError.values()).collect(
						Collectors.toMap(NlError::name, NlError::getError));
				PROV_LANG_ERR_CACHE.put(ProvLang.NL, errs);

				break;

			default :
				break;

		}

	}

	/**
	 * 
	 * @param provLang
	 *            the ProvLang.
	 * @param key
	 *            the key for the error.
	 * @return the error mapped to (provLang) key.
	 */
	public String getErrorForProvLang(ProvLang provLang, String key) {
		if (provLang.equals(ProvLang.EMPTY))
			return StringUtils.EMPTY;

		if (!(PROV_LANG_ERR_CACHE.containsKey(provLang)))
			enCacheErrors(provLang);

		return PROV_LANG_ERR_CACHE.get(provLang).entrySet().stream()
				.filter(s -> s.getKey().equals(key)).findFirst().get()
				.getValue();

	}

}
