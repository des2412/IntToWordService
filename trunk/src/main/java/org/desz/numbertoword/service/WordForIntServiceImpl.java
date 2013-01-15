/**
 * 
 */
package org.desz.numbertoword.service;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.exceptions.IntRangeUpperExc;
import org.desz.numbertoword.exceptions.IntRangeLowerExc;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.exceptions.WordForNumberServiceException;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;
import org.desz.numbertoword.mapper.IntToWord;
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
		} catch (IntRangeUpperExc e) {
			LOGGER.severe(e.getMessage());
			this.errMsg = intToWordMapper.getErrorMessage();
		} catch (IntRangeLowerExc e) {
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
