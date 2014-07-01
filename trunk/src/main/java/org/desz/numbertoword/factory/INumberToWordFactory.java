package org.desz.numbertoword.factory;

import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.INumberToWordMapper;

public interface INumberToWordFactory<T extends Number> {

	/**
	 * 
	 * @return
	 * @throws NumberToWordFactoryException
	 */
	INumberToWordMapper<T> getIntegerToWordMapper()
			throws NumberToWordFactoryException;

}
