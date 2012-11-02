package org.desz.numbertoword;

import org.desz.numbertoword.enums.EnumHolder.FR_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.FR_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.FR_UNITS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.enums.EnumHolder.UK_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.UK_UNITS;

/**
 * Class that holds the enum values determined by PROVISIONED_LANGUAGE
 * 
 * One-to-one relationship with NumberToWordMapper
 * 
 * @author des
 * 
 */
public final class LanguageSupport {

	private String millUnit;
	private String thouUnit;
	private String hunUnit;
	private String and;

	private String invalidInput;

	private PROVISIONED_LANGUAGE provisionedLanguage;
	private String nullInput;
	private String negativeInput;
	private String numberFormatErr;
	private String unknownErr;

	/**
	 * Construct state according to pl
	 * 
	 * @param pl
	 */
	public LanguageSupport(PROVISIONED_LANGUAGE pl) {
		this.provisionedLanguage = pl;
		switch (pl) {
		case UK:
			this.millUnit = UK_UNITS.MILLS.val();
			this.thouUnit = UK_UNITS.THOUS.val();
			this.hunUnit = UK_UNITS.HUNS.val();
			this.and = UK_FORMAT.AND.val();
			this.invalidInput = UK_ERRORS.INVALID_INPUT_NUMBER.val();
			this.nullInput = UK_ERRORS.NULL_INPUT.val();
			this.negativeInput = UK_ERRORS.NEGATIVE_INPUT.val();
			this.negativeInput = UK_ERRORS.NUMBERFORMAT.val();
			this.unknownErr = UK_ERRORS.UNKNOWN.val();
			break;
		case FR:
			this.millUnit = FR_UNITS.MILLS.val();
			this.thouUnit = FR_UNITS.THOUS.val();
			this.hunUnit = FR_UNITS.HUNS.val();
			this.and = FR_FORMAT.AND.val();
			this.invalidInput = FR_ERRORS.INVALID_INPUT_NUMBER.val();
			this.nullInput = FR_ERRORS.NULL_INPUT.val();
			this.negativeInput = FR_ERRORS.NEGATIVE_INPUT.val();
			this.negativeInput = FR_ERRORS.NUMBERFORMAT.val();
			this.unknownErr = FR_ERRORS.UNKNOWN.val();
			break;

		default:
			break;

		}
	}

	public String getNegativeInput() {
		return negativeInput;
	}

	public PROVISIONED_LANGUAGE getProvisionedLanguage() {
		return provisionedLanguage;
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
		return this.nullInput;
	}

	public String getNumberFormatErr() {
		return this.numberFormatErr;
	}

	public String getUnkownErr() {
		return this.unknownErr;
	}

}
