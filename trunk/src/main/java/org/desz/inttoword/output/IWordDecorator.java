package org.desz.inttoword.output;

public interface IWordDecorator {

	/**
	 * Add s to EIN.
	 * 
	 * @param wordResult
	 * @param val
	 * @return
	 */
	WordResult pluraliseOneRule(int val);

	WordResult replaceSpaceWithEmptyRule();

	WordResult pluraliseUnitRule();

	WordResult combineThouHundRule();

	WordResult restructureHundrethRule();

}
