package org.desz.numbertoword.mapper;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.language.LanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.NUMBER_CONSTANT;
import org.desz.numbertoword.enums.EnumHolder.UK_FORMAT;
import org.desz.numbertoword.enums.EnumHolder.UK_UNITS;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.factory.IntegerToWordEnumFactory;

/**
 * Class is configured by NumberToWordFactory
 * 
 * @author des: des_williams_2000@yahoo.com
 * 
 */
public final class IntegerToWordMapper implements
		IFNumberToWordMapper<BigInteger> {

	private final LanguageSupport languageSupport;

	protected final static Logger LOGGER = Logger
			.getLogger(IntegerToWordMapper.class.getName());

	public Map<String, String> intToWordMap = null;

	private static final String FORMATTED_NUMBER_SEPARATOR = UK_FORMAT.UKSEP
			.val();

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
	 * @see IntegerToWordEnumFactory
	 * @param languageSupport
	 *            specific text for target PROVISIONED_LANGUAGE
	 */
	private IntegerToWordMapper(final LanguageSupport languageSupport) {
		this.languageSupport = languageSupport;
	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 *             if validate throws Exception type
	 */

	@Override
	public String getWord(BigInteger num) throws IntegerToWordException {

		String formattedNumber = null;
		try {
			formattedNumber = validateAndFormat(num);
			//LOGGER.info("Formatted Number:" + formattedNumber);

		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			throw new IntegerToWordException(e);
		}

		//LOGGER.info("Format Separator:" + FORMATTED_NUMBER_SEPARATOR);
		String[] components = formattedNumber.split(FORMATTED_NUMBER_SEPARATOR);
		final int nComps = components.length;
		if (nComps > 3) {
			LOGGER.info(languageSupport.getInvalidInput() + num);
			setMessage(languageSupport.getInvalidInput() + num);
			throw new IntegerToWordException(languageSupport.getInvalidInput()
					+ num);
		}

		if (formattedNumber.equals("0")) {
			return intToWordMap.get("0");
		}

		String mills = UK_FORMAT.EMPTY.val();
		String thous = UK_FORMAT.EMPTY.val();
		String huns = UK_FORMAT.EMPTY.val();

		Map<UK_UNITS, BigInteger> numAtIndex = new EnumMap<UK_UNITS, BigInteger>(
				UK_UNITS.class);

		StringBuffer result = new StringBuffer();

		BigInteger val = null;
		if (nComps == 3) {

			val = BigInteger.valueOf(Long.valueOf(components[0]));
			mills = getWordForInt(val);
			numAtIndex.put(UK_UNITS.MILLS, val);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			thous = getWordForInt(val);
			numAtIndex.put(UK_UNITS.THOUS, val);

			val = BigInteger.valueOf(Long.valueOf(components[2]));
			huns = getWordForInt(val);
			numAtIndex.put(UK_UNITS.HUNS, val);

		} else if (nComps == 2) {
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			thous = getWordForInt(val);
			numAtIndex.put(UK_UNITS.MILLS, NUMBER_CONSTANT.MINUS_ONE.getVal());
			numAtIndex.put(UK_UNITS.THOUS, val);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			huns = getWordForInt(val);
			numAtIndex.put(UK_UNITS.HUNS, val);
		} else {
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			huns = getWordForInt(val);
			numAtIndex.put(UK_UNITS.MILLS, NUMBER_CONSTANT.MINUS_ONE.getVal());
			numAtIndex.put(UK_UNITS.THOUS, NUMBER_CONSTANT.MINUS_ONE.getVal());
			numAtIndex.put(UK_UNITS.HUNS, val);
		}

		// Concatenate the units of the number

		if (numAtIndex.get(UK_UNITS.MILLS).compareTo(
				NUMBER_CONSTANT.ZERO.getVal()) > 0) {
			String mn = mills + UK_FORMAT.SPACE.val()
					+ languageSupport.getMillUnit();
			result.append(mn);
		}
		if (numAtIndex.get(UK_UNITS.THOUS).compareTo(
				NUMBER_CONSTANT.ZERO.getVal()) > 0) {
			if (mills == UK_FORMAT.EMPTY.val()) {
				result.append(thous + UK_FORMAT.SPACE.val()
						+ languageSupport.getThouUnit());
			} else if (numAtIndex.get(UK_UNITS.THOUS).compareTo(
					NUMBER_CONSTANT.ONE_HUNDRED.getVal()) < 0) {
				result.append(languageSupport.getAnd() + thous.toLowerCase()
						+ UK_FORMAT.SPACE.val() + languageSupport.getThouUnit());

			} else {
				result.append(UK_FORMAT.SPACE.val() + thous.toLowerCase()
						+ UK_FORMAT.SPACE.val() + languageSupport.getThouUnit());
			}
		}
		if (numAtIndex.get(UK_UNITS.HUNS).compareTo(
				NUMBER_CONSTANT.ZERO.getVal()) > 0) {
			if (numAtIndex.get(UK_UNITS.THOUS).compareTo(
					NUMBER_CONSTANT.ZERO.getVal()) < 0
					& numAtIndex.get(UK_UNITS.MILLS).compareTo(
							NUMBER_CONSTANT.ZERO.getVal()) < 0) {
				result.append(huns);
			} else {
				if (numAtIndex.get(UK_UNITS.HUNS).compareTo(
						NUMBER_CONSTANT.ONE_HUNDRED.getVal()) < 0) {
					result.append(languageSupport.getAnd() + huns.toLowerCase());
				} else {
					result.append(UK_FORMAT.SPACE.val() + huns.toLowerCase());
				}
			}
		}

		return result.toString();
	}

	/**
	 * Injected by IntegerToWordFactory
	 * 
	 * @param numToWordMap
	 */
	public void setMapping(Map<String, String> numToWordMap) {
		this.intToWordMap = numToWordMap;

	}

	/**
	 * TODO set max val for num
	 * 
	 * @param num
	 * @return
	 * @throws IntegerToWordException
	 */
	public String validateAndFormat(BigInteger num)
			throws IntegerToWordException {

		if (num == null) {
			LOGGER.info(languageSupport.getNullInput());
			setMessage(languageSupport.getNullInput());
			throw new IntegerToWordException(languageSupport.getNullInput());
		}

		/*
		 * if (!(num instanceof Integer)) { throw new IntegerToWordException(
		 * languageSupport.getNumberFormatErr()); }
		 */

		if (num.compareTo(NUMBER_CONSTANT.ZERO.getVal()) < 0) {
			LOGGER.info(languageSupport.getNegativeInput());
			setMessage(languageSupport.getNegativeInput());
			throw new IntegerToWordException(languageSupport.getNegativeInput());
		}

		String formattedNumber = null;

		try {
			formattedNumber = convertToNumberFormat(Long.valueOf(String
					.valueOf(num)));
		} catch (NumberFormatException nfe) {
			setMessage(languageSupport.getNumberFormatErr());
			throw new IntegerToWordException(
					languageSupport.getNumberFormatErr());
		}
		return formattedNumber;
	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 */
	private String getWordForInt(BigInteger num) throws IntegerToWordException {
		String numStr = String.valueOf(num);
		String indZero;
		String indOne;
		String indTwo;
		String result = null;
		BigInteger rem = null;
		switch (numStr.length()) {
		// 1,2 or 3 digits
		case 1:
			result = intToWordMap.get(numStr);
			break;
		case 2:
			indZero = String.valueOf(numStr.charAt(0));
			indOne = String.valueOf(numStr.charAt(1));
			rem = num.mod(NUMBER_CONSTANT.TEN.getVal());

			if (num.compareTo(new BigInteger("9")) > 0
					& num.compareTo(NUMBER_CONSTANT.TWENTY.getVal()) < 0) {// teenager
				result = intToWordMap.get(indZero + indOne);
			} else { // >= 20 & <= 99
				result = getDecimalPart(rem, indZero, indOne);
			}
			break;
		case 3:
			indZero = String.valueOf(numStr.charAt(0));
			indOne = String.valueOf(numStr.charAt(1));
			indTwo = String.valueOf(numStr.charAt(2));

			rem = num.mod(NUMBER_CONSTANT.ONE_HUNDRED.getVal());
			result = intToWordMap.get(indZero) + UK_FORMAT.SPACE.val()
					+ languageSupport.getHunUnit();
			if (rem.compareTo(NUMBER_CONSTANT.ZERO.getVal()) > 0) { // not whole
																	// hundredth
				String decs = getDecimalPart(rem, indOne, indTwo);
				result += languageSupport.getAnd() + decs.toLowerCase();
			}
			break;

		default:
			setMessage(languageSupport.getUnkownErr());
			throw new IntegerToWordException(languageSupport.getUnkownErr());

		}
		LOGGER.info("getWordForInt:" + result);
		return result;
	}

	/**
	 * TODO refactor this method
	 * 
	 * @param rem
	 * @param indZero
	 *            single char string at index 0
	 * @param indOne
	 *            single char string at index 1
	 * @return
	 */
	private String getDecimalPart(BigInteger rem, String indZero, String indOne) {
		// check that indZero and indOne have length 1
		if (indZero.length() > 1 | indOne.length() > 1) {
			throw new IllegalArgumentException("TODO");
		}
		LOGGER.info("parameter rem: " + rem);
		BigInteger decs = new BigInteger(indZero + indOne);

		String result;
		StringBuffer atZero = new StringBuffer(indZero.toString());
		// int decs = Integer.valueOf(indZero + indOne);

		if (rem.compareTo(BigInteger.ZERO) == 0) {// int range[1x-9x] (x IS 0)
			result = intToWordMap.get(indZero + indOne);

		} else if (decs.compareTo(NUMBER_CONSTANT.TEN.getVal()) > 0
				& decs.compareTo(NUMBER_CONSTANT.TWENTY.getVal()) < 0) {
			result = intToWordMap.get(indZero + indOne);

		} else if (decs.compareTo(NUMBER_CONSTANT.TEN.getVal()) < 0) {// eg 09
			LOGGER.info("ZEEEEEEEEEERO");
			result = intToWordMap.get(indOne);

		} else { // 2x-9x (x NOT 0)
			atZero.append("0");
			// indZero += "0"; // add "0" to indZero so as to match key of whole
			// ten
			LOGGER.info("indZero:" + indZero + "indOne:" + indOne);
			if (!indOne.equals("0")) {
				result = intToWordMap.get(atZero.toString())
						+ UK_FORMAT.SPACE.val() + intToWordMap.get(indOne);
			} else {
				result = intToWordMap.get(atZero.toString());
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
		//LOGGER.info("Formatted Number:" + s);
		return s;
	}

}
