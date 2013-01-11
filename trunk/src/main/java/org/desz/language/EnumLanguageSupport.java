package org.desz.language;

import java.util.HashMap;
import java.util.Map;

import org.desz.numbertoword.enums.EnumHolder.DEF_FMT;
import org.desz.numbertoword.enums.EnumHolder.DE_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.DE_FMT;
import org.desz.numbertoword.enums.EnumHolder.DE_WORDS;
import org.desz.numbertoword.enums.EnumHolder.FR_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.FR_FMT;
import org.desz.numbertoword.enums.EnumHolder.FR_WORDS;
import org.desz.numbertoword.enums.EnumHolder.NL_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.NL_FMT;
import org.desz.numbertoword.enums.EnumHolder.NL_WORDS;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.UK_WORDS;

import com.google.common.collect.ImmutableMap;

/**
 * Class that holds the enum value constants for a prov_lang
 * 
 * Immutable
 * 
 * switches on PROV_LANG
 * 
 * Used by IntegerToWordEnumFactory.
 * 
 * One-to-one relationship with NumberToWordMapper
 * 
 * @author des
 * 
 */
public final class EnumLanguageSupport implements ILanguageSupport {

	private String millUnit;
	private String thouUnit;
	private String hunUnit;
	private String and;

	private String invalidInput;

	private String nullInput;
	private String negativeInput;
	private String numberFormatErr;
	private String unknownErr;

	private Map<String, String> intToWordMap = new HashMap<String, String>();

	/**
	 * 
	 * Enum Factory instance configuration for specific languages.
	 * 
	 * 
	 * @param pl
	 *            PROV_LANG
	 */
	public EnumLanguageSupport(final PROV_LANG pl) {

		switch (pl) {
		case UK:
			this.millUnit = DEF_FMT.MILLS.val();
			this.thouUnit = DEF_FMT.THOUS.val();
			this.hunUnit = DEF_FMT.HUNS.val();
			this.and = DEF_FMT.AND.val();
			this.invalidInput = UK_ERRORS.INVALID_INPUT.getError();
			this.nullInput = UK_ERRORS.NULL_INPUT.getError();
			this.negativeInput = UK_ERRORS.NEGATIVE_INPUT.getError();
			this.negativeInput = UK_ERRORS.NUMBERFORMAT.getError();
			this.unknownErr = UK_ERRORS.UNKNOWN.getError();

			for (UK_WORDS intToWord : UK_WORDS.values()) {
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}

			break;
		case FR:
			this.millUnit = FR_FMT.MILLS.val();
			this.thouUnit = FR_FMT.THOUS.val();
			this.hunUnit = FR_FMT.HUNS.val();
			this.and = FR_FMT.AND.val();
			this.invalidInput = FR_ERRORS.INVALID_INPUT.getError();
			this.nullInput = FR_ERRORS.NULL_INPUT.getError();
			this.negativeInput = FR_ERRORS.NEGATIVE_INPUT.getError();
			this.negativeInput = FR_ERRORS.NUMBERFORMAT.getError();
			this.unknownErr = FR_ERRORS.UNKNOWN.getError();
			for (FR_WORDS intToWord : FR_WORDS.values()) {
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}

			break;

		case DE:
			this.millUnit = DE_FMT.MILLS.val();
			this.thouUnit = DE_FMT.THOUS.val();
			this.hunUnit = DE_FMT.HUNS.val();
			this.and = DE_FMT.AND.val();
			this.invalidInput = DE_ERRORS.INVALID_INPUT.getError();
			this.nullInput = DE_ERRORS.NULL_INPUT.getError();
			this.negativeInput = DE_ERRORS.NEGATIVE_INPUT.getError();
			this.negativeInput = DE_ERRORS.NUMBERFORMAT.getError();
			this.unknownErr = DE_ERRORS.UNKNOWN.getError();
			for (DE_WORDS intToWord : DE_WORDS.values()) {
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}
			break;

		case NL:
			this.millUnit = NL_FMT.MILLS.val();
			this.thouUnit = NL_FMT.THOUS.val();
			this.hunUnit = NL_FMT.HUNS.val();
			this.and = NL_FMT.AND.val();
			this.invalidInput = NL_ERRORS.INVALID_INPUT.getError();
			this.nullInput = NL_ERRORS.NULL_INPUT.getError();
			this.negativeInput = NL_ERRORS.NEGATIVE_INPUT.getError();
			this.negativeInput = NL_ERRORS.NUMBERFORMAT.getError();
			this.unknownErr = NL_ERRORS.UNKNOWN.getError();
			for (NL_WORDS intToWord : NL_WORDS.values()) {
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());
			}
			break;

		default:
			break;

		}
	}

	/**
	 * return an immutable Google Map of integer to word for 
	 * the language
	 */
	public ImmutableMap<String, String> getIntToWordMap() {

		ImmutableMap<String, String> immutable = new ImmutableMap.Builder<String, String>()
				.putAll(intToWordMap).build();
		return immutable;
	}

	public String getNegativeInput() {
		return negativeInput;
	}

	public String getInvalidInput() {
		return invalidInput;
	}

	public String getHunUnit() {
		return hunUnit;
	}

	public String getMillUnit() {
		return millUnit;
	}

	public String getThouUnit() {
		return thouUnit;
	}

	
	public String getAnd() {
		return and;
	}

	public String getNullInput() {
		return nullInput;
	}

	public String getNumberFormatErr() {
		return numberFormatErr;
	}

	public String getUnkownErr() {
		return unknownErr;
	}

}
