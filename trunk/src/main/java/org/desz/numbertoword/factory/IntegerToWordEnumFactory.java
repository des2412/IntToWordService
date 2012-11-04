/**
 * 
 */
package org.desz.numbertoword.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.language.LanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.FR_WORDS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LN;
import org.desz.numbertoword.enums.EnumHolder.UK_WORDS;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;
import org.desz.numbertoword.mapper.IntegerToWordMapper;

/**
 * @author des
 * 
 *         Enums for target languages.
 *         @see IntegerToWordMapper
 *         
 * 
 */
public enum IntegerToWordEnumFactory implements INumberToWordFactory<BigInteger> {

	UK_FAC(), FR_FAC();

	private static Map<PROVISIONED_LN, IntegerToWordEnumFactory> cache = Collections
			.synchronizedMap(new HashMap<PROVISIONED_LN, IntegerToWordEnumFactory>());

	private final static Logger LOGGER = Logger
			.getLogger(IntegerToWordEnumFactory.class.getName());

	private IFNumberToWordMapper<BigInteger> integerToWordMapper;

	/**
	 * Invokes the private NumberToWordMapper constructor
	 * 
	 * @param args
	 * @return
	 * @throws NumberToWordFactoryException
	 */
	private IFNumberToWordMapper<BigInteger> newIntegerToWord(final Object[] args)
			throws NumberToWordFactoryException {
		// access private Constructor of IntegerToWordMapper using reflection
		final Constructor<?>[] constructors = IntegerToWordMapper.class
				.getDeclaredConstructors();
		if(constructors.length == 1)
		constructors[0].setAccessible(true);
		else
			throw new NumberToWordFactoryException("unexpected number of constructors");

		IFNumberToWordMapper<BigInteger> mapper = null;

		try {
			mapper = (IFNumberToWordMapper<BigInteger>) constructors[0]
					.newInstance(args);
		} catch (InstantiationException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (IllegalAccessException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (IllegalArgumentException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (InvocationTargetException e1) {
			LOGGER.severe(e1.getMessage());
		}
		if (mapper == null) {
			throw new NumberToWordFactoryException(
					"Did not initialise NumberToWordMapper");
		}
		return mapper;
	}

	/**
	 * 
	 * @param pl
	 * @return
	 */
	private boolean isCached(PROVISIONED_LN pl) {
		final boolean inCache = cache.containsKey(pl);
		if (inCache) {
			LOGGER.info("Initialised instance of IntegerToWordMapper for language "
					+ pl.name() + " available");
		}
		return inCache;
	}

	/**
	 * @param <T>
	 * @param PROVISIONED_LN
	 */
	@Override
	public IFNumberToWordMapper<BigInteger> getIntegerToWordMapper()
			throws NumberToWordFactoryException {

		final Object[] args = new Object[1];

		LanguageSupport languageSupport = null;

		switch (this) {
		case UK_FAC:
			// check cache
			if (isCached(PROVISIONED_LN.UK)) {
				return cache.get(PROVISIONED_LN.UK).integerToWordMapper;
			}

			languageSupport = new LanguageSupport(PROVISIONED_LN.UK);
			args[0] = languageSupport;
			// invoke private constructor
			this.integerToWordMapper = newIntegerToWord(args);
			// cache 'this' in Map
			cache.put(PROVISIONED_LN.UK, this);
			break;

		case FR_FAC:
			if (isCached(PROVISIONED_LN.FR)) {
				return cache.get(PROVISIONED_LN.FR).integerToWordMapper;
			}

			languageSupport = new LanguageSupport(PROVISIONED_LN.FR);
			args[0] = languageSupport;
			this.integerToWordMapper = newIntegerToWord(args);
			cache.put(PROVISIONED_LN.FR, this);
			break;

		default:
			throw new NumberToWordFactoryException(
					languageSupport.getUnkownErr());

		}
		((IntegerToWordMapper) this.integerToWordMapper)
				.setMapping(initialiseMapping());

		LOGGER.info("Added "
				+ this.name()
				+ " to cache Map. Number of configured NumberToWordEnumFactories :"
				+ cache.size());
		return this.integerToWordMapper;
	}

	/**
	 * Constructor
	 */
	private IntegerToWordEnumFactory() {
	}

	/**
	 * Provisions Map of Integer to language specific word
	 * 
	 * @return int to word Map
	 */
	private Map<String, String> initialiseMapping() {
		Map<String, String> intToWordMap = new HashMap<String, String>();
		switch (this) {
		case UK_FAC:
			for (UK_WORDS intToWord : UK_WORDS.values()) {
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}

			break;
		case FR_FAC:
			for (FR_WORDS intToWord : FR_WORDS.values()) {
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}
			break;
		default:
			break;

		}
		return intToWordMap;
	}

	/**
	 * 
	 * @param provLang
	 * @return
	 * @throws FactoryMapperRemovalException
	 */
	public static boolean removeNumberToWordEnumFactory(
			final PROVISIONED_LN provLang)
			throws FactoryMapperRemovalException {

		if (cache.containsKey(provLang)) {

			try {
				cache.remove(provLang);
				LOGGER.info("Removal of " + provLang.name()
						+ " NumberToWordEnumFactory result:"
						+ !cache.containsKey(provLang));

			} catch (Exception e) {
				throw new FactoryMapperRemovalException(e);
			}

		} else {
			LOGGER.info("References absent for " + provLang.name());
		}

		return !cache.containsKey(provLang);
	}

}
