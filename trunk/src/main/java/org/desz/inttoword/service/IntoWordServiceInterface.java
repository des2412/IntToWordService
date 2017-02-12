/**
 * 
 */
package org.desz.inttoword.service;

import org.desz.inttoword.exceptions.IntToWordServiceException;
import org.desz.inttoword.language.LanguageRepository.ProvLang;

/**
 * @author des
 * 
 */
public interface IntoWordServiceInterface<T extends Number> {
	/**
	 * 
	 * @param provLn
	 *            the ProvLang.
	 * @param the
	 *            integer.
	 * @return the word for num in provLn.
	 * @throws IntToWordServiceException
	 */
	String getWordInLang(ProvLang provLn, String num) throws IntToWordServiceException;

}
