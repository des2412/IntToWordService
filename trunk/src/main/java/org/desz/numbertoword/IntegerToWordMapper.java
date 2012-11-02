package org.desz.numbertoword;

import java.text.NumberFormat;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.UK_UNITS;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.factory.NumberToWordEnumFactory;

/**
 * Class is configured by NumberToWordFactory
 * 
 * @author des: des_williams_2000@yahoo.com
 * 
 */
public final class IntegerToWordMapper implements IFNumberToWordMapper {

	private LanguageAndFormatHelper languageAndFormatHelper;

	protected final static Logger LOGGER = Logger
			.getLogger(IntegerToWordMapper.class.getName());

	public Map<String, String> numToWordMap = null;

	private transient static NumberFormat integerFormatter = NumberFormat
			.getIntegerInstance(Locale.UK);

	/**
	 * message: Typically reports an error condition. Used by Unit tests for
	 * assertions only.
	 */
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Constructor is private to enforce Singleton semantics
	 * 
	 * @see NumberToWordEnumFactory
	 * @param languageSupport
	 *            specific text for target PROVISIONED_LANGUAGE
	 */
	private IntegerToWordMapper(LanguageAndFormatHelper languageAndFormatHelper) {
		this.languageAndFormatHelper = languageAndFormatHelper;
	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 *             if validate throws Exception type
	 */

	@Override
	public String getWord(Integer num) throws IntegerToWordException {

		String formattedNumber = null;
		try {
			formattedNumber = validateAndFormat(num);
			LOGGER.info("Formatted Number:" + formattedNumber);

		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			throw new IntegerToWordException(e);
		}

		LOGGER.info("Format Separator:"
				+ languageAndFormatHelper.getFormattedNumberSeparator());
		String[] components = formattedNumber.split(languageAndFormatHelper
				.getFormattedNumberSeparator());
		final int nComps = components.length;
		if (nComps > 3) {
			LOGGER.info(languageAndFormatHelper.getLanguageSupport()
					.getInvalidInput() + num);
			setMessage(languageAndFormatHelper.getLanguageSupport()
					.getInvalidInput() + num);
			throw new IntegerToWordException(languageAndFormatHelper
					.getLanguageSupport().getInvalidInput() + num);
		}

		if (formattedNumber.equals("0")) {
			return numToWordMap.get("0");
		}

		String mills = UK_FORMAT.EMPTY.val();
		String thous = UK_FORMAT.EMPTY.val();
		String huns = UK_FORMAT.EMPTY.val();

		Map<UK_UNITS, Integer> numAtIndex = new EnumMap<UK_UNITS, Integer>(
				UK_UNITS.class);

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
			String mn = mills
					+ UK_FORMAT.SPACE.val()
					+ languageAndFormatHelper.getLanguageSupport()
							.getMillUnit();
			result.append(mn);
		}
		if (numAtIndex.get(UK_UNITS.THOUS) > 0) {
			if (mills == UK_FORMAT.EMPTY.val()) {
				result.append(thous
						+ UK_FORMAT.SPACE.val()
						+ languageAndFormatHelper.getLanguageSupport()
								.getThouUnit());
			} else if (numAtIndex.get(UK_UNITS.THOUS) < 100) {
				result.append(languageAndFormatHelper.getLanguageSupport()
						.getAnd()
						+ thous.toLowerCase()
						+ UK_FORMAT.SPACE.val()
						+ languageAndFormatHelper.getLanguageSupport()
								.getThouUnit());

			} else {
				result.append(UK_FORMAT.SPACE.val()
						+ thous.toLowerCase()
						+ UK_FORMAT.SPACE.val()
						+ languageAndFormatHelper.getLanguageSupport()
								.getThouUnit());
			}
		}
		if (numAtIndex.get(UK_UNITS.HUNS) > 0) {
			if (numAtIndex.get(UK_UNITS.THOUS) < 0
					& numAtIndex.get(UK_UNITS.MILLS) < 0) {
				result.append(huns);
			} else {
				if (numAtIndex.get(UK_UNITS.HUNS) < 100) {
					result.append(languageAndFormatHelper.getLanguageSupport()
							.getAnd() + huns.toLowerCase());
				} else {
					result.append(UK_FORMAT.SPACE.val() + huns.toLowerCase());
				}
			}
		}

		return result.toString();
	}

	/**
	 * Injected by NumberToWordFactory
	 * 
	 * @param numToWordMap
	 */
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
	public String validateAndFormat(Number num) throws IntegerToWordException {

		if (num == null) {
			LOGGER.info(languageAndFormatHelper.getLanguageSupport()
					.getNullInput());
			setMessage(languageAndFormatHelper.getLanguageSupport()
					.getNullInput());
			throw new IntegerToWordException(languageAndFormatHelper
					.getLanguageSupport().getNullInput());
		}

		if (!(num instanceof Integer)) {
			throw new IntegerToWordException(languageAndFormatHelper
					.getLanguageSupport().getNumberFormatErr());
		}

		if ((Integer) num < 0) {
			LOGGER.info(languageAndFormatHelper.getLanguageSupport()
					.getNegativeInput());
			setMessage(languageAndFormatHelper.getLanguageSupport()
					.getNegativeInput());
			throw new IntegerToWordException(languageAndFormatHelper
					.getLanguageSupport().getNegativeInput());
		}

		String formattedNumber = null;

		try {
			formattedNumber = convertToNumberFormat(Long.valueOf(String
					.valueOf(num)));
		} catch (NumberFormatException nfe) {
			setMessage(languageAndFormatHelper.getLanguageSupport()
					.getNumberFormatErr());
			throw new IntegerToWordException(languageAndFormatHelper
					.getLanguageSupport().getNumberFormatErr());
		}
		return formattedNumber;
	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 */
	private String getWordForInt(Integer num) throws IntegerToWordException {
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
					+ languageAndFormatHelper.getLanguageSupport().getHunUnit();
			if (rem > 0) { // not whole hundredth
				String decs = getDecimalPart(rem, indOne, indTwo);
				result += languageAndFormatHelper.getLanguageSupport().getAnd()
						+ decs.toLowerCase();
			}
			break;

		default:
			setMessage(languageAndFormatHelper.getLanguageSupport()
					.getUnkownErr());
			throw new IntegerToWordException(languageAndFormatHelper
					.getLanguageSupport().getUnkownErr());

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
		StringBuffer atZero = new StringBuffer(indZero.toString());
		int decs = Integer.valueOf(indZero + indOne);

		if (rem == 0) {// int range[1x-9x] (x IS 0)
			result = numToWordMap.get(indZero + indOne);

		} else if (decs > 10 & decs < 20) {
			result = numToWordMap.get(indZero + indOne);

		} else if (indZero.equals("0")) {// eg 09
			LOGGER.info("ZEEEEEEEEEERO");
			result = numToWordMap.get(indOne);

		} else { // 2x-99 (x NOT 0)
			atZero.append("0");
			// indZero += "0"; // add "0" to indZero so as to match key of whole
			// ten
			LOGGER.info("indZero:" + indZero + "indOne:" + indOne);
			if (!indOne.equals("0")) {
				result = numToWordMap.get(atZero.toString())
						+ UK_FORMAT.SPACE.val() + numToWordMap.get(indOne);
			} else {
				result = numToWordMap.get(atZero.toString());
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
