package org.desz.inttoword.output;

public interface WordDecorator {

	WordResult pluraliseWordRule(WordResult wordResult, String word);

	WordResult removeWordRule(String word);

	WordResult pluraliseUnitRule();

}
