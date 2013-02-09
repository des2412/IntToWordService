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

import org.desz.language.EnumLanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.INumberToWordMapper;
import org.desz.numbertoword.mapper.IntToWord;
import org.desz.numbertoword.service.validator.IFormatter;
import org.desz.numbertoword.service.validator.UkIntValidator;

/**
 * @author des
 * 
 *         Enum Factorys specific for provisioned languages.
 * @see IntToWord
 * 
 * 
 */
public enum IntToWordEnumFactory implements INumberToWordFactory<BigInteger> {

	// Language specific factories
	UK_FAC(), FR_FAC(), DE_FAC(), NL_FAC();

	// Each IFNumberToWord instance once instantiated will be cached.
	private static Map<PROV_LANG, INumberToWordMapper<BigInteger>> mappingsCache = Collections
			.synchronizedMap(new HashMap<PROV_LANG, INumberToWordMapper<BigInteger>>());

	private final static Logger LOGGER = Logger
			.getLogger(IntToWordEnumFactory.class.getName());

	/**
	 * 
	 * @param pl
	 * @return new IntToWord
	 * @throws NumberToWordFactoryException
	 */
	@SuppressWarnings("unchecked")
	private static final INumberToWordMapper<BigInteger> newIntToWord(
			PROV_LANG pl) throws NumberToWordFactoryException {
		// access private Constructor using reflection
		Constructor<?>[] constructors = IntToWord.class
				.getDeclaredConstructors();

		if (constructors.length == 1) {
			constructors[0].setAccessible(true);
		} else {
			throw new NumberToWordFactoryException(
					"IntToWord.class: Detected an unexpected number of Constructors");
		}

		EnumLanguageSupport enumLanguageSupport = new EnumLanguageSupport(pl);
		INumberToWordMapper<BigInteger> mapper = null;

		IFormatter validator = new UkIntValidator(enumLanguageSupport);

		try {
			mapper = (INumberToWordMapper<BigInteger>) constructors[0]
					.newInstance(new Object[] { enumLanguageSupport, validator });
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
	private static boolean isCached(PROV_LANG pl) {
		final boolean isCached = mappingsCache.containsKey(pl);
		if (isCached) {
			LOGGER.info("IntToWord for language " + pl.name() + " available");
		}
		return isCached;
	}

	/**
	 * 
	 * @param pl
	 * @return
	 * @throws NumberToWordFactoryException
	 */
	public static INumberToWordMapper<BigInteger> getMapper(PROV_LANG pl)
			throws NumberToWordFactoryException {
		INumberToWordMapper<BigInteger> integerToWordMapper;
		switch (pl) {
		case UK:
			// check mappingsCache
			if (isCached(PROV_LANG.UK)) {
				return mappingsCache.get(PROV_LANG.UK);
			}

			integerToWordMapper = newIntToWord(PROV_LANG.UK);

			mappingsCache.put(PROV_LANG.UK, integerToWordMapper);
			break;

		case FR:
			if (isCached(PROV_LANG.FR)) {
				return mappingsCache.get(PROV_LANG.FR);
			}

			integerToWordMapper = newIntToWord(PROV_LANG.FR);
			mappingsCache.put(PROV_LANG.FR, integerToWordMapper);
			break;
		case DE:
			if (isCached(PROV_LANG.DE)) {
				return mappingsCache.get(PROV_LANG.DE);
			}

			integerToWordMapper = newIntToWord(PROV_LANG.DE);
			mappingsCache.put(PROV_LANG.DE, integerToWordMapper);
			break;

		case NL:
			if (isCached(PROV_LANG.NL)) {
				return mappingsCache.get(PROV_LANG.NL);
			}

			integerToWordMapper = newIntToWord(PROV_LANG.NL);
			mappingsCache.put(PROV_LANG.NL, integerToWordMapper);
			break;
		default:
			LOGGER.info("Unknown problem creating Factory");
			throw new NumberToWordFactoryException(UK_ERRORS.UNKNOWN.getError());

		}

		LOGGER.info("Cached " + pl.name()
				+ " IntToWord. Number of configured IntegerToWordMappers :"
				+ mappingsCache.size());
		return integerToWordMapper;
	}

	/**
	 * Each instance is specific for a PROV_LANG.
	 * 
	 * @see EnumLanguageSupport
	 * 
	 *      Instances will be cached for reuse
	 * 
	 */
	@Override
	public INumberToWordMapper<BigInteger> getIntegerToWordMapper()
			throws NumberToWordFactoryException {

		INumberToWordMapper<BigInteger> integerToWordMapper;
		switch (this) {
		case UK_FAC:
			// check mappingsCache
			if (isCached(PROV_LANG.UK)) {
				return mappingsCache.get(PROV_LANG.UK);
			}

			integerToWordMapper = newIntToWord(PROV_LANG.UK);

			mappingsCache.put(PROV_LANG.UK, integerToWordMapper);
			break;

		case FR_FAC:
			if (isCached(PROV_LANG.FR)) {
				return mappingsCache.get(PROV_LANG.FR);
			}

			integerToWordMapper = newIntToWord(PROV_LANG.FR);
			mappingsCache.put(PROV_LANG.FR, integerToWordMapper);
			break;
		case DE_FAC:
			if (isCached(PROV_LANG.DE)) {
				return mappingsCache.get(PROV_LANG.DE);
			}

			integerToWordMapper = newIntToWord(PROV_LANG.DE);
			mappingsCache.put(PROV_LANG.DE, integerToWordMapper);
			break;

		case NL_FAC:
			if (isCached(PROV_LANG.NL)) {
				return mappingsCache.get(PROV_LANG.NL);
			}

			integerToWordMapper = newIntToWord(PROV_LANG.NL);
			mappingsCache.put(PROV_LANG.NL, integerToWordMapper);
			break;
		default:
			LOGGER.info("Unknown problem creating Factory");
			throw new NumberToWordFactoryException(UK_ERRORS.UNKNOWN.getError());

		}

		LOGGER.info("Cached " + this.name()
				+ " IntToWord. Number of configured IntegerToWordMappers :"
				+ mappingsCache.size());
		return integerToWordMapper;
	}

	/**
	 * Constructor
	 */
	private IntToWordEnumFactory() {
	}

	/**
	 * Purge cache of specific Factory
	 * 
	 * @param provLang
	 * @return
	 * @throws FactoryMapperRemovalException
	 */
	public static boolean removeNumberToWordEnumFactory(final PROV_LANG provLang)
			throws FactoryMapperRemovalException {

		if (mappingsCache.containsKey(provLang)) {

			try {
				mappingsCache.remove(provLang);
				LOGGER.info("Removal of " + provLang.name()
						+ " NumberToWordEnumFactory result:"
						+ !mappingsCache.containsKey(provLang));

			} catch (Exception e) {
				throw new FactoryMapperRemovalException(e);
			}

		} else {
			LOGGER.info("Reference removed from mappingsCache for "
					+ provLang.name());
		}

		return !mappingsCache.containsKey(provLang);
	}

}
