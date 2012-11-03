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

	public enum PROVISIONED_LANGUAGE {
		UK, FR;
		
		

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
		INVALID_INPUT_NUMBER("Cannot convert this positive number:"), NULL_INPUT(
				"No number to convert"), UNKNOWN("Unknown error"), NEGATIVE_INPUT(
				"Negative integer input"), NUMBERFORMAT(
				"Number Format Exception"), LANGUAGE_NOTSUPPORTED(
				"Language not supported");
		private String val;

		UK_ERRORS(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

	public enum FR_ERRORS {
		INVALID_INPUT_NUMBER("Impossible de convertir ce nombre positif:"), NULL_INPUT(
				"Aucun numéro de convertir"), UNKNOWN("erreur inconnue"), NEGATIVE_INPUT(
				"L'entrée négative entier"), NUMBERFORMAT(
				"Exception Format de nombre"), LANGUAGE_NOTSUPPORTED(
				"Langue non pris en charge");
		private String val;

		FR_ERRORS(String val) {
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
