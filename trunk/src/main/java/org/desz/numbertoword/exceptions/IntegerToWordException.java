/**
 * 
 */
package org.desz.numbertoword.exceptions;

/**
 * @author des
 * 
 */
public class IntegerToWordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntegerToWordException() {
		super();
	}

	public IntegerToWordException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IntegerToWordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IntegerToWordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IntegerToWordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
