package org.desz.inttoword.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.desz.inttoword.content.LangContent.DEF;
import org.desz.inttoword.content.LangContent.DE_ERRORS;
import org.desz.inttoword.content.LangContent.DE_FMT;
import org.desz.inttoword.content.LangContent.DE_WORDS;
import org.desz.inttoword.content.LangContent.FR_ERRORS;
import org.desz.inttoword.content.LangContent.FR_FMT;
import org.desz.inttoword.content.LangContent.FR_WORDS;
import org.desz.inttoword.content.LangContent.NL_ERRORS;
import org.desz.inttoword.content.LangContent.NL_FMT;
import org.desz.inttoword.content.LangContent.NL_WORDS;
import org.desz.inttoword.content.LangContent.PROV_LANG;
import org.desz.inttoword.content.LangContent.UK_ERRORS;
import org.desz.inttoword.content.LangContent.UK_WORDS;
import org.desz.inttoword.mapper.Converter;

import com.google.common.collect.ImmutableMap;

/**
 * Defines constants for a PROV_LANG; cache for language specific words.
 * 
 * @see Converter
 * 
 * @author des
 * 
 */
public final class ProvLangFac implements ILangProvider {

	private String millUnit;
	private String thouUnit;
	private String hunUnit;
	private String and;
	private ImmutableMap<String, String> map;
	private String billUnit;

	/**
	 * 
	 * @param _lnId
	 *            PROV_LANG
	 */
	public ProvLangFac(final PROV_LANG _lnId) {

		Map<String, String> intToWordMap = new HashMap<String, String>();
		switch (_lnId) {
		case UK:
			this.billUnit = DEF.BILLS.val();
			this.millUnit = DEF.MILLS.val();
			this.thouUnit = DEF.THOUS.val();
			this.hunUnit = DEF.HUNS.val();
			this.and = DEF.AND.val();
			UK_ERRORS.UNKNOWN.getError();
			// populate Map of int to word for UK English
			for (UK_WORDS intToWord : UK_WORDS.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;
		case FR:
			this.billUnit = FR_FMT.BILLS.val();
			this.millUnit = FR_FMT.MILLS.val();
			this.thouUnit = FR_FMT.THOUS.val();
			this.hunUnit = FR_FMT.HUNS.val();
			this.and = FR_FMT.AND.val();
			FR_ERRORS.UNKNOWN.getError();
			for (FR_WORDS intToWord : FR_WORDS.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		case DE:
			this.billUnit = DE_FMT.BILLS.val();
			this.millUnit = DE_FMT.MILLS.val();
			this.thouUnit = DE_FMT.THOUS.val();
			this.hunUnit = DE_FMT.HUNS.val();
			this.and = DE_FMT.AND.val();
			DE_ERRORS.UNKNOWN.getError();
			for (DE_WORDS intToWord : DE_WORDS.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		case NL:
			this.billUnit = NL_FMT.BILLS.val();
			this.millUnit = NL_FMT.MILLS.val();
			this.thouUnit = NL_FMT.THOUS.val();
			this.hunUnit = NL_FMT.HUNS.val();
			this.and = NL_FMT.AND.val();
			NL_ERRORS.UNKNOWN.getError();
			for (NL_WORDS intToWord : NL_WORDS.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		default:
			break;

		}

		map = new ImmutableMap.Builder<String, String>().putAll(intToWordMap).build();
	}

	/**
	 * return the word for PROV_LN
	 */
	@Override
	public String getWord(String num) {
		Objects.requireNonNull(num);
		if (map.containsKey(num))
			return map.get(num);
		return null;
	}

	@Override
	public boolean containsWord(String num) {
		Objects.requireNonNull(num);
		return map.containsKey(num);
	}

	@Override
	public String getHunUnit() {
		return hunUnit;
	}

	@Override
	public String getMillUnit() {
		return millUnit;
	}

	@Override
	public String getThouUnit() {
		return thouUnit;
	}

	@Override
	public String getAnd() {
		return and;
	}

	@Override
	public String getBillUnit() {
		return billUnit;
	}

}
