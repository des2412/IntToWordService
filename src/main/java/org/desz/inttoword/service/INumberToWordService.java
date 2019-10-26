/**
 * 
 */
package org.desz.inttoword.service;

import org.desz.inttoword.exceptions.IntToWordServiceException;
import org.desz.inttoword.language.ProvLang;

/**
 * @author des
 * 
 */
public interface INumberToWordService {
	/**
	 * 
	 * @param provLn the ProvLang.
	 * @param num    the numerical represented as String.
	 * @return the word representation of num according to language directive.
	 * @throws IntToWordServiceException
	 */
	String getWordInLang(ProvLang provLn, String num) throws IntToWordServiceException;

}
