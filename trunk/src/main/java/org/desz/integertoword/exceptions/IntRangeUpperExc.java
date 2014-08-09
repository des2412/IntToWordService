/**
 * 
 */
package org.desz.integertoword.exceptions;

/**
 * @author des
 * 
 */
public class IntRangeUpperExc extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntRangeUpperExc() {
		super();
	}

	public IntRangeUpperExc(String message, Throwable cause) {
		super(message, cause);
	}

	public IntRangeUpperExc(String message) {
		super(message);
	}

	public IntRangeUpperExc(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
