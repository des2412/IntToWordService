package org.desz.inttoword.language;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.desz.inttoword.language.LangContent.DEF;
import org.desz.inttoword.language.LangContent.DE_FMT;
import org.desz.inttoword.language.LangContent.DE_WORDS;
import org.desz.inttoword.language.LangContent.FR_FMT;
import org.desz.inttoword.language.LangContent.FR_WORDS;
import org.desz.inttoword.language.LangContent.NL_FMT;
import org.desz.inttoword.language.LangContent.NL_WORDS;
import org.desz.inttoword.language.LangContent.PROV_LANG;
import org.desz.inttoword.language.LangContent.UK_WORDS;
import org.desz.inttoword.mapper.Int2StrConverter;

import com.google.common.collect.ImmutableMap;

/**
 * Defines Strings for number constants for a PROV_LANG.
 * 
 * @see Int2StrConverter
 * 
 * @author des
 * 
 */
public final class ProvLangFactory implements ILangProvider {

	private String millUnit;
	private String thouUnit;
	private String hunUnit;
	private String and;
	private ImmutableMap<String, String> wordIntMapping;
	private String billUnit;

	/**
	 * 
	 * @param _lnId
	 *            PROV_LANG
	 */
	public ProvLangFactory(final PROV_LANG _lnId) {

		Map<String, String> intToWordMap = new HashMap<String, String>();
		switch (_lnId) {
		case UK:
			this.billUnit = DEF.BILLS.val();
			this.millUnit = DEF.MILLS.val();
			this.thouUnit = DEF.THOUS.val();
			this.hunUnit = DEF.HUNS.val();
			this.and = DEF.AND.val();
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

			for (FR_WORDS intToWord : FR_WORDS.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		case DE:
			this.billUnit = DE_FMT.BILLS.val();
			this.millUnit = DE_FMT.MILLS.val();
			this.thouUnit = DE_FMT.THOUS.val();
			this.hunUnit = DE_FMT.HUNS.val();
			this.and = DE_FMT.AND.val();
			for (DE_WORDS intToWord : DE_WORDS.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		case NL:
			this.billUnit = NL_FMT.BILLS.val();
			this.millUnit = NL_FMT.MILLS.val();
			this.thouUnit = NL_FMT.THOUS.val();
			this.hunUnit = NL_FMT.HUNS.val();
			this.and = NL_FMT.AND.val();
			for (NL_WORDS intToWord : NL_WORDS.values())
				intToWordMap.put(intToWord.getNum(), intToWord.getWord());

			break;

		default:
			break;

		}

		wordIntMapping = new ImmutableMap.Builder<String, String>().putAll(intToWordMap).build();
	}

	/**
	 * return the word for PROV_LN
	 */
	@Override
	public String getWord(String num) {
		Objects.requireNonNull(num);
		return wordIntMapping.get(num);
	}

	@Override
	public boolean containsWord(String num) {
		return wordIntMapping.containsKey(num);
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

	@Override
	public List<String> unitsList() {
		return Arrays.asList(this.getBillUnit(), this.getMillUnit(), this.getThouUnit(), StringUtils.EMPTY);
	}

}
