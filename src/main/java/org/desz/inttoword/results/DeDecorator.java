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

import static java.util.Arrays.asList;
import java.util.List;

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

	private String pluralise(String unitWord) {
		List<String> l = asList(unitWord.split(SPACE));
		return l.get(0).length() > 3 ? unitWord.endsWith("e") ? unitWord += "n" : unitWord + "en" : unitWord;

	}

	@Override
	public Word pluraliseUnitRule() {

		// pluralise units.

		WordBuilder builder = Word.builder();
		builder = nonNull(word.getQuint()) ?

				builder.quint(normalizeSpace(pluralise(word.getQuint()))) : builder;

		builder = nonNull(word.getQuadr()) ? builder.quadr(normalizeSpace(pluralise(word.getQuadr()))) : builder;

		builder = nonNull(word.getTrill()) ? builder.trill(normalizeSpace(pluralise(word.getTrill()))) : builder;

		builder = nonNull(word.getBill()) ?

				builder.bill(normalizeSpace(pluralise(word.getBill()))) : builder;

		builder = nonNull(word.getMill()) ?

				builder.mill(normalizeSpace(pluralise(word.getMill()))) : builder;

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