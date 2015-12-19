/**
 * 
 */
package org.desz.inttoword.exceptions;

/**
 * @author des
 *
 */
public class IntToWordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public IntToWordException() {

	}

	/**
	 * @param message
	 */
	public IntToWordException(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public IntToWordException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IntToWordException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
//	public IntToWordException(String message, Throwable cause,
//			boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//		
//	}
	
	@Override
	public String getMessage(){
		return super.getMessage();
	}

}
