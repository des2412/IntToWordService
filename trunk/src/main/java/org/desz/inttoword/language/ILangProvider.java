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

	/*
	 * public String getNegativeInput();
	 * 
	 * public String getInvalidInput();
	 */
	public String getHunUnit();

	public String getMillUnit();

	public String getThouUnit();

	public String getAnd();

	public String getWord(String num);

	public boolean containsWord(String valueOf);

	public String getBillUnit();

}
