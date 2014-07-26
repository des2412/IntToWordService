/**
 * 
 */
package org.desz.numtoword.service;

import org.desz.numtoword.cms.ContentContainer.PROV_LANG;
import org.desz.numtoword.exceptions.IntToWordServiceException;

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
	String getWordInlang(PROV_LANG provLn, String num)
			throws IntToWordServiceException;

	String getErrorMessage();

	//void saveFrequency(String num) throws IntToWordServiceException;
}
