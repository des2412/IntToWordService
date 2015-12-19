/**
 * 
 */
package org.desz.inttoword.service;

import org.desz.inttoword.content.LangContent.PROV_LANG;
import org.desz.inttoword.exceptions.IntToWordServiceException;

/**
 * @author des
 * 
 */
public interface IFIntToWordService<T extends Number> {
	/**
	 * 
	 * @param provLn
	 * @param num
	 * @return word(s) in provLn
	 */
	String getWordInLang(PROV_LANG provLn, String num)
			throws IntToWordServiceException;

}
