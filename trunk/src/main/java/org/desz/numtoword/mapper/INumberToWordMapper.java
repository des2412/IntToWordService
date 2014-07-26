package org.desz.numtoword.mapper;

import org.desz.numtoword.exceptions.IntToWordException;

public interface INumberToWordMapper<T extends Number> {

	String getWord(T num) throws IntToWordException;

	String getErrorMessage();
	
	//String smartCalc(BigInteger i);

}
