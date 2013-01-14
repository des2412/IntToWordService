/**
 * 
 */
package org.desz.numbertoword.service;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.WordForNumberServiceException;

/**
 * @author des
 * 
 */
public interface IWordForNumberService<T extends Number> {
	/**
	 * 
	 * @param provLn
	 * @param num
	 * @return
	 */
	String intToWordService(PROV_LANG provLn, String num)
			throws WordForNumberServiceException;

	String getErrorMessage();

}
