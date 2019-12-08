package org.desz.numbertoword.converters;

import static java.util.Arrays.asList;
import static org.desz.numbertoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertNotNull;

import org.desz.numbertoword.converters.FuncWordMaker;
import org.desz.numbertoword.converters.WordMaker;
import org.desz.numbertoword.language.NumberWordMapping;
import org.desz.numbertoword.language.ProvLang;
import org.desz.numbertoword.results.Word;
import org.junit.Test;

public class TestWordBuilderImpl {
	static final FuncWordMaker<Word> wordBuilder = (j, k, l) -> new WordMaker().buildWord(j, k, l);
	static final NumberWordMapping intWordMapping = getInstance().getMapForProvLang(ProvLang.DE);

	@Test
	public void test_thou() {
		assertNotNull(wordBuilder.buildWord(asList(new String[] { "123", "456" }), Word.builder(), intWordMapping));
	}

	@Test
	public void test_mill() {
		assertNotNull(
				wordBuilder.buildWord(asList(new String[] { "123", "456", "999" }), Word.builder(), intWordMapping));
	}

	@Test
	public void test_quint() {
		assertNotNull(wordBuilder.buildWord(asList(new String[] { "9", "223", "372", "36", "854", "775", "807" }),
				Word.builder(), intWordMapping));
	}

}
