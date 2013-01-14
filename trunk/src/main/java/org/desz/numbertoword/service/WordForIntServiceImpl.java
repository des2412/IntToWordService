/**
 * 
 */
package org.desz.numbertoword.service;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.exceptions.IntegerToWordNegativeException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.exceptions.WordForNumberServiceException;
import org.desz.numbertoword.factory.IntegerToWordEnumFactory;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;
import org.desz.numbertoword.mapper.IntegerToWordMapper;
import org.springframework.stereotype.Component;

/**
 * 
 * @author des
 * 
 */
@Component
public final class WordForIntServiceImpl implements
		IWordForNumberService<BigInteger> {

	protected final static Logger LOGGER = Logger
			.getLogger(WordForIntServiceImpl.class.getName());

	private String errMsg;

	@Override
	public String intToWordService(PROV_LANG provLang, String num)
			throws WordForNumberServiceException {

		IFNumberToWordMapper<BigInteger> intToWordMapper = null;

		switch (provLang) {

		case UK:
			try {
				intToWordMapper = IntegerToWordEnumFactory.UK_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe(e.getMessage());
			}
			break;

		case FR:
			try {
				intToWordMapper = IntegerToWordEnumFactory.FR_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe(e.getMessage());
			}
			break;
		case DE:
			try {
				intToWordMapper = IntegerToWordEnumFactory.DE_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe(e.getMessage());
			}
			break;

		case NL:
			try {
				intToWordMapper = IntegerToWordEnumFactory.NL_FAC
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
		} catch (IntegerToWordException e) {
			LOGGER.severe(e.getMessage());
			this.errMsg = intToWordMapper.getErrorMessage();
		} catch (IntegerToWordNegativeException e) {
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
