package org.desz.numbertoword;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;

/**
 * Base class extended for Integer
 * Can be extended for other Number types
 * @author des
 *
 */
public abstract class NumberToWordMapper  {

	

	protected final static Logger LOGGER = Logger
			.getLogger(NumberToWordMapper.class.getName());

	private static final String FORMATTED_NUMBER_SEPARATOR = UK_FORMAT.UKSEP
			.val();

	private LanguageSupport languageSupport;
	
	/**
	 * set Level of Logging
	 * 
	 * @param newLevel
	 */
	protected static void setLoggingLevel(Level newLevel) {
		LOGGER.setLevel(newLevel);
	}

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

	/**
	 * message: Typically reports an error condition. Used by Unit tests for
	 * assertions only.
	 */
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}