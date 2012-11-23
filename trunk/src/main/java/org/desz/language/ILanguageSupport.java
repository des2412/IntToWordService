/**
 * Interface for application Errors
 */
package org.desz.language;

import java.util.Map;

/**
 * @author des
 *
 */
public interface ILanguageSupport {
	
	public Map<String, String> getIntToWordMap();
	
	public String getNegativeInput();
	

	public String getInvalidInput();

	public String getHunUnit();

	public String getMillUnit();
	
	public String getThouUnit();

	public String getAnd();
	
	public String getNullInput();

	public String getNumberFormatErr();

	public String getUnkownErr();

}
