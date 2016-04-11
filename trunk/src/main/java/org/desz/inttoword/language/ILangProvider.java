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

	public String getHunUnit();

	public String getMillUnit();

	public String getThouUnit();

	public String getAnd();

	public String getWord(String num);

	public boolean containsWord(String valueOf);

	public String getBillUnit();

	public List<String> unitsList();

	public String getErrorForProvLang(ProvLang provLang, String key);

}
