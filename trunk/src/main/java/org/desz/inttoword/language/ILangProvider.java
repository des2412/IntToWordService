/**
 * Interface for application Errors, number order units
 * and integer to Word Map
 */
package org.desz.inttoword.language;

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
	IntWordMapping getMapForProvLang(final ProvLang provLang);

}
