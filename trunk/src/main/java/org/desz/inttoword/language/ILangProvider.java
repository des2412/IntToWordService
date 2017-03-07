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

	/**
	 * 
	 * @param provLang
	 * @return ILangProvider for provLang.
	 */
	NumericalLangMapping factoryForProvLang(final ProvLang provLang);

}
