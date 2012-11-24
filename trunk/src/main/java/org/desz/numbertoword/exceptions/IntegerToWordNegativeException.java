/**
 * 
 */
package org.desz.numbertoword.exceptions;

/**
 * @author des
 * 
 */
public class IntegerToWordNegativeException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private IntegerToWordNegativeException() {
		super();
	}

	public IntegerToWordNegativeException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
