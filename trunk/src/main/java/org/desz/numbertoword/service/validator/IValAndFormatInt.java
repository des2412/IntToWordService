/**
 * 
 */
package org.desz.numbertoword.service.validator;

import java.math.BigInteger;

import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.exceptions.IntegerToWordNegativeException;

/**
 * @author des
 * 
 */
public interface IValAndFormatInt {

	public String validateAndFormat(BigInteger num)
			throws IntegerToWordException, IntegerToWordNegativeException;
}
