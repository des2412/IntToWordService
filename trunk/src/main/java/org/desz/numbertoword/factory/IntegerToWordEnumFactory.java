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
import org.desz.numbertoword.mapper.IFNumberToWordMapper;
import org.desz.numbertoword.mapper.IntegerToWordMapper;
import org.desz.numbertoword.service.validator.GoogleValidatorAndFormatImpl;
import org.desz.numbertoword.service.validator.IValAndFormatInt;

/**
 * @author des
 * 
 *         Enum Factorys specific for provisioned languages.
 * @see IntegerToWordMapper
 * 
 * 
 */
public enum IntegerToWordEnumFactory implements
		INumberToWordFactory<BigInteger> {

	// Language specific factories
	UK_FAC(), FR_FAC(), DE_FAC(), NL_FAC();

	// Each IFNumberToWord instance once instantiated will be cached.
	private static Map<PROV_LANG, IFNumberToWordMapper<BigInteger>> mappingsCache = Collections
			.synchronizedMap(new HashMap<PROV_LANG, IFNumberToWordMapper<BigInteger>>());

	private final static Logger LOGGER = Logger
			.getLogger(IntegerToWordEnumFactory.class.getName());

	/**
	 * 
	 * @param pl
	 * @return new IntegerToWordMapper
	 * @throws NumberToWordFactoryException
	 */
	private IFNumberToWordMapper<BigInteger> newIntegerToWordMapper(PROV_LANG pl)
			throws NumberToWordFactoryException {
		// access private Constructor using reflection
		Constructor<?>[] constructors = IntegerToWordMapper.class
				.getDeclaredConstructors();

		if (constructors.length == 1)
			constructors[0].setAccessible(true);
		else
			throw new NumberToWordFactoryException(
					"unexpected number of constructors");

		EnumLanguageSupport enumLanguageSupport = new EnumLanguageSupport(pl);
		IFNumberToWordMapper<BigInteger> mapper = null;

		IValAndFormatInt validator = new GoogleValidatorAndFormatImpl(
				enumLanguageSupport);

		try {
			mapper = (IFNumberToWordMapper<BigInteger>) constructors[0]
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
	private boolean isCached(PROV_LANG pl) {
		final boolean isCached = mappingsCache.containsKey(pl);
		if (isCached) {
			LOGGER.info("IntegerToWordMapper for language " + pl.name()
					+ " available");
		}
		return isCached;
	}

	private static final int nThreads = Runtime.getRuntime()
			.availableProcessors();

	/**
	 * Each instance is specific for a PROV_LANG.
	 * 
	 * @see EnumLanguageSupport
	 * 
	 *      Instances will be cached for reuse
	 * 
	 */
	@Override
	public IFNumberToWordMapper<BigInteger> getIntegerToWordMapper()
			throws NumberToWordFactoryException {

		IFNumberToWordMapper<BigInteger> integerToWordMapper;
		switch (this) {
		case UK_FAC:
			// check mappingsCache
			if (isCached(PROV_LANG.UK)) {
				return mappingsCache.get(PROV_LANG.UK);
			}

			integerToWordMapper = newIntegerToWordMapper(PROV_LANG.UK);

			mappingsCache.put(PROV_LANG.UK,
					integerToWordMapper);
			break;

		case FR_FAC:
			if (isCached(PROV_LANG.FR)) {
				return mappingsCache.get(PROV_LANG.FR);
			}

			integerToWordMapper = newIntegerToWordMapper(PROV_LANG.FR);
			mappingsCache.put(PROV_LANG.FR,
					integerToWordMapper);
			break;
		case DE_FAC:
			if (isCached(PROV_LANG.DE)) {
				return mappingsCache.get(PROV_LANG.DE);
			}

			integerToWordMapper = newIntegerToWordMapper(PROV_LANG.DE);
			mappingsCache.put(PROV_LANG.DE,
					integerToWordMapper);
			break;

		case NL_FAC:
			if (isCached(PROV_LANG.NL)) {
				return mappingsCache.get(PROV_LANG.NL);
			}

			integerToWordMapper = newIntegerToWordMapper(PROV_LANG.NL);
			mappingsCache.put(PROV_LANG.NL,
					integerToWordMapper);
			break;
		default:
			LOGGER.info("Unknown problem creating Factory");
			throw new NumberToWordFactoryException(UK_ERRORS.UNKNOWN.getError());

		}

		LOGGER.info("Cached "
				+ this.name()
				+ " IntegerToWordMapper. Number of configured IntegerToWordMappers :"
				+ mappingsCache.size());
		return integerToWordMapper;
	}

	/**
	 * Constructor
	 */
	private IntegerToWordEnumFactory() {
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
