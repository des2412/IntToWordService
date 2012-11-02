/**
 * 
 */
package org.desz.numbertoword.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.numbertoword.IFNumberToWordMapper;
import org.desz.numbertoword.IntegerToWordMapper;
import org.desz.numbertoword.LanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.FR_WORDS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.enums.EnumHolder.UK_WORDS;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;

/**
 * @author des
 * 
 *         Singleton enum NumberToWordMapper Factory for target languages
 *         Flyweight as initialised mappers are stored in a static List
 * 
 */
public enum IntegerToWordEnumFactory implements INumberToWordFactory {

	UK_MAPPER(), FR_MAPPER();

	private static Map<PROVISIONED_LANGUAGE, IntegerToWordEnumFactory> CONFIGURED_FACTORIES = Collections
			.synchronizedMap(new HashMap<PROVISIONED_LANGUAGE, IntegerToWordEnumFactory>());

	private final static Logger LOGGER = Logger
			.getLogger(IntegerToWordEnumFactory.class.getName());

	private IFNumberToWordMapper integerToWordMapper;

	/**
	 * 
	 * @param provLang
	 * @return
	 */
	public boolean lookUpPreInitialised(PROVISIONED_LANGUAGE provLang) {

		return CONFIGURED_FACTORIES.containsKey(provLang);
	}

	/**
	 * @param PROVISIONED_LANGUAGE
	 */
	@Override
	public IFNumberToWordMapper getIntegerToWordMapper(PROVISIONED_LANGUAGE provLang)
			throws NumberToWordFactoryException {

		// check if 'this' has already been invoked
		if (lookUpPreInitialised(provLang)) {
			LOGGER.info("Initialised instance of IntegerToWordMapper for language "
					+ provLang.name() + " available");
			return CONFIGURED_FACTORIES.get(provLang).integerToWordMapper;
		}

		// access private Constructor of IntegerToWordMapper
		final Constructor<?>[] constructorRef = IntegerToWordMapper.class
				.getDeclaredConstructors();
		constructorRef[0].setAccessible(true);
		final Object[] args = new Object[1];

		final LanguageSupport languageSupport = new LanguageSupport(provLang);
		args[0] = languageSupport;

		switch (this) {
		case UK_MAPPER:

			// invoke private constructor
			try {
				this.integerToWordMapper = (IntegerToWordMapper) constructorRef[0].newInstance(args);
			} catch (InstantiationException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (IllegalAccessException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (IllegalArgumentException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (InvocationTargetException e1) {
				throw new NumberToWordFactoryException(e1);
			}

			break;

		case FR_MAPPER:
			try {
				this.integerToWordMapper = (IntegerToWordMapper) constructorRef[0].newInstance(args);
			} catch (InstantiationException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (IllegalAccessException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (IllegalArgumentException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (InvocationTargetException e1) {
				throw new NumberToWordFactoryException(e1);
			}

			break;

		default:
			throw new NumberToWordFactoryException(
					languageSupport.getUnkownErr());

		}
		((IntegerToWordMapper) this.integerToWordMapper).setMapping(initialiseMapping());
		CONFIGURED_FACTORIES.put(provLang, this);
		LOGGER.info("Added "
				+ this.name()
				+ " to CONFIGURED_FACTORIES Map. Number of configured NumberToWordEnumFactories :"
				+ CONFIGURED_FACTORIES.size());
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
		case UK_MAPPER:
			for (UK_WORDS intToWord : UK_WORDS.values()) {
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}

			break;
		case FR_MAPPER:
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
			final PROVISIONED_LANGUAGE provLang) throws FactoryMapperRemovalException {

		if (CONFIGURED_FACTORIES.containsKey(provLang)) {

			try {
				CONFIGURED_FACTORIES.remove(provLang);
				LOGGER.info("Removal of " + provLang.name()
						+ " NumberToWordEnumFactory result:"
						+ !CONFIGURED_FACTORIES.containsKey(provLang));

			} catch (Exception e) {
				throw new FactoryMapperRemovalException(e);
			}

		} else {
			LOGGER.info("References absent for " + provLang.name());
		}

		return !CONFIGURED_FACTORIES.containsKey(provLang);
	}

}
