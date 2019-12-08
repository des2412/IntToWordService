/**
 * Interface for application Errors, number order units
 * and integer to Word Map
 */
package org.desz.numbertoword.language;

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
	NumberWordMapping getMapForProvLang(final ProvLang provLang);

}
