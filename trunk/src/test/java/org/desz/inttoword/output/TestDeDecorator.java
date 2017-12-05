package org.desz.inttoword.output;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.desz.inttoword.language.Punct.SPC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.language.ProvLangValues.DePair;
import org.junit.Test;

public class TestDeDecorator {

	@Test
	public void testPluraliseOneRule() {

		final IntWordMapping deLangMap = getInstance()
				.getMapForProvLang(ProvLang.DE);

		WordResult.Builder builder = new WordResult.Builder().withHund("ein");
		DeDecorator deDecorator = new DeDecorator(builder.build());
		assertEquals("expected eins", DePair.ONE.getWord() + "s",
				deDecorator.pluraliseOneRule(1).getHund());

		builder = new WordResult.Builder();
		String input = StringUtils.normalizeSpace(
				DePair.ONE.getWord() + SPC.val() + deLangMap.getMilln());
		builder.withMill(input);

		deDecorator = new DeDecorator(builder.build());
		WordResult res = deDecorator.pluraliseUnitRule();

		assertEquals("Actual should contain million", input,
				res.getMill().trim());
		// TWO should be pluralised (million -> millionen).
		input = StringUtils.normalizeSpace(
				DePair.TWO.getWord() + SPC.val() + deLangMap.getMilln());
		builder = new WordResult.Builder();
		builder.withMill(input);
		deDecorator = new DeDecorator(builder.build());
		res = deDecorator.pluraliseUnitRule();
		assertEquals("Actual should match with ...en", input + "en",
				res.getMill().trim());

		input = StringUtils.normalizeSpace(
				DePair.TWO.getWord() + SPC.val() + deLangMap.getBilln());
		builder = new WordResult.Builder();
		builder.withBill(input);
		deDecorator = new DeDecorator(builder.build());
		res = deDecorator.pluraliseUnitRule();
		assertEquals("Actual should match with ...n", input + "n",
				res.getBill().trim());

	}

	@Test(expected = NullPointerException.class)
	public void testConstructor() {
		new DeDecorator(null);
	}

	@Test
	public void testReplaceSpaceWithEmptyRule() {

		WordResult.Builder builder = new WordResult.Builder()
				.withThou("hundert und seben tausend")
				.withHund("drei und zwanzig");
		DeDecorator wordBuilderDecorator = new DeDecorator(builder.build());
		WordResult res = wordBuilderDecorator.replaceSpaceWithEmptyRule();
		assertFalse("Empty Space in thousandth unit shouldn't be present",
				res.getThou().trim().contains(SPC.val()));
	}

	@Test
	public void testPluraliseValueOfOneRule() {

	}

}
