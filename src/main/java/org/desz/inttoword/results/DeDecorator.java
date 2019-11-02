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
import org.desz.inttoword.results.WordResult.WordResultBuilder;

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

		WordResultBuilder builder = WordResult.builder();

		builder = nonNull(wordResult.getBill()) ?

				builder = !normalizeSpace(wordResult.getBill()).split(SPC.val())[0].matches(DePair.ONE.getWord())
						? builder.bill(normalizeSpace(wordResult.getBill()) + "n")
						: builder.bill(normalizeSpace(wordResult.getBill()))
				: builder;

		builder = nonNull(wordResult.getMill()) ?

				!wordResult.getMill().trim().split(SPC.val())[0].matches(DePair.ONE.getWord())
						? builder.mill(normalizeSpace(wordResult.getMill()) + "en")
						: builder.mill(normalizeSpace(wordResult.getMill()))
				: builder;

		builder = nonNull(wordResult.getThou()) ? builder.thou(wordResult.getThou()) : builder;

		builder = nonNull(wordResult.getHund()) ? builder.hund(wordResult.getHund()) : builder;

		return builder.build();

	}

	@Override
	public WordResult spaceWithEmptyRule() {

		WordResultBuilder builder = WordResult.builder();
		builder = nonNull(wordResult.getBill()) ? builder.bill(wordResult.getBill()) : builder;

		builder = nonNull(wordResult.getMill()) ? builder.mill(wordResult.getMill()) : builder;

		builder = nonNull(wordResult.getThou()) ? builder.thou(remove(wordResult.getThou(), SPC.val())) : builder;

		builder = nonNull(wordResult.getHund()) ? builder.hund(remove(wordResult.getHund(), SPC.val())) : builder;

		return builder.build();

	}

	@Override
	public WordResult pluraliseOneRule(int val) {
		WordResultBuilder builder = WordResult.builder();

		builder = nonNull(wordResult.getBill()) ? builder.bill(wordResult.getBill()) : builder;

		builder = nonNull(wordResult.getMill()) ? builder.mill(wordResult.getMill()) : builder;

		builder = nonNull(wordResult.getThou()) ? builder.thou(wordResult.getThou()) : builder;

		builder = nonNull(wordResult.getHund())
				? valueOf(val).mod(valueOf(100)).equals(ONE) ? builder.hund(wordResult.getHund() + "s")
						: builder.hund(wordResult.getHund())
				: builder;

		return builder.build();
	}

	@Override
	public WordResult combineThouHundRule() {
		WordResultBuilder builder = WordResult.builder();
		builder = nonNull(wordResult.getBill()) ? builder.bill(wordResult.getBill()) : builder;

		builder = nonNull(wordResult.getMill()) ? builder.mill(wordResult.getMill()) : builder;

		StringBuilder sb = new StringBuilder();
		sb = nonNull(wordResult.getThou()) ? sb.append(remove(wordResult.getThou(), SPC.val())) : sb.append(EMPTY);

		sb = nonNull(wordResult.getHund()) ? sb.append(remove(wordResult.getHund(), SPC.val())) : sb.append(EMPTY);

		builder.thou(sb.toString());
		return builder.build();

	}

	@Value
	class OrdNum {
		String ord;
		int size;

	}

	private BiFunction<OrdNum, WordResultBuilder, WordResultBuilder> funcHunConv = (x, bld) -> {

		final int i = x.getSize();

		List<String> arr = null;
		switch (x.getOrd()) {
		case "B":
			arr = asList(wordResult.getBill().split(SPC.val()));
			switch (i) {

			case 4:
				bld.bill(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.bill(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + capitalize(arr.get(2)) + SPC.val());
				break;

			case 2:
				bld.bill(arr.get(0) + SPC.val() + capitalize(arr.get(1)) + SPC.val());
				break;
			case 1:
				bld.bill(arr.get(0));
				break;
			}
			break;

		case "M":
			arr = asList(wordResult.getMill().split(SPC.val()));
			switch (i) {
			case 4:
				bld.mill(arr.get(0) + arr.get(2) + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.mill(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + capitalize(arr.get(2)) + SPC.val());
				break;

			case 2:
				bld.mill(arr.get(0) + SPC.val() + capitalize(arr.get(1)) + SPC.val());
				break;
			case 1:
				bld.mill(arr.get(0));
				break;
			}

			break;

		case "T":
			arr = asList(wordResult.getThou().split(SPC.val()));
			switch (i) {
			case 4:
				bld.thou(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + arr.get(3));
				break;
			case 3:
				bld.thou(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + arr.get(2) + SPC.val());
				break;

			case 2:
				bld.thou(arr.get(0) + SPC.val() + arr.get(1) + SPC.val());
				break;
			case 1:
				bld.thou(arr.get(0));
				break;
			}

			break;
		case "H":
			arr = asList(wordResult.getHund().split(SPC.val()));
			switch (i) {
			case 4:
				bld.hund(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.hund(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val());
				break;

			case 2:
				bld.hund(arr.get(1) + AND.val() + arr.get(0) + SPC.val());
				break;
			case 1:
				bld.hund(arr.get(0));
				break;
			}
			break;
		}

		return bld;
	};

	@Override
	public WordResult rearrangeHundredthRule() {
		WordResultBuilder builder = WordResult.builder();

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