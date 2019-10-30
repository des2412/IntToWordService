/**
 * 
 */
package org.desz.inttoword.results;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.desz.inttoword.language.ProvLangValues.DeUnit.AND;
import static org.desz.inttoword.language.Punct.SPC;

import java.util.List;
import java.util.function.BiFunction;

import org.desz.inttoword.language.ProvLangValues.DePair;
import org.desz.inttoword.results.WordResult.Builder;

import lombok.Value;

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
						? builder.withBill(normalizeSpace(wordResult.getBill()) + "n")
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

	@Override
	public WordResult spaceWithEmptyRule() {

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

		builder = nonNull(wordResult.getMill()) ? builder.withMill(wordResult.getMill()) : builder;

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

		StringBuilder sb = new StringBuilder();
		sb = nonNull(wordResult.getThou()) ? sb.append(remove(wordResult.getThou(), SPC.val())) : sb.append(EMPTY);

		sb = nonNull(wordResult.getHund()) ? sb.append(remove(wordResult.getHund(), SPC.val())) : sb.append(EMPTY);

		builder.withThou(sb.toString());
		return builder.build();

	}

	@Value
	class OrdNum {
		String ord;
		int size;

	}

	private BiFunction<OrdNum, WordResult.Builder, WordResult.Builder> funcHunConv = (x, bld) -> {

		int i = x.getSize();

		List<String> arr = null;
		switch (x.getOrd()) {
		case "B":
			arr = asList(wordResult.getBill().split(SPC.val()));
			switch (i) {

			case 4:
				bld.withBill(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.withBill(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + capitalize(arr.get(2)) + SPC.val());
				break;

			case 2:
				bld.withBill(arr.get(0) + SPC.val() + capitalize(arr.get(1)) + SPC.val());
				break;
			case 1:
				bld.withBill(arr.get(0));
				break;
			}
			break;

		case "M":
			arr = asList(wordResult.getMill().split(SPC.val()));
			switch (i) {
			case 4:
				bld.withMill(arr.get(0) + arr.get(2) + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.withMill(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + capitalize(arr.get(2)) + SPC.val());
				break;

			case 2:
				bld.withMill(arr.get(0) + SPC.val() + capitalize(arr.get(1)) + SPC.val());
				break;
			case 1:
				bld.withMill(arr.get(0));
				break;
			}

			break;

		case "T":
			arr = asList(wordResult.getThou().split(SPC.val()));
			switch (i) {
			case 4:
				bld.withThou(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + arr.get(3));
				break;
			case 3:
				bld.withThou(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + arr.get(2) + SPC.val());
				break;

			case 2:
				bld.withThou(arr.get(0) + SPC.val() + arr.get(1) + SPC.val());
				break;
			case 1:
				bld.withThou(arr.get(0));
				break;
			}

			break;
		case "H":
			arr = asList(wordResult.getHund().split(SPC.val()));
			switch (i) {
			case 4:
				bld.withHund(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.withHund(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val());
				break;

			case 2:
				bld.withHund(arr.get(1) + AND.val() + arr.get(0) + SPC.val());
				break;
			case 1:
				bld.withHund(arr.get(0));
				break;
			}
			break;
		}

		return bld;
	};

	@Override
	public WordResult rearrangeHundredthRule() {
		Builder builder = new WordResult.Builder();

		builder = nonNull(wordResult.getBill())
				? funcHunConv.apply(new OrdNum("B", asList(wordResult.getBill().split(SPC.val())).size()), builder)
				: builder;

		builder = nonNull(wordResult.getMill())
				? funcHunConv.apply(new OrdNum("M", asList(wordResult.getMill().split(SPC.val())).size()), builder)
				: builder;

		builder = nonNull(wordResult.getThou())
				? funcHunConv.apply(new OrdNum("T", asList(wordResult.getThou().split(SPC.val())).size()), builder)
				: builder;

		builder = nonNull(wordResult.getHund())
				? funcHunConv.apply(new OrdNum("H", asList(wordResult.getHund().split(SPC.val())).size()), builder)
				: builder;

		return builder.build();
	}
}