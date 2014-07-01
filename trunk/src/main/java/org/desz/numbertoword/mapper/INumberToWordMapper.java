package org.desz.numbertoword.mapper;

import org.desz.numbertoword.exceptions.IntToWordException;

public interface INumberToWordMapper<T extends Number> {

	String getWord(T num) throws IntToWordException;

	String getErrorMessage();
	
	//String smartCalc(BigInteger i);

}
