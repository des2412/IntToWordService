package org.desz.inttoword.results;

public interface IWordDecorator {

	/**
	 * Add s to EIN.
	 * 
	 * @param val
	 * @return the Word.
	 */
	Word pluraliseRule(int val);

	Word spaceWithEmptyRule();

	Word pluraliseUnitRule();

	Word combineThouHundRule();

	Word reArrangeHundredthRule();

}
