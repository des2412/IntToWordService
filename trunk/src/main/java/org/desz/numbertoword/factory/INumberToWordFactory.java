package org.desz.numbertoword.factory;

import org.desz.numbertoword.IFNumberToWordMapper;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;

public interface INumberToWordFactory {

	/**
	 * 
	 * @param provLang
	 * @return
	 * @throws NumberToWordFactoryException
	 */
	IFNumberToWordMapper getIntegerToWordMapper()
			throws NumberToWordFactoryException;

}
