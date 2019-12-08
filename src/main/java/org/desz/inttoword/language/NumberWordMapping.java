/**
 * 
 */
package org.desz.inttoword.language;

import java.util.Map;
import java.util.Optional;

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
public class NumberWordMapping {
	private String id;
	private String quintn;
	private String quadrn;
	private String trilln;
	private String billn;
	private String milln;
	private String thoud;
	private String hund;
	private String and;

	private Map<String, String> map;

	public boolean containsMapping(int num) {
		return map.containsKey(String.valueOf(num));
	}

	/**
	 * @return word mapped to num or empty String.
	 */
	public Optional<String> wordForNum(int num) {
		return map.containsKey(String.valueOf(num)) ? Optional.of(map.get(String.valueOf(num))) : Optional.empty();
	}

}
