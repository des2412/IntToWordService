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
 *         Enum Factorys target languages.
 * @see IntegerToWordMapper
 * 
 * 
 */
public enum IntegerToWordEnumFactory implements
		INumberToWordFactory<BigInteger> {

	// Language specific factories
	UK_FAC(), FR_FAC(), DE_FAC(), NL_FAC;

	// Each factory once instantiated will be cached.
	private static Map<PROVISIONED_LN, IntegerToWordEnumFactory> factoryCache = Collections
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
	private IFNumberToWordMapper<BigInteger> newIntegerToWordMapper(
			final Object[] args) throws NumberToWordFactoryException {
		// access private Constructor of IntegerToWordMapper using reflection
		final Constructor<?>[] constructors = IntegerToWordMapper.class
				.getDeclaredConstructors();
		if (constructors.length == 1)
			constructors[0].setAccessible(true);
		else
			throw new NumberToWordFactoryException(
					"unexpected number of constructors");
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
		final boolean isCached = factoryCache.containsKey(pl);
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

		final Object[] args = new Object[1];

		EnumLanguageSupport enumLanguageSupport = null;

		switch (this) {
		case UK_FAC:
			// check factoryCache
			if (isCached(PROVISIONED_LN.UK)) {
				return factoryCache.get(PROVISIONED_LN.UK).integerToWordMapper;
			}

			enumLanguageSupport = new EnumLanguageSupport(PROVISIONED_LN.UK);
			args[0] = enumLanguageSupport;
			// invoke private constructor
			this.integerToWordMapper = newIntegerToWordMapper(args);
			// factoryCache 'this' in Map
			factoryCache.put(PROVISIONED_LN.UK, this);
			break;

		case FR_FAC:
			if (isCached(PROVISIONED_LN.FR)) {
				return factoryCache.get(PROVISIONED_LN.FR).integerToWordMapper;
			}

			enumLanguageSupport = new EnumLanguageSupport(PROVISIONED_LN.FR);
			args[0] = enumLanguageSupport;
			this.integerToWordMapper = newIntegerToWordMapper(args);
			factoryCache.put(PROVISIONED_LN.FR, this);
			break;
		case DE_FAC:
			if (isCached(PROVISIONED_LN.DE)) {
				return factoryCache.get(PROVISIONED_LN.DE).integerToWordMapper;
			}

			enumLanguageSupport = new EnumLanguageSupport(PROVISIONED_LN.DE);
			args[0] = enumLanguageSupport;
			this.integerToWordMapper = newIntegerToWordMapper(args);
			factoryCache.put(PROVISIONED_LN.DE, this);
			break;

		case NL_FAC:
			if (isCached(PROVISIONED_LN.NL)) {
				return factoryCache.get(PROVISIONED_LN.NL).integerToWordMapper;
			}

			enumLanguageSupport = new EnumLanguageSupport(PROVISIONED_LN.NL);
			args[0] = enumLanguageSupport;
			this.integerToWordMapper = newIntegerToWordMapper(args);
			factoryCache.put(PROVISIONED_LN.NL, this);
			break;
		default:
			LOGGER.info("Unknown problem creating Factory");
			throw new NumberToWordFactoryException(UK_ERRORS.UNKNOWN.getError());

		}

		LOGGER.info("Added "
				+ this.name()
				+ " to factoryCache Map. Number of configured NumberToWordEnumFactories :"
				+ factoryCache.size());
		return this.integerToWordMapper;
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

		if (factoryCache.containsKey(provLang)) {

			try {
				factoryCache.remove(provLang);
				LOGGER.info("Removal of " + provLang.name()
						+ " NumberToWordEnumFactory result:"
						+ !factoryCache.containsKey(provLang));

			} catch (Exception e) {
				throw new FactoryMapperRemovalException(e);
			}

		} else {
			LOGGER.info("Reference removed from factoryCache for "
					+ provLang.name());
		}

		return !factoryCache.containsKey(provLang);
	}

}
