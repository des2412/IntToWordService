package org.desz.inttoword.converters;

import static java.util.Arrays.asList;
import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.results.Word;
import org.junit.Test;

public class TestWordBuilderImpl {
	LongToWordBuilder wordBuilderImpl = new LongToWordBuilder();
	final IntWordMapping intWordMapping = getInstance().getMapForProvLang(ProvLang.UK);

	@Test
	public void test() {
		assertNotNull(wordBuilderImpl.buildWord(asList(new String[] { "123", "456" }), Word.builder(), intWordMapping));
	}

	@Test
	public void test2() {
		assertNotNull(wordBuilderImpl.buildWord(asList(new String[] { "123", "456", "999" }), Word.builder(),
				intWordMapping));
	}

	@Test
	public void test3() {
		Word act = wordBuilderImpl.buildWord(asList(new String[] { "9", "223", "372", "36", "854", "775", "807" }),
				Word.builder(), intWordMapping);
		assertNotNull(act);
	}

}
