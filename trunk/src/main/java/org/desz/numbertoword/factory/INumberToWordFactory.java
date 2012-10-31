package org.desz.numbertoword.factory;

import org.desz.numbertoword.INumberToWordMapper;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;

public interface INumberToWordFactory {
	INumberToWordMapper getNumberToWordMapper() throws NumberToWordFactoryException;

}
