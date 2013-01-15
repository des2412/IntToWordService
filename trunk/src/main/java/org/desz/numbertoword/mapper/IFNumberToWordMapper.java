package org.desz.numbertoword.mapper;

import org.desz.numbertoword.exceptions.IntRangeUpperExc;
import org.desz.numbertoword.exceptions.IntRangeLowerExc;

public interface IFNumberToWordMapper<T extends Number> {

	String getWord(T num) throws IntRangeUpperExc,
			IntRangeLowerExc;

	String getErrorMessage();

}
