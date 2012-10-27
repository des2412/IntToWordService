/**
 * 
 */
package org.desz.numbertoword.enums;

/**
 * @author des
 * 
 *         Class for enums for Errors, Format and Units
 * 
 */
public class EnumHolder {

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
