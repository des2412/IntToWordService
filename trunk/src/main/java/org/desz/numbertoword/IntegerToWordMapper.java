package org.desz.numbertoword;

import java.text.NumberFormat;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import org.desz.numbertoword.enums.EnumHolder.FR_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.UK_UNITS;
import org.desz.numbertoword.factory.NumberToWordFactory;

/**
 * Class is configured by NumberToWordFactory
 * @author des: des_williams_2000@yahoo.com
 * 
 */
public class IntegerToWordMapper extends NumberToWordMapper implements INumberToWordMapper{

	private static NumberFormat integerFormatter = NumberFormat.getIntegerInstance(Locale.UK);
	
	/**
	 * Constructor is private to enforce Singleton semantics
	 * 
	 * @see NumberToWordFactory
	 * @param languageSupport
	 *            specific text for target PROVISIONED_LANGUAGE
	 */
	private IntegerToWordMapper(LanguageSupport languageSupport) {
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
			mills = getWordForInt(val);
			numAtIndex.put(UK_UNITS.MILLS, val);

			val = Integer.valueOf(components[1]);
			thous = getWordForInt(val);
			numAtIndex.put(UK_UNITS.THOUS, val);

			val = Integer.valueOf(components[2]);
			huns = getWordForInt(val);
			numAtIndex.put(UK_UNITS.HUNS, val);

		} else if (nComps == 2) {
			val = Integer.valueOf(components[0]);
			thous = getWordForInt(val);
			numAtIndex.put(UK_UNITS.MILLS, -1);
			numAtIndex.put(UK_UNITS.THOUS, val);

			val = Integer.valueOf(components[1]);
			huns = getWordForInt(val);
			numAtIndex.put(UK_UNITS.HUNS, val);
		} else {
			val = Integer.valueOf(components[0]);
			huns = getWordForInt(val);
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
	
	/**
	 * validate and formats num
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 */
	protected String validateAndFormat(Number num) throws Exception {

		if (num == null) {
			LOGGER.info(FR_ERRORS.NULL_INPUT.val());
			setMessage(FR_ERRORS.NULL_INPUT.val());
			throw new Exception(FR_ERRORS.NULL_INPUT.val());
		}

		if ((Integer) num < 0) {
			LOGGER.info(FR_ERRORS.NEGATIVE_INPUT.val());
			setMessage(FR_ERRORS.NEGATIVE_INPUT.val());
			throw new Exception(FR_ERRORS.NEGATIVE_INPUT.val());
		}

		String formattedNumber = null;

		try {
			formattedNumber = convertToNumberFormat(Long.valueOf(String
					.valueOf(num)));
		} catch (NumberFormatException nfe) {
			setMessage(FR_ERRORS.NUMBERFORMAT.val());
			throw new Exception(FR_ERRORS.NUMBERFORMAT.val());
		}
		return formattedNumber;
	}


	

	/**
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 */
	protected String getWordForInt(Integer num) throws Exception {
		String numStr = String.valueOf(num);
		String indZero;
		String indOne;
		String indTwo;
		String result = null;
		Integer rem = null;
		switch (numStr.length()) {
		// 1,2 or 3 digits
		case 1:
			result = numToWordMap.get(numStr);
			break;
		case 2:
			indZero = String.valueOf(numStr.charAt(0));
			indOne = String.valueOf(numStr.charAt(1));
			rem = num % 10;

			if (num > 9 & num < 20) {// teenager
				result = numToWordMap.get(indZero + indOne);
			} else { // >= 20 & <= 99
				result = getDecimalPart(rem, indZero, indOne);
			}
			break;
		case 3:
			indZero = String.valueOf(numStr.charAt(0));
			indOne = String.valueOf(numStr.charAt(1));
			indTwo = String.valueOf(numStr.charAt(2));

			rem = num % 100;
			result = numToWordMap.get(indZero) + " "
					+ getLanguageSupport().getHunUnit();
			if (rem > 0) { // not whole hundredth
				String decs = getDecimalPart(rem, indOne, indTwo);
				result += getLanguageSupport().getAnd() + decs.toLowerCase();
			}
			break;

		default:
			setMessage(FR_ERRORS.NUMBERFORMAT.val());
			throw new Exception(FR_ERRORS.UNKNOWN.val());

		}

		return result;
	}

	/**
	 * 
	 * @param rem
	 * @param indZero
	 * @param indOne
	 * @return
	 */
	private String getDecimalPart(Integer rem, String indZero, String indOne) {
		String result;
		int decs = Integer.valueOf(indZero + indOne);

		if (rem == 0 | decs > 10 & decs < 20) {// teen
			result = numToWordMap.get(indZero + indOne);

		} else if (indZero.equals("0")) {
			result = numToWordMap.get(indOne);

		} else { // 20-99
			indZero += "0"; // add "0" to indZero so as to match key of whole
							// ten
			LOGGER.info("indZero:" + indZero + "indOne:" + indOne);
			if (!indOne.equals("0")) {
				result = numToWordMap.get(indZero) + UK_FORMAT.SPACE.val()
						+ numToWordMap.get(indOne);
			} else {
				result = numToWordMap.get(indZero);
			}
		}
		return result;
	}

	/**
	 * The formatted String that results has 1 to 3 parts.
	 * 
	 * @param num
	 * @return formatted String representation of num
	 */
	private String convertToNumberFormat(Long num)
			throws IllegalArgumentException {
		String s = null;
		try {
			s = integerFormatter.format(num);
		} catch (IllegalArgumentException e) {
			throw (e);
		}
		LOGGER.info("Formatted Number:" + s);
		return s;
	}

}
