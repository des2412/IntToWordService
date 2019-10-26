/**
 * 
 */
package org.desz.inttoword.results;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.desz.inttoword.language.Punct.SPC;

import static java.util.Arrays.asList;
import java.util.List;

import org.desz.inttoword.language.ProvLangValues.DePair;
import org.desz.inttoword.language.ProvLangValues.DeUnit;

/**
 * @author des
 *
 */
public class DeDecorator implements IWordDecorator {

	private WordResult wordResult;

	public DeDecorator(WordResult wordResult) {
		requireNonNull(wordResult);
		this.wordResult = wordResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.desz.inttoword.results.IWordDecorator#pluraliseUnitRule(java.lang.
	 * String)
	 */
	@Override
	public WordResult pluraliseUnitRule() {

		// million and billion pluralised

		WordResult.Builder builder = new WordResult.Builder();

		builder = nonNull(wordResult.getBill()) ?

				builder = !normalizeSpace(wordResult.getBill()).split(SPC.val())[0].matches(DePair.ONE.getWord())
						? builder.withBill(normalizeSpace(wordResult.getBill().trim() + "n"))
						: builder.withBill(normalizeSpace(wordResult.getBill()))
				: builder;

		builder = nonNull(wordResult.getMill()) ?

				!wordResult.getMill().trim().split(SPC.val())[0].matches(DePair.ONE.getWord())
						? builder.withMill(normalizeSpace(wordResult.getMill()) + "en")
						: builder.withMill(normalizeSpace(wordResult.getMill()))
				: builder;

		builder = nonNull(wordResult.getThou()) ? builder.withThou(wordResult.getThou()) : builder;

		builder = nonNull(wordResult.getHund()) ? builder.withHund(wordResult.getHund()) : builder;

		return builder.build();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.desz.inttoword.results.IWordDecorator#removeWordRule(java.lang.String)
	 */
	@Override
	public WordResult replaceSpaceWithEmptyRule() {

		WordResult.Builder builder = new WordResult.Builder();
		builder = nonNull(wordResult.getBill()) ? builder.withBill(wordResult.getBill()) : builder;

		builder = nonNull(wordResult.getMill()) ? builder.withMill(wordResult.getMill()) : builder;

		builder = nonNull(wordResult.getThou()) ? builder.withThou(remove(wordResult.getThou(), SPC.val())) : builder;

		builder = nonNull(wordResult.getHund()) ? builder.withHund(remove(wordResult.getHund(), SPC.val())) : builder;

		return builder.build();

	}

	@Override
	public WordResult pluraliseOneRule(int val) {
		WordResult.Builder builder = new WordResult.Builder();

		builder = nonNull(wordResult.getBill()) ? builder.withBill(wordResult.getBill()) : builder;

		builder = nonNull(wordResult.getMill()) ? builder.withMill(wordResult.getMill().trim()) : builder;

		builder = nonNull(wordResult.getThou()) ? builder.withThou(wordResult.getThou()) : builder;

		builder = nonNull(wordResult.getHund())
				? valueOf(val).mod(valueOf(100)).equals(ONE) ? builder.withHund(wordResult.getHund() + "s")
						: builder.withHund(wordResult.getHund())
				: builder;

		return builder.build();
	}

	@Override
	public WordResult combineThouHundRule() {
		WordResult.Builder builder = new WordResult.Builder();
		builder = nonNull(wordResult.getBill()) ? builder.withBill(wordResult.getBill()) : builder;

		builder = nonNull(wordResult.getMill()) ? builder.withMill(wordResult.getMill()) : builder;

		final StringBuilder sb = new StringBuilder();
		String s = nonNull(wordResult.getThou()) ? sb.append(remove(wordResult.getThou(), SPC.val())).toString()
				: EMPTY;

		s = nonNull(wordResult.getHund()) ? sb.append(remove(wordResult.getHund(), SPC.val())).toString() : EMPTY;

		builder.withThou(sb.toString());
		return builder.build();

	}

	// TODO create test!
	@Override
	public WordResult restructureHundrethRule() {
		WordResult.Builder builder = new WordResult.Builder();
		List<String> arr = null;
		builder = nonNull(wordResult.getBill()) ? builder.withBill(wordResult.getBill()) : builder;

		final StringBuilder sb = new StringBuilder();
		if (nonNull(wordResult.getMill())) {
			arr = asList(wordResult.getMill().split(SPC.val()));
			builder = arr.size() > 2
					? builder.withMill(arr.get(1) + DeUnit.AND.val() + arr.get(0) + SPC.val() + arr.get(2) + SPC.val())
					: builder.withMill(wordResult.getMill());

		}

		if (nonNull(wordResult.getThou())) {
			arr = asList(wordResult.getThou().split(SPC.val()));
			builder = arr.size() > 2
					? builder.withThou(arr.get(1) + DeUnit.AND.val() + arr.get(0) + SPC.val() + arr.get(2) + SPC.val())
					: builder.withThou(wordResult.getThou());

		}

		if (nonNull(wordResult.getHund())) {
			arr = asList(wordResult.getHund().split(SPC.val()));
			builder = arr.size() > 2 ? builder.withHund(arr.get(1) + DeUnit.AND.val() + arr.get(0) + SPC.val())
					: builder.withHund(wordResult.getHund());

		}
		return builder.build();
	}
}