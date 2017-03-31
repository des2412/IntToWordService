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
				final String num = bill.split(DefUnit.SPC.val())[0];
				if (!num.matches(DeIntWordPair.ONE.getWord()))
					builder.withBill(StringUtils.normalizeSpace(bill + "n"));

				else
					builder.withBill(
							StringUtils.normalizeSpace(wordResult.getBill()));
			}

			if (Objects.nonNull(wordResult.getMill())) {
				final String mill = wordResult.getMill().trim();
				final String num = mill.split(DefUnit.SPC.val())[0];
				if (!num.matches(DeIntWordPair.ONE.getWord()))
					builder.withMill(
							StringUtils.normalizeSpace(wordResult.getMill())
									+ "en");

				else
					builder.withMill(
							StringUtils.normalizeSpace(wordResult.getMill()));
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

		WordResult.Builder res = new WordResult.Builder();
		if (Objects.nonNull(wordResult.getBill()))
			res.withBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill()))
			res.withMill(wordResult.getMill());

		if (Objects.nonNull(wordResult.getThou()))
			res.withThou(StringUtils.remove(wordResult.getThou(),
					DefUnit.SPC.val()));

		if (Objects.nonNull(wordResult.getHund()))
			res.withHund(StringUtils.remove(wordResult.getHund(),
					DefUnit.SPC.val()));

		return res.build();

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
		WordResult.Builder res = new WordResult.Builder();
		if (Objects.nonNull(wordResult.getBill()))
			res.withBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill()))
			res.withMill(wordResult.getMill());

		StringBuilder sb = new StringBuilder();
		if (Objects.nonNull(wordResult.getThou()))
			sb.append(StringUtils.remove(wordResult.getThou(),
					DefUnit.SPC.val()));

		if (Objects.nonNull(wordResult.getHund()))
			sb.append(StringUtils.remove(wordResult.getHund(),
					DefUnit.SPC.val()));

		res.withThou(sb.toString());
		return res.build();

	}

	@Override
	public WordResult restructureHundrethRule() {
		WordResult.Builder res = new WordResult.Builder();
		String[] arr = null;
		if (Objects.nonNull(wordResult.getBill()))
			res.withBill(wordResult.getBill());

		if (Objects.nonNull(wordResult.getMill())) {
			arr = wordResult.getMill().split(" ");
			if (arr.length > 2) {
				String s = arr[1] + DeUnit.AND.val() + arr[0]
						+ DefUnit.SPC.val() + arr[2] + DefUnit.SPC.val();
				res.withMill(s);

			} else {
				res.withMill(wordResult.getMill());
			}

		}

		if (Objects.nonNull(wordResult.getThou())) {
			arr = wordResult.getThou().split(DefUnit.SPC.val());
			if (arr.length > 2) {
				String s = arr[1] + DeUnit.AND.val() + arr[0]
						+ DefUnit.SPC.val() + arr[2] + DefUnit.SPC.val();
				res.withThou(s);

			} else {
				res.withThou(wordResult.getThou());
			}
		}

		if (Objects.nonNull(wordResult.getHund())) {
			arr = wordResult.getHund().split(" ");
			if (arr.length > 1) {
				String s = arr[1] + DeUnit.AND.val() + arr[0]
						+ DefUnit.SPC.val();
				res.withHund(s);

			} else {
				res.withHund(wordResult.getHund());
			}
		}
		return res.build();
	}
}