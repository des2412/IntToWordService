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
import org.desz.numbertoword.LanguageAndFormatHelper;
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
public enum NumberToWordEnumFactory implements INumberToWordFactory {

	UK_MAPPER(), FR_MAPPER();

	private static Map<PROVISIONED_LANGUAGE, NumberToWordEnumFactory> FACTORIES = Collections
			.synchronizedMap(new HashMap<PROVISIONED_LANGUAGE, NumberToWordEnumFactory>());

	private final static Logger LOGGER = Logger
			.getLogger(NumberToWordEnumFactory.class.getName());

	private IFNumberToWordMapper integerToWordMapper;

	/**
	 * 
	 * @param pl
	 * @return
	 */
	public boolean lookUpPreInitialised(PROVISIONED_LANGUAGE pl) {

		return FACTORIES.containsKey(pl);
	}

	/**
	 * @param PROVISIONED_LANGUAGE
	 */
	@Override
	public IFNumberToWordMapper getIntegerToWordMapper(PROVISIONED_LANGUAGE pl)
			throws NumberToWordFactoryException {

		// check if 'this' has already been invoked
		if (lookUpPreInitialised(pl)) {
			LOGGER.info("Initialised instance of IntegerToWordMapper for language "
					+ pl.name() + " available");
			return FACTORIES.get(pl).integerToWordMapper;
		}

		// access private Constructor of IntegerToWordMapper
		Constructor<?>[] c = IntegerToWordMapper.class
				.getDeclaredConstructors();
		c[0].setAccessible(true);
		Object[] args = new Object[1];

		IntegerToWordMapper mapper = null;

		LanguageAndFormatHelper languageAndFormatHelper = new LanguageAndFormatHelper();
		;
		LanguageSupport languageSupport = new LanguageSupport(pl);
		languageAndFormatHelper.setLanguageSupport(languageSupport);
		args[0] = languageAndFormatHelper;

		switch (this) {
		case UK_MAPPER:

			// invoke private constructor
			try {
				mapper = (IntegerToWordMapper) c[0].newInstance(args);
			} catch (InstantiationException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (IllegalAccessException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (IllegalArgumentException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (InvocationTargetException e1) {
				throw new NumberToWordFactoryException(e1);
			}
			mapper.setMapping(initialiseMapping());
			this.integerToWordMapper = mapper;
			FACTORIES.put(pl, this);

			break;

		case FR_MAPPER:
			try {
				mapper = (IntegerToWordMapper) c[0].newInstance(args);
			} catch (InstantiationException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (IllegalAccessException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (IllegalArgumentException e1) {
				throw new NumberToWordFactoryException(e1);
			} catch (InvocationTargetException e1) {
				throw new NumberToWordFactoryException(e1);
			}
			mapper.setMapping(initialiseMapping());
			this.integerToWordMapper = mapper;
			FACTORIES.put(pl, this);
			break;

		default:
			throw new NumberToWordFactoryException(
					languageSupport.getUnkownErr());

		}
		LOGGER.info("Added "
				+ this.name()
				+ " to Factories Map. Number of configured NumberToWordEnumFactories :"
				+ FACTORIES.size());
		return this.integerToWordMapper;
	}

	/**
	 * Constructor
	 */
	private NumberToWordEnumFactory() {
	}

	/**
	 * Provisions Map of Integer to language specific word
	 * 
	 * @return
	 */
	private Map<String, String> initialiseMapping() {
		Map<String, String> numToWordMap = new HashMap<String, String>();
		switch (this) {
		case UK_MAPPER:
			for (UK_WORDS intToWord : UK_WORDS.values()) {
				numToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}

			break;
		case FR_MAPPER:
			for (FR_WORDS intToWord : FR_WORDS.values()) {
				numToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}
			break;
		default:
			break;

		}
		return numToWordMap;
	}

	/**
	 * 
	 * @param pl
	 * @return
	 * @throws FactoryMapperRemovalException
	 */
	public static boolean removeNumberToWordEnumFactory(
			final PROVISIONED_LANGUAGE pl) throws FactoryMapperRemovalException {

		if (FACTORIES.containsKey(pl)) {

			try {
				FACTORIES.remove(pl);
				LOGGER.info("Removal of " + pl.name()
						+ " NumberToWordEnumFactory result:"
						+ !FACTORIES.containsKey(pl));

			} catch (Exception e) {
				throw new FactoryMapperRemovalException(e.getMessage());
			}

		} else {
			LOGGER.info("References absent for " + pl.name());
		}

		return !FACTORIES.containsKey(pl);
	}

}
