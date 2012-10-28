/**
 * 
 */
package org.desz.numbertoword.factory;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.desz.numbertoword.INumberToWordMapper;
import org.desz.numbertoword.UkNumberToWordMapper;

/**
 * @author des
 * 
 *         Singleton-Factory for target languages
 * 
 */
public enum NumberToWordFactory implements INumberToWordFactory {

	UK_SINGLETON();

	private final static Logger LOGGER = Logger
			.getLogger(NumberToWordFactory.class.getName());

	final AtomicReference<UkNumberToWordMapper> ref = new AtomicReference<UkNumberToWordMapper>();

	UkNumberToWordMapper ukNumberToWordMapper = null;

	@Override
	public INumberToWordMapper getNumberToWordMapper() throws Exception {
		switch (this) {
		case UK_SINGLETON:
			boolean res = ref.compareAndSet(ukNumberToWordMapper,
					new UkNumberToWordMapper());
			LOGGER.info("Result of AtomicReference compareAndSet:" + res);
			return ref.get();
			// .. other languages

		default:
			throw new Exception("");
		}
	}

	private NumberToWordFactory() {

	}

}
