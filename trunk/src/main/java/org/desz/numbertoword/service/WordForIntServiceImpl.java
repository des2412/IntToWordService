/**
 * 
 */
package org.desz.numbertoword.service;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
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
		WordForNumberService<BigInteger> {

	protected final static Logger LOGGER = Logger
			.getLogger(WordForIntServiceImpl.class.getName());

	@Override
	public IFNumberToWordMapper<BigInteger> getIntegerToWordMapper(
			PROV_LANG provLang) {

		IFNumberToWordMapper<BigInteger> intToWordMapper = null;

		switch (provLang) {

		case UK:
			try {
				intToWordMapper = (IntegerToWordMapper) IntegerToWordEnumFactory.UK_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe("Could not create UK Factory");
			}
			break;

		case FR:
			try {
				intToWordMapper = (IntegerToWordMapper) IntegerToWordEnumFactory.FR_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe("Could not create UK Factory");
			}
			break;
		case DE:
			try {
				intToWordMapper = (IntegerToWordMapper) IntegerToWordEnumFactory.DE_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe("Could not create UK Factory");
			}
			break;

		case NL:
			try {
				intToWordMapper = (IntegerToWordMapper) IntegerToWordEnumFactory.NL_FAC
						.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe("Could not create UK Factory");
			}
			break;
		default:
			LOGGER.info(UK_ERRORS.UNKNOWN.getError());

		}
		LOGGER.info("Returning IntegerToWordMapper");
		return intToWordMapper;
	}

}
