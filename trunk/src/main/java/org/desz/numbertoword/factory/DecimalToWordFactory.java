package org.desz.numbertoword.factory;

import java.math.BigDecimal;

import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.INumberToWordMapper;

public enum DecimalToWordFactory implements INumberToWordFactory<BigDecimal> {
	;

	@Override
	public INumberToWordMapper<BigDecimal> getIntegerToWordMapper()
			throws NumberToWordFactoryException {
		// TODO Auto-generated method stub
		return null;
	}

}
