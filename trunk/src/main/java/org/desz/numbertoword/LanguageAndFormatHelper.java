package org.desz.numbertoword;

import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;

/**
 * Base class extended for Integer Can be extended for other Number types
 * 
 * @author des
 * 
 */
public final class LanguageAndFormatHelper {

	private static final String FORMATTED_NUMBER_SEPARATOR = UK_FORMAT.UKSEP
			.val();

	private LanguageSupport languageSupport;

	/**
	 * 
	 * @return languageSupport
	 */
	public LanguageSupport getLanguageSupport() {
		return languageSupport;
	}

	public void setLanguageSupport(LanguageSupport languageSupport) {
		this.languageSupport = languageSupport;
	}

	public String getFormattedNumberSeparator() {
		return FORMATTED_NUMBER_SEPARATOR;
	}

}