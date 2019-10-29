package org.desz.inttoword.results;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.desz.inttoword.language.Punct.SPC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.language.ProvLangValues.DePair;
import org.desz.inttoword.results.DeDecorator;
import org.desz.inttoword.results.WordResult;
import org.junit.Test;

public class TestDeDecorator {

	@Test
	public void testPluraliseOneRule() {

		final IntWordMapping deLangMap = getInstance().getMapForProvLang(ProvLang.DE);

		WordResult.Builder builder = new WordResult.Builder().withHund("ein");
		DeDecorator deDecorator = new DeDecorator(builder.build());
		assertEquals("expected eins", DePair.ONE.getWord() + "s", deDecorator.pluraliseOneRule(1).getHund());

		builder = new WordResult.Builder();
		String input = normalizeSpace(DePair.ONE.getWord() + SPC.val() + deLangMap.getMilln());
		builder.withMill(input);

		deDecorator = new DeDecorator(builder.build());
		WordResult res = deDecorator.pluraliseUnitRule();

		assertEquals("Actual should contain million", input, res.getMill().trim());
		// TWO should be pluralised (million -> millionen).
		input = normalizeSpace(DePair.TWO.getWord() + SPC.val() + deLangMap.getMilln());
		builder = new WordResult.Builder();
		builder.withMill(input);
		deDecorator = new DeDecorator(builder.build());
		res = deDecorator.pluraliseUnitRule();
		assertEquals("Actual should match with ...en", input + "en", res.getMill().trim());

		input = normalizeSpace(DePair.TWO.getWord() + SPC.val() + deLangMap.getBilln());
		builder = new WordResult.Builder();
		builder.withBill(input);
		deDecorator = new DeDecorator(builder.build());
		res = deDecorator.pluraliseUnitRule();
		assertEquals("Actual should match with ...n", input + "n", res.getBill().trim());

	}

	@Test(expected = NullPointerException.class)
	public void testConstructor() {
		new DeDecorator(null);
	}

	@Test
	public void testReplaceSpaceWithEmptyRule() {

		WordResult.Builder builder = new WordResult.Builder().withThou("hundert und seben tausend")
				.withHund("drei und zwanzig");
		assertFalse("Not expecting empty in thousandth unit",
				new DeDecorator(builder.build()).spaceWithEmptyRule().getThou().trim().contains(SPC.val()));
	}

	@Test
	public void testcombineThouHundRule() {
		WordResult.Builder builder = new WordResult.Builder().withThou("neunhundertneunundneunzigtausend")
				.withHund("neunhundertneunundneunzig");

		assertEquals("neunhundertneunundneunzigtausendneunhundertneunundneunzig",
				new DeDecorator(builder.build()).combineThouHundRule().toString());

	}

}
