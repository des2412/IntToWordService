package org.desz.inttoword.output;

import static org.desz.inttoword.language.ProvLangFactory.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.desz.inttoword.language.LanguageRepository;
import org.desz.inttoword.language.LanguageRepository.DeIntWordPair;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.language.NumericalLangMapping;
import org.junit.Test;

public class TestDeWordBuilderDecorator {

	private DeWordBuilderDecorator wordBuilderDecorator;
	@Test
	public void testPluraliseUnitRule() {

		final NumericalLangMapping deLangMap = getInstance()
				.numericMap(ProvLang.DE);

		WordResult.Builder builder = new WordResult.Builder();
		String input = DeIntWordPair.ONE.getWord()
				+ LanguageRepository.Def.SPACE.val() + deLangMap.getMilln();
		builder.withMill(input);

		wordBuilderDecorator = new DeWordBuilderDecorator(builder.build());
		WordResult res = wordBuilderDecorator.pluraliseUnitRule();

		assertEquals("", input, res.getMill().trim());
		// assert pluralisation

		input = DeIntWordPair.TWO.getWord() + LanguageRepository.Def.SPACE.val()
				+ deLangMap.getMilln();
		builder = new WordResult.Builder();
		builder.withMill(input);
		wordBuilderDecorator = new DeWordBuilderDecorator(builder.build());
		res = wordBuilderDecorator.pluraliseUnitRule();
		assertEquals("", input + "en", res.getMill().trim());

		input = DeIntWordPair.TWO.getWord() + LanguageRepository.Def.SPACE.val()
				+ deLangMap.getBilln();
		builder = new WordResult.Builder();
		builder.withBill(input);
		wordBuilderDecorator = new DeWordBuilderDecorator(builder.build());
		res = wordBuilderDecorator.pluraliseUnitRule();
		assertEquals("", input + "n", res.getBill().trim());

	}

	@Test(expected = NullPointerException.class)
	public void testConstructor() {
		new DeWordBuilderDecorator(null);
	}

	@Test
	public void testReplaceSpaceWithEmptyRule() {

		WordResult.Builder builder = new WordResult.Builder()
				.withThou("hundert und seben tausend")
				.withHund("drei und zwanzig");
		DeWordBuilderDecorator wordBuilderDecorator = new DeWordBuilderDecorator(
				builder.build());
		assertFalse("Empty Space should not be present", wordBuilderDecorator
				.replaceSpaceWithEmptyRule().getThou().contains(" "));
	}

	@Test
	public void testPluraliseValueOfOneRule() {
		WordResult.Builder builder = new WordResult.Builder().withHund("ein");
		DeWordBuilderDecorator wordBuilderDecorator = new DeWordBuilderDecorator(
				builder.build());
		assertEquals("Pluralisation Failed", "eins", wordBuilderDecorator
				.pluraliseValueOfOneRule(builder.build(), 1).getHund());
	}

}
