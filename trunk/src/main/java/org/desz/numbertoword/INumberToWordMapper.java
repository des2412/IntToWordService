package org.desz.numbertoword;

import org.desz.numbertoword.exceptions.IntegerToWordException;

public interface INumberToWordMapper {

	String getWord(Integer num) throws IntegerToWordException;

	// String validateAndFormat(Integer num) throws Exception;

}
