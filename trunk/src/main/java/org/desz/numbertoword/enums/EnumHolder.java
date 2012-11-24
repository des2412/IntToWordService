/**
 * 
 */
package org.desz.numbertoword.enums;

import java.math.BigInteger;

import org.desz.numbertoword.factory.IntegerToWordEnumFactory;

/**
 * @author des
 * 
 *         EnumHolder: Enums for language, integer to word mappings, Errors,
 *         Format and Units
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
		UK("UK", "UK ENGLISH", true), FR("FR", "FRANÇAIS", true), DE("DE",
				"DEUTSCH", true), NL("NL", "NEDERLANDSE", true), EMPTY("EMPTY",
				"Select...", false);

		private String code;
		private String description;
		private boolean valid;

		public boolean isValid() {
			return valid;
		}

		/**
		 * Constructor
		 * 
		 * @param code
		 * @param description
		 * @param valid
		 */
		private PROVISIONED_LN(String code, String description, boolean valid) {
			this.code = code;
			this.description = description;
			this.valid = valid;
		}

		public String getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}

	};

	/**
	 * Enum for BigInteger constants
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

	/**
	 * XX_WORDS specific mappings.
	 * 
	 * The Factory will set the appropriate mapping.
	 * 
	 * @see IntegerToWordEnumFactory
	 * 
	 * @author des
	 * 
	 */
	public enum NL_WORDS {
		ZERO("0", "Nul"), ONE("1", "Een"), TWO("2", "Twee"), THREE("3", "Drei"), FOUR(
				"4", "Vier"), FIVE("5", "Vijf"), SIX("6", "Zes"), SEVEN("7",
				"Zeven"), EIGHT("8", "Acht"), NINE("9", "Negen"), TEN("10",
				"Tien"), ELEVEN("11", "Elf"), TWELVE("12", "Twaalf"), THIRTEEN(
				"13", "Dertein"), FOURTEEN("14", "Veertien"), FIFTEEN("15",
				"Vijftien"), SIXTEEN("16", "Zestien"), SEVENTEEN("17",
				"Zeventien"), EIGHTEEN("18", "Achttien"), NINETEEN("19",
				"Negentien"), TWENTY("20", "Twintig"), TWENTYONE("21",
				"Eenentwintig"), TWENTYTWO("22", "Tweeëntwintig"), TWENTYTHREE(
				"23", "Dreiëntwintig"), TWENTYFOUR("24", "Vierentwintig"), TWENTYFIVE(
				"25", "Vijfentwintig"), TWENTYSIX("26", "Zesentwintig"), TWENTYSEVEN(
				"27", "Zevenentwintig"), TWENTYEIGHT("28", "Achtentwintig"), TWENTYNINE(
				"29", "Negenentwintig"), THIRTY("30", "Dertig"), FORTY("40",
				"Veertig"), FIFTY("50", "Vijftig"), SIXTY("60", "Zestig"), SEVENTY(
				"70", "Zeventig"), EIGHTY("80", "Tachtig"), NINETY("90",
				"Negentig");

		private String word;
		private String num;

		private NL_WORDS(String num, String word) {
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

	public enum DE_WORDS {
		ZERO("0", "Null"), ONE("1", "Eins"), TWO("2", "Zwei"), THREE("3",
				"Drei"), FOUR("4", "Vier"), FIVE("5", "Fünf"), SIX("6", "Sechs"), SEVEN(
				"7", "Sieben"), EIGHT("8", "Acht"), NINE("9", "Neun"), TEN(
				"10", "Zehn"), ELEVEN("11", "Elf"), TWELVE("12", "Zwölf"), THIRTEEN(
				"13", "Dreizehn"), FOURTEEN("14", "Vierzehn"), FIFTEEN("15",
				"Fünfzehn"), SIXTEEN("16", "Sechzehn"), SEVENTEEN("17",
				"Siebzehn"), EIGHTEEN("18", "Achtzehn"), NINETEEN("19",
				"Nehnzehn"), TWENTY("20", "Zwanzig"), TWENTYONE("21",
				"Einundzwanzig"), TWENTYTWO("22", "Zweiundzwanzig"), TWENTYTHREE(
				"23", "Dreiundzwanzig"), TWENTYFOUR("24", "Vierundzwanzig"), TWENTYFIVE(
				"25", "Fünfundzwanzig"), TWENTYSIX("26", "Sechsundzwanzig"), TWENTYSEVEN(
				"27", "Siebenundzwanzig"), TWENTYEIGHT("28", "Achtundzwanzig"), TWENTYNINE(
				"29", "Neunundzwanzig"), THIRTY("30", "Dreißig"), FORTY("40",
				"Vierzig"), FIFTY("50", "Fünfzig"), SIXTY("60", "Sechzig"), SEVENTY(
				"70", "Siebzig"), EIGHTY("80", "Achtzig"), NINETY("90",
				"Neunzig");

		private String word;
		private String num;

		private DE_WORDS(String num, String word) {
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
	 * French integer to word
	 * 
	 * @author des
	 * 
	 */
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
	 * Errors possible during conversion.
	 * 
	 * Languages: UK, FR, DE, NL
	 * 
	 * UK is default if invalid language is selected.
	 * 
	 * Each XX_ERRORS Enum is a discrete set with language dependent values.
	 * 
	 * @author des
	 * 
	 */
	public enum UK_ERRORS {
		INVALID_INPUT("Invalid input"), INVALID_NUMBER(
				"Fractional or non integral numbers disallowed"), NULL_INPUT(
				"No number to convert"), UNKNOWN("Unknown error"), NEGATIVE_INPUT(
				"Negative integer input"), NUMBERFORMAT(
				"Number Format Exception"), INVALID_LN_SEL(
				"Valid language not chosen");

		private final String error;

		UK_ERRORS(String error) {
			this.error = error;
		}

		public String getError() {
			return this.error;
		}

	};

	public enum FR_ERRORS {
		INVALID_INPUT("Invalid d'entrée"), INVALID_NUMBER(
				"Fractionnés ou non nombres entiers a refusé"), NULL_INPUT(
				"Aucun numéro de convertir"), UNKNOWN("erreur inconnue"), NEGATIVE_INPUT(
				"L'entrée négative entier"), NUMBERFORMAT(
				"Exception Format de nombre"), INVALID_LN_SEL(
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
	 * German Error messages
	 * 
	 * @author des
	 * 
	 */
	public enum DE_ERRORS {
		INVALID_INPUT("ungültige Eingabe"), INVALID_NUMBER(
				"Gebrochene oder nicht ganze Zahlen aberkannt"), NULL_INPUT(
				"null-Eingang"), UNKNOWN("unbekannter Fehler"), NEGATIVE_INPUT(
				"Minuseingang"), NUMBERFORMAT("Zahlenformat Ausnahme"), INVALID_LN_SEL(
				"ungültige Auswahl der Sprache");
		private String error;

		private DE_ERRORS(String error) {
			this.error = error;
		}

		public String getError() {
			return this.error;
		}

	};

	/**
	 * NL error messages
	 * 
	 * @author des
	 * 
	 */
	public enum NL_ERRORS {
		INVALID_INPUT("Ongeldige Invoer"), INVALID_NUMBER(
				"Fractionele of niet-gehele getallen niet toegestaan"), NULL_INPUT(
				"null-Eingang"), UNKNOWN("Onbekende Fout"), NEGATIVE_INPUT(
				"Negatieve Ingang"), NUMBERFORMAT("Getalnotatie Uitzondering"), INVALID_LN_SEL(
				"Ongeldig Taalkeuze");
		private String error;

		private NL_ERRORS(String error) {
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
	/*
	 * public enum UK_UNITS { MILLS("million"), THOUS("thousand"),
	 * HUNS("hundred"); private String val;
	 * 
	 * UK_UNITS(String val) { this.val = val; }
	 * 
	 * public String val() { return val; }
	 * 
	 * };
	 * 
	 * public enum FR_UNITS { MILLS("million"), THOUS("mille"), HUNS("cent");
	 * private String val;
	 * 
	 * FR_UNITS(String val) { this.val = val; }
	 * 
	 * public String val() { return val; }
	 * 
	 * };
	 */

	/*
	 * public enum DE_UNITS {
	 * 
	 * MILLS("million"), THOUS("tausend"), HUNS("hundert");
	 * 
	 * private String val;
	 * 
	 * DE_UNITS(String val) { this.val = val; }
	 * 
	 * public String val() { return val; } }
	 */

	/*
	 * public enum NL_UNITS {
	 * 
	 * MILLS("miljeon"), THOUS("duizend"), HUNS("honderd");
	 * 
	 * private String val;
	 * 
	 * NL_UNITS(String val) { this.val = val; }
	 * 
	 * public String val() { return val; } }
	 */

	/**
	 * Format help for String representation of converted number
	 * 
	 * @author des
	 * 
	 */
	public enum DEF_FMT {

		SPACE(" "), AND(SPACE.val() + "and" + SPACE.val()), EMPTY(""), NUM_SEP(
				","), MILLS("million"), THOUS("thousand"), HUNS("hundred");

		private String val;

		DEF_FMT(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

	public enum FR_FMT {
		AND(DEF_FMT.SPACE.val() + "et" + DEF_FMT.SPACE.val()), MILLS("million"), THOUS(
				"mille"), HUNS("cent");

		private String val;

		FR_FMT(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}

	};

	public enum DE_FMT {

		AND(DEF_FMT.SPACE.val() + "und" + DEF_FMT.SPACE.val()), MILLS("million"), THOUS(
				"tausend"), HUNS("hundert");

		private String val;

		DE_FMT(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}
	};

	public enum NL_FMT {

		AND(DEF_FMT.SPACE.val() + "en" + DEF_FMT.SPACE.val()), MILLS("miljeon"), THOUS(
				"duizend"), HUNS("honderd");

		private String val;

		NL_FMT(String val) {
			this.val = val;
		}

		public String val() {
			return val;
		}
	};

}
