/**
 * 
 */
package org.desz.numbertoword.exceptions;

/**
 * @author des
 *
 */
public class AppConversionException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public AppConversionException() {
		super();
	}

	public AppConversionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AppConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppConversionException(String message) {
		super(message);
	}

	public AppConversionException(Throwable cause) {
		super(cause);
	}

}
