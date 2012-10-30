package org.desz.numbertoword;

import java.util.EnumMap;
import java.util.Map;

import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.UK_UNITS;
import org.desz.numbertoword.factory.NumberToWordFactory;

/**
 * Class is configured by NumberToWordFactory
 * @author des: des_williams_2000@yahoo.com
 * 
 */
public class NumberToWordMapper extends NumberToWordMapperParent {

	/**
	 * Constructor is private to enforce Singleton semantics
	 * 
	 * @see NumberToWordFactory
	 * @param languageSupport
	 *            specific text for target PROVISIONED_LANGUAGE
	 */
	private NumberToWordMapper(LanguageSupport languageSupport) {
		setLanguageSupport(languageSupport);
	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 *             if validate throws Exception type
	 */

	public String getWord(Integer num) throws Exception {

		String formattedNumber = null;
		// internationalisation variables
		// units
		String millUnit = getLanguageSupport().getMillUnit();
		String thouUnit = getLanguageSupport().getThouUnit();
		String and = getLanguageSupport().getAnd();
		//errors
		String invalidInput = getLanguageSupport().getInvalidInput();
		

		try {
			formattedNumber = validateAndFormat(num);
			LOGGER.info("Formatted Number:" + formattedNumber);

		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			throw (e);
		}

		LOGGER.info("Format Separator:" + getFormattedNumberSeparator());
		String[] components = formattedNumber
				.split(getFormattedNumberSeparator());
		final int nComps = components.length;
		if (nComps > 3) {
			LOGGER.info(invalidInput + num);
			setMessage(invalidInput + num);
			throw new Exception(invalidInput + num);
		}

		if (formattedNumber.equals("0")) {
			return numToWordMap.get("0");
		}

		String mills = UK_FORMAT.EMPTY.val();
		String thous = UK_FORMAT.EMPTY.val();
		String huns = UK_FORMAT.EMPTY.val();

		Map<UK_UNITS, Integer> numAtIndex = new EnumMap<UK_UNITS, Integer>(UK_UNITS.class);

		StringBuffer result = new StringBuffer();

		Integer val = null;
		if (nComps == 3) {

			val = Integer.valueOf(components[0]);
			mills = getWordForNum(val);
			numAtIndex.put(UK_UNITS.MILLS, val);

			val = Integer.valueOf(components[1]);
			thous = getWordForNum(val);
			numAtIndex.put(UK_UNITS.THOUS, val);

			val = Integer.valueOf(components[2]);
			huns = getWordForNum(val);
			numAtIndex.put(UK_UNITS.HUNS, val);

		} else if (nComps == 2) {
			val = Integer.valueOf(components[0]);
			thous = getWordForNum(val);
			numAtIndex.put(UK_UNITS.MILLS, -1);
			numAtIndex.put(UK_UNITS.THOUS, val);

			val = Integer.valueOf(components[1]);
			huns = getWordForNum(val);
			numAtIndex.put(UK_UNITS.HUNS, val);
		} else {
			val = Integer.valueOf(components[0]);
			huns = getWordForNum(val);
			numAtIndex.put(UK_UNITS.MILLS, -1);
			numAtIndex.put(UK_UNITS.THOUS, -1);
			numAtIndex.put(UK_UNITS.HUNS, val);
		}

		// Concatenate the units of the number

		if (numAtIndex.get(UK_UNITS.MILLS) > 0) {
			String mn = mills + UK_FORMAT.SPACE.val() + millUnit;
			result.append(mn);
		}
		if (numAtIndex.get(UK_UNITS.THOUS) > 0) {
			if (mills == UK_FORMAT.EMPTY.val()) {
				result.append(thous + UK_FORMAT.SPACE.val() + thouUnit);
			} else if (numAtIndex.get(UK_UNITS.THOUS) < 100) {
				result.append(and + thous.toLowerCase() + UK_FORMAT.SPACE.val()
						+ thouUnit);

			} else {
				result.append(UK_FORMAT.SPACE.val() + thous.toLowerCase()
						+ UK_FORMAT.SPACE.val() + thouUnit);
			}
		}
		if (numAtIndex.get(UK_UNITS.HUNS) > 0) {
			if (numAtIndex.get(UK_UNITS.THOUS) < 0
					& numAtIndex.get(UK_UNITS.MILLS) < 0) {
				result.append(huns);
			} else {
				if (numAtIndex.get(UK_UNITS.HUNS) < 100) {
					result.append(and + huns.toLowerCase());
				} else {
					result.append(UK_FORMAT.SPACE.val() + huns.toLowerCase());
				}
			}
		}

		return result.toString();
	}

	public void setMapping(Map<String, String> numToWordMap) {
		this.numToWordMap = numToWordMap;
		
	}

}
