package org.desz.numbertoword;

import java.util.HashMap;
import java.util.Map;

import org.desz.numbertoword.enums.EnumHolder.ERRORS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.UK_UNITS;

/**
 * 
 * @author des: des_williams_2000@yahoo.com
 * 
 */
public class NumberToWordMapper extends NumberToWordBase {

	/**
	 * Constructor
	 */
	private NumberToWordMapper(PROVISIONED_LANGUAGE pl) {
		setProvisionedLanguage(pl);
		initialiseMapping();
		// all numbers will be converted to UK format
		setFormattedNumberSeparator(UK_FORMAT.UKSEP.val());
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
			LOGGER.info(ERRORS.INVALID_INPUT_NUMBER.val() + num);
			setMessage(ERRORS.INVALID_INPUT_NUMBER.val() + num);
			throw new Exception(ERRORS.INVALID_INPUT_NUMBER.val() + num);
		}

		if (formattedNumber.equals("0")) {
			return numToWordMap.get("0");
		}

		String mills = UK_FORMAT.EMPTY.val();
		String thous = UK_FORMAT.EMPTY.val();
		String huns = UK_FORMAT.EMPTY.val();

		Map<UK_UNITS, Integer> numAtIndex = new HashMap<UK_UNITS, Integer>();

		StringBuffer result = new StringBuffer();

		String millUnit = getLanguageSupport().getMillUnit();
		String thouUnit = getLanguageSupport().getThouUnit();
		String and = getLanguageSupport().getAnd();

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

}
