/**
 * 
 */
package org.desz.inttoword.language;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

/**
 * @author des
 *
 */
public class NumericalLangMapping {

	private String milln;
	private String thoud;
	private String hund;
	private String and;
	private String billn;
	private Map<String, String> intToWordMap;

	private NumericalLangMapping(Builder builder) {
		this.milln = builder.milln;
		this.thoud = builder.thoud;
		this.hund = builder.hund;
		this.and = builder.and;
		this.billn = builder.billn;
		this.intToWordMap = builder.intToWordMap;
	}

	/**
	 * @return the milln
	 */
	public String getMilln() {
		return milln;
	}

	/**
	 * @return the thoud
	 */
	public String getThoud() {
		return thoud;
	}

	/**
	 * @return the hund
	 */
	public String getHund() {
		return hund;
	}

	/**
	 * @return the and
	 */
	public String getAnd() {
		return and;
	}

	/**
	 * @return the billn
	 */
	public String getBilln() {
		return billn;
	}

	/**
	 * @return the intToWordMap
	 */
	public Map<String, String> getIntToWordMap() {
		return intToWordMap;
	}

	public static class Builder {

		private String milln;
		private String thoud;
		private String hund;
		private String and;
		private String billn;
		private Map<String, String> intToWordMap;

		public Builder withMilln(String milln) {
			this.milln = milln;
			return this;
		}

		public Builder withThoud(String thoud) {
			this.thoud = thoud;
			return this;
		}

		public Builder withHund(String hund) {
			this.hund = hund;
			return this;
		}

		public Builder withAnd(String and) {
			this.and = and;
			return this;
		}

		public Builder withBilln(String billn) {
			this.billn = billn;
			return this;
		}

		public Builder withIntToWordMap(Map<String, String> intToWordMap) {
			this.intToWordMap = intToWordMap;
			return this;
		}

		public NumericalLangMapping build() {
			validate();
			return new NumericalLangMapping(this);
		}

		private void validate() {
			Preconditions.checkArgument(!StringUtils.isBlank(milln), "milln may not be blank");
			Preconditions.checkArgument(!StringUtils.isBlank(thoud), "thoud may not be blank");
			Preconditions.checkArgument(!StringUtils.isBlank(hund), "hund may not be blank");
			Preconditions.checkArgument(!StringUtils.isBlank(and), "and may not be blank");
			Preconditions.checkArgument(!StringUtils.isBlank(billn), "billn may not be blank");

			Preconditions.checkArgument(!intToWordMap.isEmpty(), "intToWordMap may not be empty");
		}
	}

}
