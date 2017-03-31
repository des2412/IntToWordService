/**
 * Interface for application Errors, number order units
 * and integer to Word Map
 */
package org.desz.inttoword.language;

import org.desz.inttoword.language.ProvLangFactoryParts.ProvLang;

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
	NumericalLangMapping numericMap(final ProvLang provLang);

}
