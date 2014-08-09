/**
 * 
 */
package org.desz.integertoword.service.validator;

import java.math.BigInteger;

import org.desz.integertoword.exceptions.IntRangeLowerExc;
import org.desz.integertoword.exceptions.IntRangeUpperExc;

/**
 * @author des
 * 
 */
public interface IFormatter {

	/**
	 * Validate and Format BigInteger
	 * @param num
	 * @return
	 * @throws IntRangeUpperExc
	 * @throws IntRangeLowerExc
	 */
	public String validateAndFormat(BigInteger num)
			throws IntRangeUpperExc, IntRangeLowerExc;
}
