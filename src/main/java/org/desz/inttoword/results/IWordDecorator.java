package org.desz.inttoword.results;

public interface IWordDecorator {

	/**
	 * Add s to EIN.
	 * 
	 * @param val
	 * @return the WordResult.
	 */
	WordResult pluraliseOneRule(int val);

	WordResult replaceSpaceWithEmptyRule();

	WordResult pluraliseUnitRule();

	WordResult combineThouHundRule();

	WordResult restructureHundrethRule();

}
