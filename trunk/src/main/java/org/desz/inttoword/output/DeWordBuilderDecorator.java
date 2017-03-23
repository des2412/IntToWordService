/**
 * 
 */
package org.desz.inttoword.output;

import java.util.Objects;

import org.desz.inttoword.language.LanguageRepository.DeIntWordPair;

/**
 * @author des
 *
 */
public class DeWordBuilderDecorator implements WordDecorator {

	private WordResult wordResult;

	public DeWordBuilderDecorator(WordResult builder) {
		super();
		this.wordResult = builder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.desz.inttoword.output.WordDecorator#pluraliseUnitRule(java.lang.
	 * String)
	 */
	@Override
	public WordResult pluraliseUnitRule() {

		// check for million to be pluralised

		WordResult.Builder builder = new WordResult.Builder();

		if (Objects.nonNull(wordResult)) {
			if (Objects.nonNull(wordResult.getBill())) {
				final String bill = wordResult.getBill().trim();
				final String num = bill.split(" ")[0];
				final String mtch = DeIntWordPair.ONE.getWord();
				if (!num.matches(mtch))
					builder.withBill(wordResult.getBill().trim() + "n");

				else
					builder.withMill(wordResult.getBill());
			}

			if (Objects.nonNull(wordResult.getMill())) {
				final String mill = wordResult.getMill().trim();
				final String num = mill.split(" ")[0];
				final String mtch = DeIntWordPair.ONE.getWord();
				if (!num.matches(mtch))
					builder.withMill(wordResult.getMill().trim() + "en");

				else
					builder.withMill(wordResult.getMill());
			}
			if (Objects.nonNull(wordResult.getThou()))
				builder.withThou(wordResult.getThou());

			if (Objects.nonNull(wordResult.getHund()))
				builder.withHund(wordResult.getHund());

		}

		return builder.build();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.desz.inttoword.output.WordDecorator#removeWordRule(java.lang.String)
	 */
	@Override
	public WordResult removeWordRule(String word) {
		return null;
	}

	@Override
	public WordResult pluraliseWordRule(WordResult wordResult, String word) {
		WordResult.Builder builder = new WordResult.Builder();

		if (Objects.nonNull(wordResult.getBill()))
			builder.withBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill()))
			builder.withMill(wordResult.getMill().trim());

		if (Objects.nonNull(wordResult.getThou()))
			builder.withThou(wordResult.getThou());

		if (Objects.nonNull(wordResult.getHund()))
			builder.withHund(wordResult.getHund() + "s");

		return builder.build();
	}

}
