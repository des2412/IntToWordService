package org.desz.numbertoword.factory;

import org.desz.numbertoword.IFNumberToWordMapper;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;

public interface INumberToWordFactory {

	IFNumberToWordMapper getIntegerToWordMapper(PROVISIONED_LANGUAGE pl)
			throws NumberToWordFactoryException;

}
