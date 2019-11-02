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
import org.desz.inttoword.results.Word.WordBuilder;

import lombok.Value;

/**
 * @author des
 *
 */
public class DeDecorator implements IWordDecorator {

	private Word word;

	public DeDecorator(Word word) {
		requireNonNull(word);
		this.word = word;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.desz.inttoword.results.IWordDecorator#pluraliseUnitRule(java.lang.
	 * String)
	 */
	@Override
	public Word pluraliseUnitRule() {

		// million and billion pluralised

		WordBuilder builder = Word.builder();

		builder = nonNull(word.getBill()) ?

				builder = !normalizeSpace(word.getBill()).split(SPC.val())[0].matches(DePair.ONE.getWord())
						? builder.bill(normalizeSpace(word.getBill()) + "n")
						: builder.bill(normalizeSpace(word.getBill()))
				: builder;

		builder = nonNull(word.getMill()) ?

				!word.getMill().split(SPC.val())[0].matches(DePair.ONE.getWord())
						? builder.mill(normalizeSpace(word.getMill()) + "en")
						: builder.mill(normalizeSpace(word.getMill()))
				: builder;

		builder = nonNull(word.getThou()) ? builder.thou(word.getThou()) : builder;

		builder = nonNull(word.getHund()) ? builder.hund(word.getHund()) : builder;

		return builder.build();

	}

	@Override
	public Word spaceWithEmptyRule() {

		WordBuilder builder = Word.builder();

		builder = nonNull(word.getThou()) ? builder.thou(remove(word.getThou(), SPC.val())) : builder;

		builder = nonNull(word.getHund()) ? builder.hund(remove(word.getHund(), SPC.val())) : builder;

		return builder.build();

	}

	@Override
	public Word pluraliseRule(int val) {
		WordBuilder builder = Word.builder();

		builder = nonNull(word.getBill()) ? builder.bill(word.getBill()) : builder;

		builder = nonNull(word.getMill()) ? builder.mill(word.getMill()) : builder;

		builder = nonNull(word.getThou()) ? builder.thou(word.getThou()) : builder;

		builder = nonNull(word.getHund())
				? valueOf(val).mod(valueOf(100)).equals(ONE) ? builder.hund(word.getHund() + "s")
						: builder.hund(word.getHund())
				: builder;

		return builder.build();
	}

	@Override
	public Word combineThouHundRule() {
		WordBuilder builder = Word.builder();
		builder = nonNull(word.getBill()) ? builder.bill(word.getBill()) : builder;

		builder = nonNull(word.getMill()) ? builder.mill(word.getMill()) : builder;

		StringBuilder sb = new StringBuilder();
		sb = nonNull(word.getThou()) ? sb.append(remove(word.getThou(), SPC.val())) : sb.append(EMPTY);

		sb = nonNull(word.getHund()) ? sb.append(remove(word.getHund(), SPC.val())) : sb.append(EMPTY);

		return builder.thou(sb.toString()).build();

	}

	@Value
	class IntCharacteristic {
		// billion..hundred
		String unit;
		int size;

	}

	private BiFunction<IntCharacteristic, WordBuilder, WordBuilder> funcHunConv = (intCharacteristic, bld) -> {

		final int i = intCharacteristic.getSize();

		List<String> arr = null;
		switch (intCharacteristic.getUnit()) {
		case "B":
			arr = asList(word.getBill().split(SPC.val()));
			switch (i) {

			case 4:
				bld.bill(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.bill(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + capitalize(arr.get(2)));
				break;

			case 2:
				bld.bill(arr.get(0) + SPC.val() + capitalize(arr.get(1)));
				break;
			case 1:
				bld.bill(arr.get(0));
				break;
			}
			break;

		case "M":
			arr = asList(word.getMill().split(SPC.val()));
			switch (i) {
			case 4:
				bld.mill(arr.get(0) + arr.get(2) + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.mill(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + capitalize(arr.get(2)));
				break;

			case 2:
				bld.mill(arr.get(0) + SPC.val() + capitalize(arr.get(1)));
				break;
			case 1:
				bld.mill(arr.get(0));
				break;
			}

			break;

		case "T":
			arr = asList(word.getThou().split(SPC.val()));
			switch (i) {
			case 4:
				bld.thou(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + arr.get(3));
				break;
			case 3:
				bld.thou(arr.get(1) + AND.val() + arr.get(0) + SPC.val() + arr.get(2));
				break;

			case 2:
				bld.thou(arr.get(0) + SPC.val() + arr.get(1));
				break;
			case 1:
				bld.thou(arr.get(0));
				break;
			}

			break;
		case "H":
			arr = asList(word.getHund().split(SPC.val()));
			switch (i) {
			case 4:
				bld.hund(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1) + SPC.val() + capitalize(arr.get(3)));
				break;
			case 3:
				bld.hund(arr.get(0) + arr.get(2) + SPC.val() + arr.get(1));
				break;

			case 2:
				bld.hund(arr.get(1) + AND.val() + arr.get(0));
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
	public Word reArrangeHundredthRule() {
		WordBuilder builder = Word.builder();

		builder = nonNull(word.getBill())
				? funcHunConv.apply(new IntCharacteristic("B", asList(word.getBill().split(SPC.val())).size()), builder)
				: builder;

		builder = nonNull(word.getMill())
				? funcHunConv.apply(new IntCharacteristic("M", asList(word.getMill().split(SPC.val())).size()), builder)
				: builder;

		builder = nonNull(word.getThou())
				? funcHunConv.apply(new IntCharacteristic("T", asList(word.getThou().split(SPC.val())).size()), builder)
				: builder;

		builder = nonNull(word.getHund())
				? funcHunConv.apply(new IntCharacteristic("H", asList(word.getHund().split(SPC.val())).size()), builder)
				: builder;

		return builder.build();
	}
}