package org.desz.numbertoword.results;

import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.desz.numbertoword.factory.ProvLangFactory.getInstance;
import static org.desz.numbertoword.language.ProvLangValues.DePair.ONE;
import static org.desz.numbertoword.language.ProvLangValues.DePair.TWO;
import static org.desz.numbertoword.language.Punct.SPC;
import static org.junit.Assert.assertEquals;

import org.desz.numbertoword.language.NumberWordMapping;
import org.desz.numbertoword.language.ProvLang;
import org.desz.numbertoword.results.DeDecorator;
import org.desz.numbertoword.results.Word;
import org.junit.Test;

public class TestDeDecorator {

	final NumberWordMapping deWordMapping = getInstance().getMapForProvLang(ProvLang.DE);

	@Test
	public void test_pluralise_unit_rule_ein() {

		String input = normalizeSpace(ONE.getWord() + SPC.val() + deWordMapping.getQuintn());
		assertEquals("Expected ein trillion", input,
				new DeDecorator(Word.builder().quint(input).build()).pluraliseUnitRule().getQuint());
	}

	@Test
	public void test_pluralise_unit_rule_not_ein() {
		final String input = normalizeSpace(TWO.getWord() + SPC.val() + deWordMapping.getQuintn());
		Word res = new DeDecorator(Word.builder().quint(input).build()).pluraliseUnitRule();
		assertEquals("zwei trillionen", res.getQuint().toLowerCase());

	}

	@Test
	public void testPluraliseOneRule() {

		assertEquals("expected eins", ONE.getWord() + "s",
				new DeDecorator(Word.builder().hund("ein").build()).pluraliseHundredthRule(1).getHund());

		String input = normalizeSpace(ONE.getWord() + SPC.val() + deWordMapping.getMilln());
		assertEquals("Should contain million", input,
				new DeDecorator(Word.builder().mill(input).build()).pluraliseUnitRule().getMill());

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
