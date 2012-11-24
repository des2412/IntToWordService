/**
 * Interface for application Errors
 */
package org.desz.language;

import com.google.common.collect.ImmutableMap;

/**
 * @author des
 *
 */
public interface ILanguageSupport {
	
	public ImmutableMap<String, String> getIntToWordMap();
	
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
