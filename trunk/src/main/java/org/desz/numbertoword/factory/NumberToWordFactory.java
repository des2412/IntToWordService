/**
 * 
 */
package org.desz.numbertoword.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.numbertoword.INumberToWordMapper;
import org.desz.numbertoword.IntegerToWordMapper;
import org.desz.numbertoword.LanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.FR_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.FR_WORDS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.UK_WORDS;

/**
 * @author des
 * 
 *         Singleton enum NumberToWordMapper Factory for target languages
 *         Flyweight as initialised mappers are stored in a List
 * 
 */
public enum NumberToWordFactory implements INumberToWordFactory {

	UK_MAPPER(), FR_MAPPER();

	private final static Logger LOGGER = Logger
			.getLogger(NumberToWordFactory.class.getName());

	private static Map<PROVISIONED_LANGUAGE, INumberToWordMapper> mappers = Collections
			.synchronizedMap(new EnumMap<PROVISIONED_LANGUAGE, INumberToWordMapper>(
					PROVISIONED_LANGUAGE.class));

	/**
	 * Uses Reflection to invoke private constructor of IntegerToWordMapper
	 * 
	 */
	@Override
	public INumberToWordMapper getNumberToWordMapper() {
		// access the private Constructor
		Constructor<?>[] c = IntegerToWordMapper.class.getDeclaredConstructors();
		c[0].setAccessible(true);
		Object[] args = new Object[1];
		String ln = UK_FORMAT.EMPTY.val();
		switch (this) {
		case UK_MAPPER:
			if (!mappers.containsKey(PROVISIONED_LANGUAGE.UK)) {

				args[0] = new LanguageSupport(PROVISIONED_LANGUAGE.UK);
				// invoke the private constructor
				IntegerToWordMapper ukNumberToWordMapper = null;
				try {
					ukNumberToWordMapper = (IntegerToWordMapper) c[0]
							.newInstance(args);
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ukNumberToWordMapper.setMapping(initialiseMapping());
				mappers.put(PROVISIONED_LANGUAGE.UK, ukNumberToWordMapper);
				LOGGER.info("List of mappers [size]:" + mappers.size());
				ln = PROVISIONED_LANGUAGE.UK.name();
				return ukNumberToWordMapper;
			} else {
				return mappers.get(PROVISIONED_LANGUAGE.UK);
			}

		case FR_MAPPER:
			if (!mappers.containsKey(PROVISIONED_LANGUAGE.FR)) {

				args[0] = new LanguageSupport(PROVISIONED_LANGUAGE.FR);
				IntegerToWordMapper frNumberToWordMapper = null;
				try {
					frNumberToWordMapper = (IntegerToWordMapper) c[0]
							.newInstance(args);
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frNumberToWordMapper.setMapping(initialiseMapping());
				mappers.put(PROVISIONED_LANGUAGE.FR, frNumberToWordMapper);
				LOGGER.info("List of mappers [size]:" + mappers.size());
				ln = PROVISIONED_LANGUAGE.FR.name();
				return frNumberToWordMapper;
			} else {
				return mappers.get(PROVISIONED_LANGUAGE.FR);
			}

		default:
			RuntimeException e = null;
			if (ln.equals(PROVISIONED_LANGUAGE.UK.name())) {
				e = new RuntimeException(UK_ERRORS.LANGUAGE_NOTSUPPORTED.val());
			} else if (ln.equals(PROVISIONED_LANGUAGE.FR.name())) {
				e = new RuntimeException(FR_ERRORS.LANGUAGE_NOTSUPPORTED.val());
			}

			throw e;

		}
	}

	/**
	 * Constructor
	 */
	private NumberToWordFactory() {
	}

	/**
	 * initialise map of number to corresponding word specific for
	 * PROVISIONED_LANGUAGE
	 * 
	 * @param ln
	 * @return
	 */
	protected Map<String, String> initialiseMapping() {
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
	 * @throws Exception
	 * 
	 */
	public static String removeNumberToWordMapper(PROVISIONED_LANGUAGE pl)
			throws Exception {
		String s = null;

		if (mappers.containsKey(pl)) {

			IntegerToWordMapper mapper = (IntegerToWordMapper) mappers.get(pl);
			s = mapper.getLanguageSupport().getProvisionedLanguage().name();
			try {
				mappers.remove(pl);
			} catch (Exception e) {
				throw new Exception(e);
			}
			if (mappers.containsKey(pl)) {
				throw new Exception("Failed to remove");
			}

		}
		return s;
	}

}
