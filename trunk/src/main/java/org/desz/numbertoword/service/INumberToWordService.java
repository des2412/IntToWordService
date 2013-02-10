/**
 * 
 */
package org.desz.numbertoword.service;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordServiceException;

/**
 * @author des
 * 
 */
public interface INumberToWordService<T extends Number> {
	/**
	 * 
	 * @param provLn
	 * @param num
	 * @return
	 */
	String intToWordService(PROV_LANG provLn, String num)
			throws IntToWordServiceException;

	String getErrorMessage();

	void saveFrequency(String num) throws IntToWordServiceException;
}
