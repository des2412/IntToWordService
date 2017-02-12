/**
 * Interface for application Errors, number order units
 * and integer to Word Map
 */
package org.desz.inttoword.language;

import java.util.List;
import org.desz.inttoword.language.LanguageRepository.ProvLang;

/**
 * @author des
 * 
 */
public interface ILangProvider {

	public String getHundred();

	public String getMillion();

	public String getThousand();

	public String getAnd();

	public String getWord(String num);

	public String getBillion();

	public List<String> unitsList();

	/**
	 * 
	 * @param provLang
	 * @return ILangProvider for provLang.
	 */
	public ILangProvider factoryForProvLang(final ProvLang provLang);

}
