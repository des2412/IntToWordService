/**
 * 
 */
package org.desz.inttoword.language;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Map;

import lombok.Builder;
import lombok.Value;

/**
 * @author des
 * 
 *         Instance per language, NL..FR. ProvLangFactory maintains cache.
 *
 */

@Builder
@Value
public class IntWordMapping {

	private String milln;
	private String thoud;
	private String hund;
	private String and;
	private String billn;
	private Map<String, String> intToWordMap;

	/**
	 * @return word mapped to num or empty String.
	 */
	public String wordForNum(int num) {
		return intToWordMap.containsKey(String.valueOf(num)) ? intToWordMap.get(String.valueOf(num)) : EMPTY;
	}

}
