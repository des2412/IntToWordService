/**
 * 
 */
package org.desz.integertoword.exceptions;

/**
 * @author des
 *
 */
public class MongoDbException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MongoDbException() {
		super();

	}

	/**
	 * @param message
	 */
	public MongoDbException(String message) {
		super(message);

	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
