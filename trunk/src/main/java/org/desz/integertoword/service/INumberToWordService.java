/**
 * 
 */
package org.desz.integertoword.service;

import org.desz.integertoword.content.ContentContainer.PROV_LANG;
import org.desz.integertoword.exceptions.IntToWordServiceException;

/**
 * @author des
 * 
 */
public interface INumberToWordService<T extends Number> {
	/**
	 * 
	 * @param provLn
	 * @param num
	 * @return word(s) in provLn
	 */
	String getWordInLang(PROV_LANG provLn, String num)
			throws IntToWordServiceException;

}
