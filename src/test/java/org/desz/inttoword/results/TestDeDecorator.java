package org.desz.inttoword.results;

import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.desz.inttoword.language.ProvLangValues.DePair.ONE;
import static org.desz.inttoword.language.ProvLangValues.DePair.TWO;
import static org.desz.inttoword.language.Punct.SPC;
import static org.junit.Assert.assertEquals;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.junit.Test;

public class TestDeDecorator {

	final IntWordMapping deWordMapping = getInstance().getMapForProvLang(ProvLang.DE);

	@Test
	public void testPluraliseOneRule() {

		assertEquals("expected eins", ONE.getWord() + "s",
				new DeDecorator(Word.builder().hund("ein").build()).pluraliseRule(1).getHund());

		String input = normalizeSpace(ONE.getWord() + SPC.val() + deWordMapping.getMilln());
		assertEquals("Should contain million", input,
				new DeDecorator(Word.builder().mill(input).build()).pluraliseUnitRule().getMill());
		// TWO should be pluralised (million -> millionen).
		input = normalizeSpace(TWO.getWord() + SPC.val() + deWordMapping.getMilln());
		assertEquals("Should match with ...en", input + "en",
				new DeDecorator(Word.builder().mill(input).build()).pluraliseUnitRule().getMill());

		input = normalizeSpace(TWO.getWord() + SPC.val() + deWordMapping.getBilln());
		assertEquals("Should match with ...n", input + "n",
				new DeDecorator(Word.builder().bill(input).build()).pluraliseUnitRule().getBill());

	}

	@Test(expected = NullPointerException.class)
	public void testConstructor() {
		new DeDecorator(null);
	}

	@Test
	public void testcombineThouHundRule() {

		assertEquals("neunhundertneunundneunzigtausendneunhundertneunundneunzig", new DeDecorator(
				Word.builder().thou("neunhundertneunundneunzigtausend").hund("neunhundertneunundneunzig").build())
						.combineThouHundRule().getThou());

	}

}
