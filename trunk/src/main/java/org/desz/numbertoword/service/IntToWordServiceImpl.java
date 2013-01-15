/**
 * 
 */
package org.desz.numbertoword.service;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.exceptions.IntToWordServiceException;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.desz.numbertoword.mapper.INumberToWordMapper;
import org.springframework.stereotype.Component;

/**
 * 
 * @author des
 * 
 */
@Component
public final class IntToWordServiceImpl implements
		INumberToWordService<BigInteger> {

	protected final static Logger LOGGER = Logger
			.getLogger(IntToWordServiceImpl.class.getName());

	private String errMsg;

	@Override
	public String intToWordService(PROV_LANG provLang, String num)
			throws IntToWordServiceException {

		INumberToWordMapper<BigInteger> intToWordMapper = null;

		switch (provLang) {

		case UK:
			try {
				intToWordMapper = IntToWordEnumFactory.UK_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe(e.getMessage());
			}
			break;

		case FR:
			try {
				intToWordMapper = IntToWordEnumFactory.FR_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe(e.getMessage());
			}
			break;
		case DE:
			try {
				intToWordMapper = IntToWordEnumFactory.DE_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe(e.getMessage());
			}
			break;

		case NL:
			try {
				intToWordMapper = IntToWordEnumFactory.NL_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe(e.getMessage());
			}
			break;
		default:
			LOGGER.info(UK_ERRORS.UNKNOWN.getError());

		}

		try {
			return intToWordMapper.getWord(new BigInteger(num));
		} catch (IntToWordExc e) {
			LOGGER.severe(e.getMessage());
			this.errMsg = intToWordMapper.getErrorMessage();
		}
		return null;
	}

	@Override
	public String getErrorMessage() {
		return this.errMsg;
	}

}
