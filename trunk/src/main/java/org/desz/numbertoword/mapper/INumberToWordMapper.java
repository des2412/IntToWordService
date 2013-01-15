package org.desz.numbertoword.mapper;

import org.desz.numbertoword.exceptions.IntToWordExc;

public interface INumberToWordMapper<T extends Number> {

	String getWord(T num) throws IntToWordExc;

	String getErrorMessage();

}
