package org.desz.inttoword.output;

public interface IWordDecorator {

	WordResult pluraliseOneRule(WordResult wordResult, int val);

	WordResult replaceSpaceWithEmptyRule();

	WordResult pluraliseUnitRule();

	WordResult combineThouHundRule();

}
