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
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;

/**
 * @author des
 * 
 *         Singleton-Factory for target languages
 * 
 */
public enum NumberToWordFactory implements INumberToWordFactory {

	UK_MAPPER(), FR_MAPPER();

	private final static Logger LOGGER = Logger
			.getLogger(NumberToWordFactory.class.getName());

	final AtomicReference<INumberToWordMapper> ukRef = new AtomicReference<INumberToWordMapper>();

	final AtomicReference<INumberToWordMapper> frRef = new AtomicReference<INumberToWordMapper>();

	private Map<PROVISIONED_LANGUAGE, AtomicReference<INumberToWordMapper>> mappers = new HashMap<PROVISIONED_LANGUAGE, AtomicReference<INumberToWordMapper>>();

	/**
	 * Factory that uses Refelection to invoke private constructor of
	 * NumberToWordMapper
	 */
	@Override
	public INumberToWordMapper getNumberToWordMapper() throws Exception {

		Constructor<?>[] c = NumberToWordMapper.class.getDeclaredConstructors();
		c[0].setAccessible(true);

		Object[] args = new Object[1];

		switch (this) {
		case UK_MAPPER:
			if (ukRef.get() == null) {

				args[0] = PROVISIONED_LANGUAGE.UK;
				// invoke the private constructor
				NumberToWordMapper ukNumberToWordMapper = (NumberToWordMapper) c[0]
						.newInstance(args);
				ukNumberToWordMapper.setLanguageSupport(new LanguageSupport((PROVISIONED_LANGUAGE) args[0]));

				ukRef.set(ukNumberToWordMapper);
				LOGGER.info("set AtomicReference for UkNumberToWordMapper instance");
				mappers.put(PROVISIONED_LANGUAGE.UK, ukRef);
				LOGGER.info("List of mappers [size]:" + mappers.size());
				return ukRef.get();
			} else {
				return mappers.get(PROVISIONED_LANGUAGE.UK).get();
			}

		case FR_MAPPER:
			if (frRef.get() == null) {

				args[0] = PROVISIONED_LANGUAGE.FR;
				NumberToWordMapper frNumberToWordMapper = (NumberToWordMapper) c[0]
						.newInstance(args);
				frNumberToWordMapper.setLanguageSupport(new LanguageSupport((PROVISIONED_LANGUAGE) args[0]));
				frRef.set(frNumberToWordMapper);
				LOGGER.info("set AtomicReference for FrNumberToWordMapper instance");
				mappers.put(PROVISIONED_LANGUAGE.FR, frRef);
				LOGGER.info("List of mappers [size]:" + mappers.size());
				return frRef.get();
			} else {
				return mappers.get(PROVISIONED_LANGUAGE.FR).get();
			}

		default:
			throw new Exception("Language not supported");
		}
	}

	private NumberToWordFactory() {

	}

}
