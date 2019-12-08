/**
 * 
 */
package org.desz.numbertoword.language;

/**
 * @author des
 *
 */
public enum Punct {
	SPC(" "), ERR_SEP(SPC.val() + "/" + SPC.val());

	private String val;

	private Punct(String val) {
		this.val = val;
	}

	/**
	 * @return the val
	 */
	public String val() {
		return val;
	}

}
