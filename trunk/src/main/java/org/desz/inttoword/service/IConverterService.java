/**
 * 
 */
package org.desz.inttoword.service;

import org.desz.inttoword.exceptions.IntToWordServiceException;
import org.desz.inttoword.language.LangContent.PROV_LANG;

/**
 * @author des
 * 
 */
public interface IConverterService<T extends Number> {
	/**
	 * 
	 * @param provLn
	 *            the PROV_LANG.
	 * @param the
	 *            integer.
	 * @return the word for num in provLn.
	 * @throws IntToWordServiceException
	 */
	String getWordInLang(PROV_LANG provLn, String num) throws IntToWordServiceException;

}
