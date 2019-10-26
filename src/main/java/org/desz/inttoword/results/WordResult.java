package org.desz.inttoword.results;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.regex.Pattern.compile;
import static org.desz.inttoword.language.Punct.SPC;

import java.util.List;
import java.util.regex.Pattern;

import org.desz.inttoword.language.ProvLangValues.DePair;
import org.desz.inttoword.language.ProvLangValues.FrPair;
import org.desz.inttoword.language.ProvLangValues.NlPair;
import org.desz.inttoword.language.ProvLangValues.UkPair;

public class WordResult {

	private final String bill;
	private final String mill;
	private final String thou;
	private final String hund;

	private WordResult(Builder builder) {
		this.bill = builder.bill;
		this.mill = builder.mill;
		this.thou = builder.thou;
		this.hund = builder.hund;
	}

	/**
	 * @return the bill
	 */
	public String getBill() {
		return bill;
	}

	/**
	 * @param bill the bill to set
	 */
	/**
	 * @return the mill
	 */
	public String getMill() {
		return mill;
	}

	/**
	 * @return the thou
	 */
	public String getThou() {
		return thou;
	}

	/**
	 * @return the hund
	 */
	public String getHund() {
		return hund;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (nonNull(bill))
			sb.append(bill);
		if (nonNull(mill))
			sb.append(mill);
		if (nonNull(thou))
			sb.append(thou);
		if (nonNull(hund))
			sb.append(hund);
		return sb.toString().trim();
	}

	/**
	 * Builder of WordResult.
	 * 
	 * @author des
	 *
	 */
	public static class Builder {

		private String bill;
		private String mill;
		private String thou;
		private String hund;

		private static final List<Pattern> matches = asList(compile("(?i:.*" + UkPair.ZERO.getWord() + ".*)"),
				compile("(?i:.*" + DePair.ZERO.getWord() + ".*)"), compile("(?i:.*" + NlPair.ZERO.getWord() + ".*)"),
				compile("(?i:.*" + FrPair.ZERO.getWord() + ".*)")

		);

		public Builder withBill(String bill) {
			this.bill = bill + SPC.val();
			return this;
		}

		public Builder withMill(String mill) {
			this.mill = mill + SPC.val();
			return this;
		}

		public Builder withThou(String thou) {
			this.thou = thou + SPC.val();
			return this;
		}

		public Builder withHund(String hund) {
			this.hund = hund;
			return this;
		}

		public WordResult build() {
			validate();
			return new WordResult(this);
		}

		private void validate() {

			if (!isNull(hund) && matchesZero(hund))
				hund = null;
			if (!isNull(thou) && matchesZero(thou))
				thou = null;
			if (!isNull(mill) && matchesZero(mill))
				mill = null;
			if (!isNull(bill) && matchesZero(bill))
				bill = null;

		}

		private boolean matchesZero(final String s) {
			return matches.stream().anyMatch(k -> k.matcher(s).matches());
		}
	}

}
