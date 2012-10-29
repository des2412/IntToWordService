/**
 * 
 */
package org.desz.numbertoword.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.desz.numbertoword.INumberToWordMapper;
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

	@Override
	public INumberToWordMapper getNumberToWordMapper() throws Exception {
		switch (this) {
		case UK_MAPPER:
			if (ukRef.get() == null) {
				NumberToWordMapper ukNumberToWordMapper = new NumberToWordMapper(PROVISIONED_LANGUAGE.UK);
				//ukNumberToWordMapper.setProvisionedLanguage(PROVISIONED_LANGUAGE.UK);
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
				NumberToWordMapper frNumberToWordMapper = new NumberToWordMapper(PROVISIONED_LANGUAGE.FR);
				//frNumberToWordMapper.setProvisionedLanguage(PROVISIONED_LANGUAGE.FR);
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
