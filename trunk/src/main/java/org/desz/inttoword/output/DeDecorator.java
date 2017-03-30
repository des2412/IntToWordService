/**
 * 
 */
package org.desz.inttoword.output;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.language.LanguageRepository.DeIntWordPair;

/**
 * @author des
 *
 */
public class DeDecorator implements IWordDecorator {

	private WordResult wordResult;

	public DeDecorator(WordResult wordResult) {
		Objects.requireNonNull(wordResult);
		this.wordResult = wordResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.desz.inttoword.output.IWordDecorator#pluraliseUnitRule(java.lang.
	 * String)
	 */
	@Override
	public WordResult pluraliseUnitRule() {

		// million and billion pluralised

		WordResult.Builder builder = new WordResult.Builder();

		if (Objects.nonNull(wordResult)) {
			if (Objects.nonNull(wordResult.getBill())) {
				final String bill = wordResult.getBill().trim();
				final String num = bill.split(StringUtils.EMPTY)[0];
				final String mtch = DeIntWordPair.ONE.getWord();
				if (!num.matches(mtch))
					builder.withBill(bill + "n");

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
	 * org.desz.inttoword.output.IWordDecorator#removeWordRule(java.lang.String)
	 */
	@Override
	public WordResult replaceSpaceWithEmptyRule() {

		WordResult res = new WordResult.Builder().build();
		if (Objects.nonNull(wordResult.getBill()))
			res.setBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill()))
			res.setMill(wordResult.getMill());

		if (Objects.nonNull(wordResult.getThou())) {
			String s = wordResult.getThou().replaceAll("\\s+", "");
			res.setThou(s);
		}

		if (Objects.nonNull(wordResult.getHund())) {
			String s = wordResult.getHund().replaceAll("\\s+", "");
			res.setHund(s);
		}

		return res;

	}

	@Override
	public WordResult pluraliseOneRule(WordResult wordResult, int val) {
		WordResult.Builder builder = new WordResult.Builder();

		if (Objects.nonNull(wordResult.getBill()))
			builder.withBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill()))
			builder.withMill(wordResult.getMill().trim());

		if (Objects.nonNull(wordResult.getThou()))
			builder.withThou(wordResult.getThou());

		if (Objects.nonNull(wordResult.getHund())) {
			String s = wordResult.getHund();

			if (val % 100 == 1)
				s += "s";

			builder.withHund(s);
		}

		return builder.build();
	}

	@Override
	public WordResult combineThouHundRule() {
		WordResult res = new WordResult.Builder().build();
		if (Objects.nonNull(wordResult.getBill()))
			res.setBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill()))
			res.setMill(wordResult.getMill());

		StringBuilder sb = new StringBuilder();
		if (Objects.nonNull(wordResult.getThou())) {
			sb.append(wordResult.getThou().replaceAll("\\s+", ""));

		}

		if (Objects.nonNull(wordResult.getHund())) {
			sb.append(wordResult.getHund().replaceAll("\\s+", ""));
		}
		res.setThou(sb.toString());
		return res;
	}

}
