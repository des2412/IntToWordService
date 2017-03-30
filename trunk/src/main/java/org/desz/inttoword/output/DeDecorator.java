/**
 * 
 */
package org.desz.inttoword.output;

import java.math.BigInteger;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.language.LanguageRepository.DeIntWordPair;
import org.desz.inttoword.language.LanguageRepository.DeUnit;
import org.desz.inttoword.language.LanguageRepository.DefUnit;

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
				final String bill = StringUtils
						.normalizeSpace(wordResult.getBill());
				final String num = bill.split(" ")[0];
				if (!num.matches(DeIntWordPair.ONE.getWord()))
					builder.withBill(bill + "n");

				else
					builder.withBill(wordResult.getBill());
			}

			if (Objects.nonNull(wordResult.getMill())) {
				final String mill = wordResult.getMill().trim();
				final String num = mill.split(" ")[0];
				if (!num.matches(DeIntWordPair.ONE.getWord()))
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

		if (Objects.nonNull(wordResult.getThou()))

			res.setThou(wordResult.getThou().replaceAll("\\s+", ""));

		if (Objects.nonNull(wordResult.getHund()))
			res.setHund(wordResult.getHund().replaceAll("\\s+", ""));

		return res;

	}

	@Override
	public WordResult pluraliseOneRule(int val) {
		WordResult.Builder builder = new WordResult.Builder();

		if (Objects.nonNull(wordResult.getBill()))
			builder.withBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill()))
			builder.withMill(wordResult.getMill().trim());

		if (Objects.nonNull(wordResult.getThou()))
			builder.withThou(wordResult.getThou());

		if (Objects.nonNull(wordResult.getHund())) {
			if (BigInteger.valueOf(val).mod(BigInteger.valueOf(100))
					.equals(BigInteger.ONE))
				builder.withHund(wordResult.getHund() + "s");
			else
				builder.withHund(wordResult.getHund());
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
		if (Objects.nonNull(wordResult.getThou()))
			sb.append(wordResult.getThou().replaceAll("\\s+", ""));

		if (Objects.nonNull(wordResult.getHund()))
			sb.append(wordResult.getHund().replaceAll("\\s+", ""));

		res.setThou(sb.toString());
		return res;

	}

	@Override
	public WordResult restructureHundrethRule() {
		WordResult res = new WordResult.Builder().build();
		String[] arr = null;
		if (Objects.nonNull(wordResult.getBill()))
			res.setBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill())) {
			arr = wordResult.getMill().split(" ");
			if (arr.length > 2) {
				String s = arr[1] + DeUnit.AND.val() + arr[0]
						+ DefUnit.SPACE.val() + arr[2] + DefUnit.SPACE.val();
				res.setMill(s);

			} else {
				res.setMill(wordResult.getMill());
			}

		}

		if (Objects.nonNull(wordResult.getThou())) {
			arr = wordResult.getThou().split(" ");
			if (arr.length > 2) {
				String s = arr[1] + DeUnit.AND.val() + arr[0]
						+ DefUnit.SPACE.val() + arr[2] + DefUnit.SPACE.val();
				res.setThou(s);

			} else {
				res.setThou(wordResult.getThou());
			}
		}

		if (Objects.nonNull(wordResult.getHund())) {
			arr = wordResult.getHund().split(" ");
			if (arr.length > 1) {
				String s = arr[1] + DeUnit.AND.val() + arr[0]
						+ DefUnit.SPACE.val();
				res.setHund(s);

			} else {
				res.setHund(wordResult.getHund());
			}
		}
		return res;
	}
}