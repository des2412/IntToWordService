package org.desz.inttoword.output;

public interface IWordDecorator {

	/**
	 * Add s to EIN.
	 * 
	 * @param wordResult
	 * @param val
	 * @return
	 */
	WordResult pluraliseOneRule(WordResult wordResult, int val);

	WordResult replaceSpaceWithEmptyRule();

	WordResult pluraliseUnitRule();

	WordResult combineThouHundRule();

}
