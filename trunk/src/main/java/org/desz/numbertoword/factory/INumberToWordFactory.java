package org.desz.numbertoword.factory;

import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;

public interface INumberToWordFactory<T extends Number> {

	/**
	 * 
	 * @param provLang
	 * @return
	 * @throws NumberToWordFactoryException
	 */
	IFNumberToWordMapper getIntegerToWordMapper()
			throws NumberToWordFactoryException;

}
