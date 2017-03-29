package org.desz.inttoword.output;

import static org.desz.inttoword.language.LanguageRepository.DefUnit.SPACE;

import java.util.Objects;
import java.util.regex.Pattern;

import org.desz.inttoword.language.LanguageRepository.DeIntWordPair;
import org.desz.inttoword.language.LanguageRepository.FrIntWordPair;
import org.desz.inttoword.language.LanguageRepository.NlIntWordPair;
import org.desz.inttoword.language.LanguageRepository.UkIntWordPair;

public class WordResult {

	private String bill;
	private String mill;
	private String thou;

	private String hund;

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
	 * @param bill
	 *            the bill to set
	 */
	public void setBill(String bill) {
		this.bill = bill;
	}

	/**
	 * @return the mill
	 */
	public String getMill() {
		return mill;
	}

	/**
	 * @param mill
	 *            the mill to set
	 */
	public void setMill(String mill) {
		this.mill = mill;
	}

	/**
	 * @return the thou
	 */
	public String getThou() {
		return thou;
	}

	/**
	 * @param thou
	 *            the thou to set
	 */
	public void setThou(String thou) {
		this.thou = thou;
	}

	/**
	 * @return the hund
	 */
	public String getHund() {
		return hund;
	}

	/**
	 * @param hund
	 *            the hund to set
	 */
	public void setHund(String hund) {
		this.hund = hund;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		if (Objects.nonNull(bill))
			builder2.append(bill);
		if (Objects.nonNull(mill))
			builder2.append(mill);
		if (Objects.nonNull(thou))
			builder2.append(thou);
		if (Objects.nonNull(hund))
			builder2.append(hund);
		return builder2.toString().trim();
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

		public Builder withBill(String bill) {
			this.bill = bill + SPACE.val();
			return this;
		}

		public Builder withMill(String mill) {
			this.mill = mill + SPACE.val();
			return this;
		}

		public Builder withThou(String thou) {
			this.thou = thou + SPACE.val();
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

			if (!Objects.isNull(hund) && matchesZero(hund))
				hund = null;
			if (!Objects.isNull(thou) && matchesZero(thou))
				thou = null;
			if (!Objects.isNull(mill) && matchesZero(mill))
				mill = null;
			if (!Objects.isNull(bill) && matchesZero(bill))
				bill = null;

		}

		private static Pattern p1 = Pattern
				.compile("(?i:.*" + UkIntWordPair.ZERO.getWord() + ".*)");
		private static Pattern p2 = Pattern
				.compile("(?i:.*" + DeIntWordPair.ZERO.getWord() + ".*)");
		private static Pattern p3 = Pattern
				.compile("(?i:.*" + NlIntWordPair.ZERO.getWord() + ".*)");
		private static Pattern p4 = Pattern
				.compile("(?i:.*" + FrIntWordPair.ZERO.getWord() + ".*)");

		private boolean matchesZero(String s) {
			if (p1.matcher(s).matches())
				return true;

			if (p2.matcher(s).matches())
				return true;

			if (p3.matcher(s).matches())
				return true;

			if (p4.matcher(s).matches())
				return true;

			return false;
		}
	}

}
