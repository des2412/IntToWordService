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
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LN;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;
import org.desz.numbertoword.mapper.IntegerToWordMapper;

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
	UK_FAC(), FR_FAC(), DE_FAC(), NL_FAC;

	// Each factory once instantiated will be cached.
	private static Map<PROVISIONED_LN, IntegerToWordMapper> mappingsCache = Collections
			.synchronizedMap(new HashMap<PROVISIONED_LN, IntegerToWordMapper>());

	private final static Logger LOGGER = Logger
			.getLogger(IntegerToWordEnumFactory.class.getName());

	/**
	 * 
	 * @param pl
	 * @return new IntegerToWordMapper
	 * @throws NumberToWordFactoryException
	 */
	private IFNumberToWordMapper<BigInteger> newIntegerToWordMapper(
			PROVISIONED_LN pl) throws NumberToWordFactoryException {
		// access private Constructor of IntegerToWordMapper using reflection
		final Constructor<?>[] constructors = IntegerToWordMapper.class
				.getDeclaredConstructors();
		if (constructors.length == 1)
			constructors[0].setAccessible(true);
		else
			throw new NumberToWordFactoryException(
					"unexpected number of constructors");

		EnumLanguageSupport enumLanguageSupport = new EnumLanguageSupport(pl);
		IFNumberToWordMapper<BigInteger> mapper = null;

		try {
			mapper = (IFNumberToWordMapper<BigInteger>) constructors[0]
					.newInstance(new Object[] { enumLanguageSupport });
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
		final boolean isCached = mappingsCache.containsKey(pl);
		if (isCached) {
			LOGGER.info("IntegerToWordMapper for language " + pl.name()
					+ " available");
		}
		return isCached;
	}

	/**
	 * Get a language specific Enum Factory Each instance is specific for a
	 * PROVISIONED_LN.
	 * 
	 * @see EnumLanguageSupport
	 * 
	 *      Instances will be cached in the initialised state for reuse
	 * 
	 */
	@Override
	public IFNumberToWordMapper<BigInteger> getIntegerToWordMapper()
			throws NumberToWordFactoryException {

		EnumLanguageSupport enumLanguageSupport = null;
		IFNumberToWordMapper<BigInteger> integerToWordMapper;
		switch (this) {
		case UK_FAC:
			// check mappingsCache
			if (isCached(PROVISIONED_LN.UK)) {
				return mappingsCache.get(PROVISIONED_LN.UK);
			}
			integerToWordMapper = newIntegerToWordMapper(PROVISIONED_LN.UK);
			mappingsCache.put(PROVISIONED_LN.UK,
					(IntegerToWordMapper) integerToWordMapper);
			break;

		case FR_FAC:
			if (isCached(PROVISIONED_LN.FR)) {
				return mappingsCache.get(PROVISIONED_LN.FR);
			}

			integerToWordMapper = newIntegerToWordMapper(PROVISIONED_LN.FR);
			mappingsCache.put(PROVISIONED_LN.FR,
					(IntegerToWordMapper) integerToWordMapper);
			break;
		case DE_FAC:
			if (isCached(PROVISIONED_LN.DE)) {
				return mappingsCache.get(PROVISIONED_LN.DE);
			}

			integerToWordMapper = newIntegerToWordMapper(PROVISIONED_LN.DE);
			mappingsCache.put(PROVISIONED_LN.DE,
					(IntegerToWordMapper) integerToWordMapper);
			break;

		case NL_FAC:
			if (isCached(PROVISIONED_LN.NL)) {
				return mappingsCache.get(PROVISIONED_LN.NL);
			}

			integerToWordMapper = newIntegerToWordMapper(PROVISIONED_LN.NL);
			mappingsCache.put(PROVISIONED_LN.NL,
					(IntegerToWordMapper) integerToWordMapper);
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
	public static boolean removeNumberToWordEnumFactory(
			final PROVISIONED_LN provLang) throws FactoryMapperRemovalException {

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
