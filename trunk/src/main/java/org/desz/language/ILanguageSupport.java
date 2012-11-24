/**
 * Interface for application Errors, number order units
 * and integer to Word Map
 */
package org.desz.language;

import com.google.common.collect.ImmutableMap;

/**
 * @author des
 * 
 */
public interface ILanguageSupport {

	/* ImmutableMap returned so not violating Demeter */
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
