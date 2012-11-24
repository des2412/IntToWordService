package org.desz.numbertoword.mapper;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.DEF_FMT;
import org.desz.numbertoword.enums.EnumHolder.NUMBER_CONSTANT;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.factory.IntegerToWordEnumFactory;

/**
 * Class is configured by NumberToWordFactory
 * 
 * Converts an Integer to the word representation in the target language.
 * 
 * @author des: des_williams_2000@yahoo.com
 * 
 */
public final class IntegerToWordMapper implements
		IFNumberToWordMapper<BigInteger> {

	private final ILanguageSupport enumLanguageSupport;

	protected final static Logger LOGGER = Logger
			.getLogger(IntegerToWordMapper.class.getName());

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
	 * @see IntegerToWordEnumFactory which has the logic to 'inject' the correct
	 *      enumLanguageSupport
	 * 
	 * @param enumLanguageSupport
	 *            specific for PROVISIONED_LN
	 */
	private IntegerToWordMapper(final ILanguageSupport enumLanguageSupport) {
		this.enumLanguageSupport = enumLanguageSupport;
	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws IntegerToWordException
	 *             if validate throws Exception type
	 */
	@Override
	public String getWord(BigInteger num) throws IntegerToWordException {

		String formattedNumber = null;
		try {
			formattedNumber = validateAndFormat(num);

		} catch (IntegerToWordException e) {
			LOGGER.info(e.getMessage());
			setMessage(enumLanguageSupport.getNumberFormatErr());
			throw new IntegerToWordException(e);
		}

		// LOGGER.info("Format Separator:" + FORMATTED_NUMBER_SEPARATOR);
		String[] components = formattedNumber.split(DEF_FMT.NUM_SEP.val());
		final int nComps = components.length;

		if (formattedNumber.equals("0")) {
			return enumLanguageSupport.getIntToWordMap().get("0");
		}

		String mills = DEF_FMT.EMPTY.val();
		String thous = DEF_FMT.EMPTY.val();
		String huns = DEF_FMT.EMPTY.val();

		Map<DEF_FMT, BigInteger> numAtIndex = new EnumMap<DEF_FMT, BigInteger>(
				DEF_FMT.class);

		BigInteger val = null;
		switch (nComps) {

		case 3:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			mills = getWordForInt(val);
			numAtIndex.put(DEF_FMT.MILLS, val);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			thous = getWordForInt(val);
			numAtIndex.put(DEF_FMT.THOUS, val);

			val = BigInteger.valueOf(Long.valueOf(components[2]));
			huns = getWordForInt(val);
			numAtIndex.put(DEF_FMT.HUNS, val);
			break;

		case 2:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			thous = getWordForInt(val);
			numAtIndex
					.put(DEF_FMT.MILLS, NUMBER_CONSTANT.MINUS_ONE.getBigInt());
			numAtIndex.put(DEF_FMT.THOUS, val);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			huns = getWordForInt(val);
			numAtIndex.put(DEF_FMT.HUNS, val);
			break;

		case 1:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			huns = getWordForInt(val);
			numAtIndex
					.put(DEF_FMT.MILLS, NUMBER_CONSTANT.MINUS_ONE.getBigInt());
			numAtIndex
					.put(DEF_FMT.THOUS, NUMBER_CONSTANT.MINUS_ONE.getBigInt());
			numAtIndex.put(DEF_FMT.HUNS, val);
			break;
		default:
			LOGGER.info(enumLanguageSupport.getInvalidInput() + num);
			setMessage(enumLanguageSupport.getInvalidInput() + num);
			throw new IntegerToWordException(
					enumLanguageSupport.getInvalidInput() + num);

		}

		// Concatenate units of the number

		StringBuffer result = new StringBuffer();

		if (numAtIndex.get(DEF_FMT.MILLS).compareTo(
				NUMBER_CONSTANT.ZERO.getBigInt()) > 0) {
			String mn = mills + DEF_FMT.SPACE.val()
					+ enumLanguageSupport.getMillUnit();
			result.append(mn);
		}

		final BigInteger thou = numAtIndex.get(DEF_FMT.THOUS);
		if (thou.compareTo(NUMBER_CONSTANT.ZERO.getBigInt()) > 0) {

			String appThous = thous + DEF_FMT.SPACE.val()
					+ enumLanguageSupport.getThouUnit();

			if (mills == DEF_FMT.EMPTY.val()) {
				result.append(appThous);
			} else if (thou.compareTo(NUMBER_CONSTANT.ONE_HUNDRED.getBigInt()) < 0) {
				result.append(enumLanguageSupport.getAnd()
						+ appThous.toLowerCase());

			} else {
				result.append(DEF_FMT.SPACE.val() + appThous.toLowerCase());
			}
		}
		if (numAtIndex.get(DEF_FMT.HUNS).compareTo(
				NUMBER_CONSTANT.ZERO.getBigInt()) > 0) {
			if (!parentsHoldValue(numAtIndex, DEF_FMT.MILLS, DEF_FMT.THOUS)) {
				result.append(huns);
			} else {
				if (numAtIndex.get(DEF_FMT.HUNS).compareTo(
						NUMBER_CONSTANT.ONE_HUNDRED.getBigInt()) < 0) {
					result.append(enumLanguageSupport.getAnd()
							+ huns.toLowerCase());
				} else {
					result.append(DEF_FMT.SPACE.val() + huns.toLowerCase());
				}
			}
		}

		return result.toString();
	}

	/**
	 * 
	 * @param numAtIndex
	 * @param units
	 * @return
	 */
	private boolean parentsHoldValue(Map<DEF_FMT, BigInteger> numAtIndex,
			DEF_FMT... units) {

		boolean result = true;
		List<DEF_FMT> list = Arrays.asList(units);
		for (DEF_FMT unit : list) {
			if (numAtIndex.get(unit)
					.compareTo(NUMBER_CONSTANT.ZERO.getBigInt()) < 0) {
				result = false;
			} else {
				result = true;
			}
		}
		return result;

	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws IntegerToWordException
	 */
	public String validateAndFormat(BigInteger num)
			throws IntegerToWordException {

		if (num == null) {
			LOGGER.info(enumLanguageSupport.getNullInput());
			setMessage(enumLanguageSupport.getNullInput());
			throw new IntegerToWordException(enumLanguageSupport.getNullInput());
		}

		if (num.compareTo(NUMBER_CONSTANT.ZERO.getBigInt()) < 0) {
			LOGGER.info(enumLanguageSupport.getNegativeInput());
			setMessage(enumLanguageSupport.getNegativeInput());
			throw new IntegerToWordException(
					enumLanguageSupport.getNegativeInput());
		}

		String formattedNumber = null;

		try {
			formattedNumber = convertToNumberFormat(Long.valueOf(String
					.valueOf(num)));
		} catch (NumberFormatException nfe) {
			setMessage(enumLanguageSupport.getNumberFormatErr());
			throw new IntegerToWordException(
					enumLanguageSupport.getNumberFormatErr());
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
		String indZero = null;
		String indOne = null;
		String indTwo = null;
		String result = null;

		if (enumLanguageSupport.getIntToWordMap().containsKey(numStr)) {
			return enumLanguageSupport.getIntToWordMap().get(numStr);
		}

		switch (numStr.length()) {
		// 1,2 or 3 digits
		case 1:
			result = enumLanguageSupport.getIntToWordMap().get(numStr);
			break;
		case 2:
			indZero = String.valueOf(numStr.charAt(0));
			indOne = String.valueOf(numStr.charAt(1));

			if (num.compareTo(new BigInteger("9")) > 0
					& num.compareTo(NUMBER_CONSTANT.TWENTY.getBigInt()) < 0) {// teenager
				result = enumLanguageSupport.getIntToWordMap().get(
						indZero + indOne);
			} else { // >= 20 & <= 99
				result = getDecimalPart(indZero, indOne);
			}
			break;
		case 3:
			indZero = String.valueOf(numStr.charAt(0));
			indOne = String.valueOf(numStr.charAt(1));
			indTwo = String.valueOf(numStr.charAt(2));

			BigInteger rem = num.mod(NUMBER_CONSTANT.ONE_HUNDRED.getBigInt());
			result = enumLanguageSupport.getIntToWordMap().get(indZero)
					+ DEF_FMT.SPACE.val() + enumLanguageSupport.getHunUnit();
			if (rem.compareTo(NUMBER_CONSTANT.ZERO.getBigInt()) > 0) { // not
																		// whole
																		// hundredth
				String decs = getDecimalPart(indOne, indTwo);
				result += enumLanguageSupport.getAnd() + decs.toLowerCase();
			}
			break;

		default:
			setMessage(enumLanguageSupport.getUnkownErr());
			throw new IntegerToWordException(enumLanguageSupport.getUnkownErr());

		}
		LOGGER.info("getWordForInt:" + result);
		return result;
	}

	/**
	 * @param indZero
	 *            single char string at index 0
	 * @param indOne
	 *            single char string at index 1
	 * @return result
	 */
	private String getDecimalPart(String indZero, String indOne) {
		// check that indZero and indOne have length 1
		if (indZero.length() > 1 | indOne.length() > 1) {
			throw new IllegalArgumentException(
					"String arguments should have length of 1");
		}
		final BigInteger decs = new BigInteger(indZero + indOne);

		final String result;
		StringBuffer atZero = new StringBuffer(indZero.toString());

		if (decs.compareTo(BigInteger.ZERO) == 0
				| (decs.compareTo(NUMBER_CONSTANT.TEN.getBigInt()) > 0 & decs
						.compareTo(NUMBER_CONSTANT.TWENTY.getBigInt()) < 0)) {
			result = enumLanguageSupport.getIntToWordMap()
					.get(indZero + indOne);

		} else if (decs.compareTo(NUMBER_CONSTANT.TEN.getBigInt()) < 0) {// eg
																			// 09
			LOGGER.info("[0-9]");
			result = enumLanguageSupport.getIntToWordMap().get(indOne);

		} else { // 2x-9x (x NOT 0)
			atZero.append("0");
			// indZero += "0"; // add "0" to indZero so as to match
			// key(intToWordMap) whole
			// ten
			if (!indOne.equals("0")) {
				result = enumLanguageSupport.getIntToWordMap().get(
						atZero.toString())
						+ DEF_FMT.SPACE.val()
						+ enumLanguageSupport.getIntToWordMap().get(indOne);
			} else {
				result = enumLanguageSupport.getIntToWordMap().get(
						atZero.toString());
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
		return s;
	}

}
