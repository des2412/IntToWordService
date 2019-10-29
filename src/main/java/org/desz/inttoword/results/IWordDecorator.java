package org.desz.inttoword.results;

public interface IWordDecorator {

	/**
	 * Add s to EIN.
	 * 
	 * @param val
	 * @return the WordResult.
	 */
	WordResult pluraliseOneRule(int val);

	WordResult spaceWithEmptyRule();

	WordResult pluraliseUnitRule();

	WordResult combineThouHundRule();

	WordResult rearrangeHundredthRule();

}
