package org.desz.inttoword.output;

public interface IWordDecorator {

	WordResult pluraliseValueOfOneRule(WordResult wordResult, int val);

	WordResult replaceSpaceWithEmptyRule();

	WordResult pluraliseUnitRule();

	WordResult combineThousandsAndHundreds();

}
