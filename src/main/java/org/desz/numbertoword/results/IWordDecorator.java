package org.desz.numbertoword.results;

public interface IWordDecorator {

	/**
	 * pluralise EIN.
	 * 
	 * @param val
	 * @return the Word.
	 */
	Word pluraliseHundredthRule(int val);

	Word pluraliseUnitRule();

	Word combineThouHundRule();

}
