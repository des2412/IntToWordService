/**
 * 
 */
package org.desz.numbertoword.enums;

/**
 * @author des
 * 
 *         enums for int to word mappings, Errors, Format and Units
 * 
 */
public class EnumHolder {

	public enum UKINTEGERTOWORD {
		ZERO("0", "Zero"), ONE("1", "One"), TWO("2", "Two"), THREE("3", "Three"), FOUR(
				"4", "Four"), FIVE("5", "Five"), SIX("6", "Six"), SEVEN("7",
				"Seven"), EIGHT("8", "Eight"), NINE("9", "Nine"), TEN("10",
				"Ten"), ELEVEN("11", "Eleven"), TWELVE("12", "Twelve"), THIRTEEN(
				"13", "Thirteen"), FOURTEEN("14", "Fourteen"), FIFTEEN("15",
				"Fifteen"), SIXTEEN("16", "Sixteen"), SEVENTEEN("17",
				"Seventeen"), EIGHTEEN("18", "Eighteen"), NINETEEN("19",
				"Nineteen"), TWENTY("20", "Twenty"), THIRTY("30", "Thirty"), FORTY(
				"40", "Forty"), FIFTY("50", "Fifty"), SIXTY("60", "Sixty"), SEVENTY(
				"70", "Seventy"), EIGHTY("80", "Eighty"), NINETY("90", "Ninety");

		private String word;
		private String num;

		private UKINTEGERTOWORD(String num, String word) {
			this.num = num;
			this.word = word;
		}

		public String getWord() {
			return word;
		}

		public String getNum() {
			return num;
		}

	};

	/**
	 * Errors possible during conversion
	 * 
	 * @author des
	 * 
	 */
	public enum ERRORS {
		INVALID_INPUT_NUMBER("Cannot convert this positive number:"), NULL_INPUT(
				"No number to convert"), UNKNOWN("Unknown error"), NEGATIVE_INPUT(
				"Negative integer input"), NUMBERFORMAT(
				"Number Format Exception");
		private String val;

		ERRORS(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

	/**
	 * Units of formatted number
	 * 
	 * @author des
	 * 
	 */
	public enum UNITS {
		MILLS("million"), THOUS("thousand"), HUNS("hundred");
		private String val;

		UNITS(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

	/**
	 * Format help for String representation of converted number
	 * 
	 * @author des
	 * 
	 */
	public enum FORMAT {
		AND(" and "), SPACE(" "), EMPTY("");

		private String val;

		FORMAT(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

}
