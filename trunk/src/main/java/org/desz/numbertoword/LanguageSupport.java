package org.desz.numbertoword;

import org.desz.numbertoword.enums.EnumHolder.FR_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.FR_UNITS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.UK_UNITS;

public class LanguageSupport {

	private String millUnit;
	private String thouUnit;
	private String hunUnit;
	private String and;
	private PROVISIONED_LANGUAGE provisionedLanguage;

	public LanguageSupport(PROVISIONED_LANGUAGE pl) {
		this.provisionedLanguage = pl;
		switch (pl) {
		case UK:
			this.millUnit = UK_UNITS.MILLS.val();
			this.thouUnit = UK_UNITS.THOUS.val();
			this.hunUnit = UK_UNITS.HUNS.val();
			this.and = UK_FORMAT.AND.val();

			break;
		case FR:
			this.millUnit = FR_UNITS.MILLS.val();
			this.thouUnit = FR_UNITS.THOUS.val();
			this.hunUnit = FR_UNITS.HUNS.val();
			this.and = FR_FORMAT.AND.val();
			break;

		default:
			break;

		}
	}

	public PROVISIONED_LANGUAGE getProvisionedLanguage() {
		return provisionedLanguage;
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

}
