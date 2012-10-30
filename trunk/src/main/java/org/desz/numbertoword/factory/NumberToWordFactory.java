/**
 * 
 */
package org.desz.numbertoword.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.desz.numbertoword.INumberToWordMapper;
import org.desz.numbertoword.LanguageSupport;
import org.desz.numbertoword.NumberToWordMapper;
import org.desz.numbertoword.enums.EnumHolder.FR_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;

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

	final AtomicReference<INumberToWordMapper> ukRef = new AtomicReference<INumberToWordMapper>();
	final AtomicReference<INumberToWordMapper> frRef = new AtomicReference<INumberToWordMapper>();

	private static Map<PROVISIONED_LANGUAGE, AtomicReference<INumberToWordMapper>> mappers = new HashMap<PROVISIONED_LANGUAGE, AtomicReference<INumberToWordMapper>>();

	/**
	 * Uses Reflection to invoke private constructor of NumberToWordMapper
	 * 
	 */
	@Override
	public INumberToWordMapper getNumberToWordMapper() throws Exception {
		// access the private Constructor
		Constructor<?>[] c = NumberToWordMapper.class.getDeclaredConstructors();
		c[0].setAccessible(true);
		Object[] args = new Object[1];
		String ln = UK_FORMAT.EMPTY.val();
		switch (this) {
		case UK_MAPPER:
			if (ukRef.get() == null) {

				args[0] = new LanguageSupport(PROVISIONED_LANGUAGE.UK);
				// invoke the private constructor
				NumberToWordMapper ukNumberToWordMapper = (NumberToWordMapper) c[0]
						.newInstance(args);

				ukRef.set(ukNumberToWordMapper);
				LOGGER.info("set AtomicReference for UkNumberToWordMapper instance");
				mappers.put(PROVISIONED_LANGUAGE.UK, ukRef);
				LOGGER.info("List of mappers [size]:" + mappers.size());
				ln = PROVISIONED_LANGUAGE.UK.name();
				return ukRef.get();
			} else {
				return mappers.get(PROVISIONED_LANGUAGE.UK).get();
			}

		case FR_MAPPER:
			if (frRef.get() == null) {

				args[0] = new LanguageSupport(PROVISIONED_LANGUAGE.FR);
				NumberToWordMapper frNumberToWordMapper = (NumberToWordMapper) c[0]
						.newInstance(args);
				frRef.set(frNumberToWordMapper);
				LOGGER.info("set AtomicReference for FrNumberToWordMapper instance");
				mappers.put(PROVISIONED_LANGUAGE.FR, frRef);
				LOGGER.info("List of mappers [size]:" + mappers.size());
				ln = PROVISIONED_LANGUAGE.FR.name();
				return frRef.get();
			} else {
				return mappers.get(PROVISIONED_LANGUAGE.FR).get();
			}

		default:
			Exception e = new Exception();
			if (ln.equals(PROVISIONED_LANGUAGE.UK.name())) {
				e = new Exception(UK_ERRORS.LANGUAGE_NOTSUPPORTED.val());
			} else if (ln.equals(PROVISIONED_LANGUAGE.FR.name())) {
				e = new Exception(FR_ERRORS.LANGUAGE_NOTSUPPORTED.val());
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
	 * @throws Exception
	 * 
	 */
	public static String removeNumberToWordMapper(PROVISIONED_LANGUAGE pl)
			throws Exception {
		String s = null;

		if (mappers.containsKey(pl)) {

			NumberToWordMapper mapper = (NumberToWordMapper) mappers.get(pl)
					.get();
			s = mapper.getLanguageSupport().getProvisionedLanguage().name();
			mappers.remove(pl);
			if (mappers.containsKey(pl)) {
				throw new Exception("Failed to remove");
			}

		}
		return s;
	}

}
