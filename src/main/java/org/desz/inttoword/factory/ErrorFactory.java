package org.desz.inttoword.factory;

import static java.util.Collections.synchronizedMap;
import java.util.HashMap;
import java.util.Map;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.language.ProvLangValues.DeError;
import org.desz.inttoword.language.ProvLangValues.FrError;
import org.desz.inttoword.language.ProvLangValues.NlError;
import org.desz.inttoword.language.ProvLangValues.UkError;

public class ErrorFactory {

	private static Map<ProvLang, Map<String, String>> ERROR_CACHE = synchronizedMap(
			new HashMap<ProvLang, Map<String, String>>());

	private static String enCacheErrors(ProvLang provLang, String key) {

		switch (provLang) {
		case UK:
			ERROR_CACHE.put(ProvLang.UK, of(UkError.values()).collect(toMap(UkError::name, UkError::getError)));

			break;
		case FR:
			ERROR_CACHE.put(ProvLang.FR, of(FrError.values()).collect(toMap(FrError::name, FrError::getError)));

			break;

		case DE:
			ERROR_CACHE.put(ProvLang.DE, of(DeError.values()).collect(toMap(DeError::name, DeError::getError)));

			break;

		case NL:
			ERROR_CACHE.put(ProvLang.NL, of(NlError.values()).collect(toMap(NlError::name, NlError::getError)));

			break;

		default:
			break;

		}
		return ERROR_CACHE.get(provLang).get(key);
	}

	/**
	 * 
	 * @param provLang the ProvLang.
	 * @param key      the key for the error.
	 * @return the error mapped to (provLang) key.
	 */
	public static String getErrorForProvLang(ProvLang provLang, String key) {
		if (provLang.equals(ProvLang.EMPTY))
			return EMPTY;

		return !ERROR_CACHE.containsKey(provLang) ? enCacheErrors(provLang, key) : ERROR_CACHE.get(provLang).get(key);

	}

}
