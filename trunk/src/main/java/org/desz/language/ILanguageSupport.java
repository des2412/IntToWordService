/**
 * Interface for application Errors, number order units
 * and integer to Word Map
 */
package org.desz.language;


/**
 * @author des
 * 
 */
public interface ILanguageSupport {

	public String getNegativeInput();

	public String getInvalidInput();

	public String getHunUnit();

	public String getMillUnit();

	public String getThouUnit();

	public String getAnd();

	public String getNullInput();

	public String getNumberFormatErr();

	public String getUnkownErr();
	
	public String getWord(String num);

}
