/**
 * 
 */
package org.desz.numbertoword.exceptions;

/**
 * @author des
 *
 */
public class IntToWordExc extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public IntToWordExc() {

	}

	/**
	 * @param message
	 */
	public IntToWordExc(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public IntToWordExc(Throwable cause) {
		super(cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IntToWordExc(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IntToWordExc(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
	
	@Override
	public String getMessage(){
		return super.getMessage();
	}

}
