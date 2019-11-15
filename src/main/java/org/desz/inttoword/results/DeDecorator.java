/**
 * 
 */
package org.desz.inttoword.results;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.remove;

import org.desz.inttoword.language.ProvLangValues.DePair;
import org.desz.inttoword.results.Word.WordBuilder;

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

		// pluralise applicable units.

		WordBuilder builder = Word.builder();
		builder = nonNull(word.getQuint()) ?

				builder = !normalizeSpace(word.getQuint()).split(SPACE)[0].matches(DePair.ONE.getWord())
						? builder.quint(normalizeSpace(word.getQuint()))
						: builder.quint(normalizeSpace(word.getQuint()))
				: builder;

		builder = nonNull(word.getQuint()) ?

				builder = !normalizeSpace(word.getQuadr()).split(SPACE)[0].matches(DePair.ONE.getWord())
						? builder.quadr(normalizeSpace(word.getQuadr()))
						: builder.quadr(normalizeSpace(word.getQuadr()))
				: builder;

		builder = nonNull(word.getTrill()) ?

				builder = !normalizeSpace(word.getTrill()).split(SPACE)[0].matches(DePair.ONE.getWord())
						? builder.trill(normalizeSpace(word.getTrill()))
						: builder.trill(normalizeSpace(word.getTrill()))
				: builder;

		builder = nonNull(word.getBill()) ?

				builder = !normalizeSpace(word.getBill()).split(SPACE)[0].matches(DePair.ONE.getWord())
						? builder.bill(normalizeSpace(word.getBill()) + "n")
						: builder.bill(normalizeSpace(word.getBill()))
				: builder;

		builder = nonNull(word.getMill()) ?

				!word.getMill().split(SPACE)[0].matches(DePair.ONE.getWord())
						? builder.mill(normalizeSpace(word.getMill()) + "en")
						: builder.mill(normalizeSpace(word.getMill()))
				: builder;

		builder = nonNull(word.getThou()) ? builder.thou(word.getThou()) : builder;

		builder = nonNull(word.getHund()) ? builder.hund(word.getHund()) : builder;

		return builder.build();

	}

	@Override
	public Word pluraliseRule(int val) {
		WordBuilder builder = word.toBuilder();

		builder = nonNull(word.getHund())
				? valueOf(val).mod(valueOf(100)).equals(ONE) ? builder.hund(word.getHund() + "s")
						: builder.hund(word.getHund())
				: builder;

		return builder.build();
	}

	@Override
	public Word combineThouHundRule() {
		WordBuilder builder = word.toBuilder();

		StringBuilder sb = new StringBuilder();
		sb = nonNull(word.getThou()) ? sb.append(remove(word.getThou(), SPACE)) : sb.append(EMPTY);

		sb = nonNull(word.getHund()) ? sb.append(remove(word.getHund(), SPACE)) : sb.append(EMPTY);

		builder.thou(sb.toString().toLowerCase()).hund(null);

		return builder.build();

	}

}