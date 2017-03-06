/**
 * Interface for application Errors, number order units
 * and integer to Word Map
 */
package org.desz.inttoword.language;

import org.desz.inttoword.language.LanguageRepository.ProvLang;

/**
 * @author des
 * 
 */
public interface ILangProvider {

	String getHundred();

	String getMillion();

	String getThousand();

	String getAnd();

	String getWord(String num);

	String getBillion();

	/**
	 * 
	 * @param provLang
	 * @return ILangProvider for provLang.
	 */
	ILangProvider factoryForProvLang(final ProvLang provLang);

}
