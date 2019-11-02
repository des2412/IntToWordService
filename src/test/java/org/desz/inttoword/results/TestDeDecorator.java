package org.desz.inttoword.results;

import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.desz.inttoword.language.ProvLangValues.DePair.ONE;
import static org.desz.inttoword.language.ProvLangValues.DePair.TWO;
import static org.desz.inttoword.language.Punct.SPC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.junit.Test;

public class TestDeDecorator {

	final IntWordMapping deWordMapping = getInstance().getMapForProvLang(ProvLang.DE);

	@Test
	public void testPluraliseOneRule() {

		assertEquals("expected eins", ONE.getWord() + "s",
				new DeDecorator(WordResult.builder().hund("ein").build()).pluraliseOneRule(1).getHund());

		String input = normalizeSpace(ONE.getWord() + SPC.val() + deWordMapping.getMilln());
		assertEquals("Actual should contain million", input,
				new DeDecorator(WordResult.builder().mill(input).build()).pluraliseUnitRule().getMill());
		// TWO should be pluralised (million -> millionen).
		input = normalizeSpace(TWO.getWord() + SPC.val() + deWordMapping.getMilln());
		assertEquals("Actual should match with ...en", input + "en",
				new DeDecorator(WordResult.builder().mill(input).build()).pluraliseUnitRule().getMill());

		input = normalizeSpace(TWO.getWord() + SPC.val() + deWordMapping.getBilln());
		assertEquals("Actual should match with ...n", input + "n",
				new DeDecorator(WordResult.builder().bill(input).build()).pluraliseUnitRule().getBill());

	}

	@Test(expected = NullPointerException.class)
	public void testConstructor() {
		new DeDecorator(null);
	}

	@Test
	public void testReplaceSpaceWithEmptyRule() {

		assertFalse("Not expecting empty in thousandth",
				new DeDecorator(WordResult.builder().thou("hundert und seben tausend").hund("drei und zwanzig").build())
						.spaceWithEmptyRule().getThou().trim().contains(SPC.val()));
	}

	@Test
	public void testcombineThouHundRule() {

		assertEquals("neunhundertneunundneunzigtausendneunhundertneunundneunzig", new DeDecorator(
				WordResult.builder().thou("neunhundertneunundneunzigtausend").hund("neunhundertneunundneunzig").build())
						.combineThouHundRule().getThou());

	}

}
