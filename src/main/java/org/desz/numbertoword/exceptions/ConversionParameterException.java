/**
 * 
 */
package org.desz.numbertoword.exceptions;

/**
 * @author des
 *
 */
public class ConversionParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ConversionParameterException() {

	}

	/**
	 * @param message
	 */
	public ConversionParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ConversionParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConversionParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ConversionParameterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
