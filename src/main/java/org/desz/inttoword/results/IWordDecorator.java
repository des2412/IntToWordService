package org.desz.inttoword.results;

public interface IWordDecorator {

	/**
	 * pluralise EIN.
	 * 
	 * @param val
	 * @return the Word.
	 */
	Word pluraliseRule(int val);

	Word pluraliseUnitRule();

	Word combineThouHundRule();

}
