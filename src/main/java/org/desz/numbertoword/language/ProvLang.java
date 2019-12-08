package org.desz.numbertoword.language;

/**
 * ProvLang for language.
 * 
 */
public enum ProvLang {
	EMPTY("EMPTY", "Select...", false), UK("UK", "UK-English", true), FR("FR", "Français", true),
	DE("DE", "Deutsch", true), NL("NL", "Nederlandse", true);

	private String code;
	private String description;
	private boolean valid; /* the validation */

	public boolean isValid() {
		return valid;
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 * @param description
	 * @param valid
	 */
	private ProvLang(String code, String description, boolean valid) {
		this.code = code;
		this.description = description;
		this.valid = valid;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}