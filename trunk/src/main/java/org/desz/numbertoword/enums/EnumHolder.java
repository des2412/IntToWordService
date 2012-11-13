/**
 * 
 */
package org.desz.numbertoword.enums;

import java.math.BigInteger;

/**
 * @author des
 * 
 *         EnumHolder: Aggregation of enums for language, integer to word
 *         mappings, Errors, Format and Units
 * 
 */
public final class EnumHolder {

	/**
	 * Enum encapsulating application level provisioned languages EMPTY is
	 * defined for UI HTML elements, e.g, SELECT
	 * 
	 * @author des
	 * 
	 */
	public enum PROVISIONED_LN {
		UK("UK", "UK ENGLISH", true), FR("FR", "FRENCH", true), EMPTY("EMPTY",
				"Select...", false);

		private String code;
		private String description;
		private boolean validOption;

		public boolean isValidOption() {
			return validOption;
		}

		public void setValidOption(boolean validOption) {
			this.validOption = validOption;
		}

		public void setValidChoice(boolean validOption) {
			this.validOption = validOption;
		}

		private PROVISIONED_LN(String code, String description,
				boolean validOption) {
			this.code = code;
			this.description = description;
			this.validOption = validOption;
		}

		public String getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}

	};

	/**
	 * Enum for number constants
	 * 
	 * @author des
	 * 
	 */
	public enum NUMBER_CONSTANT {

		MINUS_ONE, ZERO, TEN, ONE_HUNDRED, TWENTY;

		private NUMBER_CONSTANT() {

		}

		public BigInteger getBigInt() {
			switch (this) {
			case MINUS_ONE:
				return new BigInteger("-1");
			case ZERO:
				return BigInteger.ZERO;
			case TEN:
				return BigInteger.TEN;
			case TWENTY:
				return new BigInteger("20");
			case ONE_HUNDRED:
				return new BigInteger("100");
			default:
				break;
			}
			return null;
		}
	};

	public enum FR_WORDS {
		ZERO("0", "Zéro"), ONE("1", "Un"), TWO("2", "Deux"), THREE("3", "Trois"), FOUR(
				"4", "Quatre"), FIVE("5", "Cinq"), SIX("6", "Six"), SEVEN("7",
				"Sept"), EIGHT("8", "Huit"), NINE("9", "Neuf"), TEN("10", "Dix"), ELEVEN(
				"11", "Onze"), TWELVE("12", "Douze"), THIRTEEN("13", "Treize"), FOURTEEN(
				"14", "Quatorze"), FIFTEEN("15", "Quinze"), SIXTEEN("16",
				"Seize"), SEVENTEEN("17", "Dix-sept"), EIGHTEEN("18",
				"Dix-huit"), NINETEEN("19", "Dix-neuf"), TWENTY("20", "Vingt"), THIRTY(
				"30", "Trente"), FORTY("40", "Quarante"), FIFTY("50",
				"Cinquante"), SIXTY("60", "Soixante"), SEVENTY("70",
				"Soixante-dix"), EIGHTY("80", "Soixante-vingt"), NINETY("90",
				"Quarante-vingt-dix");

		private String word;
		private String num;

		private FR_WORDS(String num, String word) {
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

	public enum UK_WORDS {
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

		private UK_WORDS(String num, String word) {
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
	public enum UK_ERRORS {
		INVALID_INPUT_NUMBER("Cannot convert this positive number:"), FRACTIONAL_DISALLOWED(
				"Fractional numbers disallowed"), NULL_INPUT(
				"No number to convert"), UNKNOWN("Unknown error"), NEGATIVE_INPUT(
				"Negative integer input"), NUMBERFORMAT(
				"Number Format Exception"), LANGUAGE_NOTSELECTED(
				"Language not selected"), INVALID_LN_SEL(
				"Valid language not chosen");
		
		private final String error;

		UK_ERRORS(String error) {
			this.error = error;
		}

		public String getError() {
			return this.error;
		}
		
		public static String getValue(UK_ERRORS e){
			return e.getError();
		}

	};

	public enum FR_ERRORS {
		INVALID_INPUT_NUMBER("Impossible de convertir ce nombre positif:"), FRACTIONAL_DISALLOWED(
				"nombres fractionnaires a refusé"), NULL_INPUT(
				"Aucun numéro de convertir"), UNKNOWN("erreur inconnue"), NEGATIVE_INPUT(
				"L'entrée négative entier"), NUMBERFORMAT(
				"Exception Format de nombre"), LANGUAGE_NOTSELECTED(
				"Langue non pris en charge"), INVALID_LN_SEL(
				"Langue valide pas choisi");
		private String error;

		private FR_ERRORS(String error) {
			this.error = error;
		}

		public String getError() {
			return this.error;
		}

	};

	/**
	 * Units of formatted number
	 * 
	 * @author des
	 * 
	 */
	public enum UK_UNITS {
		MILLS("million"), THOUS("thousand"), HUNS("hundred");
		private String val;

		UK_UNITS(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

	public enum FR_UNITS {
		MILLS("million"), THOUS("mille"), HUNS("cent");
		private String val;

		FR_UNITS(String val) {
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
	public enum UK_FORMAT {
		AND(" and "), SPACE(" "), EMPTY(""), UKSEP(",");

		private String val;

		UK_FORMAT(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

	// TODO remove redundancies SPACE...
	public enum FR_FORMAT {
		AND(" et "), SPACE(" "), EMPTY(""), UKSEP(",");

		private String val;

		FR_FORMAT(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

}
