package org.desz.inttoword.converters;

import static java.util.Arrays.asList;
import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.results.Word;
import org.junit.Test;

public class TestWordBuilderImpl {
	static final ILongToWordBuilder<Word> wordBuilder = (j, k, l) -> new LongToWordBuilder().buildWord(j, k, l);
	static final IntWordMapping intWordMapping = getInstance().getMapForProvLang(ProvLang.DE);

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
