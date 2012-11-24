package org.desz.numbertoword.mapper;

import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.exceptions.IntegerToWordNegativeException;

public interface IFNumberToWordMapper<T extends Number> {

	String getWord(T num) throws IntegerToWordException,
			IntegerToWordNegativeException;

}
