/**
 * 
 */
package org.desz.numbertoword.service.validator;

import java.math.BigInteger;

import org.desz.numbertoword.exceptions.IntRangeLowerExc;
import org.desz.numbertoword.exceptions.IntRangeUpperExc;

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
