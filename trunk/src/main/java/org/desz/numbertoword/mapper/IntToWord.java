package org.desz.numbertoword.mapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.DEF_FMT;
import org.desz.numbertoword.enums.EnumHolder.NUMBER_CONSTANT;
import org.desz.numbertoword.exceptions.IntRangeUpperExc;
import org.desz.numbertoword.exceptions.IntRangeLowerExc;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.desz.numbertoword.service.validator.IFormatter;

/**
 * Class is strict Singleton and configured by INumberToWordFactory
 * 
 * Converts an Integer to the word representation in the target language.
 * 
 * @author des: des_williams_2000@yahoo.com
 * 
 */
public final class IntToWord implements
		IFNumberToWordMapper<BigInteger> {

	private final ILanguageSupport enumLanguageSupport;

	protected transient final static Logger LOGGER = Logger
			.getLogger(IntToWord.class.getName());

	private IFormatter validator;

	/**
	 * error messages that may be encountered
	 */
	private String message;

	@Override
	public String getErrorMessage() {
		return message;
	}

	/**
	 * used to set message
	 * @param message
	 */
	private void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Constructor is private to enforce Singleton semantics
	 * 
	 * @see IntToWordEnumFactory which 'injects' the correct
	 *      enumLanguageSupport and chosen IValAndFormatValidator
	 * 
	 * @param enumLanguageSupport
	 * @param validator
	 */
	private IntToWord(final ILanguageSupport enumLanguageSupport,
			IFormatter validator) {
		this.enumLanguageSupport = enumLanguageSupport;
		this.validator = validator;
	}

	/**
	 * 
	 * @return enumLanguageSupport
	 */
	public ILanguageSupport getEnumLanguageSupport() {
		return enumLanguageSupport;
	}

	/**
	 * 
	 * @param num
	 *            BigInteger to format
	 * @return num in UK Format
	 * @throws IntRangeUpperExc
	 * @throws IntRangeLowerExc
	 */
	public String formatBigInteger(BigInteger num)
			throws IntRangeUpperExc, IntRangeLowerExc {
		return validator.validateAndFormat(num);
	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws IntRangeUpperExc
	 *             if validate throws Exception type
	 * @throws IntRangeLowerExc
	 */
	@Override
	public String getWord(BigInteger num) throws IntRangeUpperExc,
			IntRangeLowerExc {

		String formattedNumber = null;
		try {
			formattedNumber = this.validator.validateAndFormat(num);

		} catch (IntRangeUpperExc e) {
			LOGGER.info(e.getMessage());
			setMessage(enumLanguageSupport.getNumberFormatErr());
			throw new IntRangeUpperExc(e);
		} catch (IntRangeLowerExc e) {
			setMessage(enumLanguageSupport.getInvalidInput());
			throw new IntRangeLowerExc(e.getMessage());
		}

		if (formattedNumber.equals("0")) {
			return enumLanguageSupport.getIntToWordMap().get("0");
		}

		String[] components = formattedNumber.split(DEF_FMT.NUM_SEP.val());
		final int nComps = components.length;

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
					.put(DEF_FMT.MILLS, NUMBER_CONSTANT.MINUS_ONE.getVal());
			numAtIndex.put(DEF_FMT.THOUS, val);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			huns = getWordForInt(val);
			numAtIndex.put(DEF_FMT.HUNS, val);
			break;

		case 1:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			huns = getWordForInt(val);
			numAtIndex
					.put(DEF_FMT.MILLS, NUMBER_CONSTANT.MINUS_ONE.getVal());
			numAtIndex
					.put(DEF_FMT.THOUS, NUMBER_CONSTANT.MINUS_ONE.getVal());
			numAtIndex.put(DEF_FMT.HUNS, val);
			break;
		default:
			//LOGGER.info(enumLanguageSupport.getInvalidInput() + num);
			setMessage(enumLanguageSupport.getInvalidInput() + num);
			throw new IntRangeUpperExc(
					enumLanguageSupport.getInvalidInput() + num);

		}

		// Concatenate units of the number

		StringBuffer result = new StringBuffer();

		if (numAtIndex.get(DEF_FMT.MILLS).compareTo(
				NUMBER_CONSTANT.ZERO.getVal()) > 0) {
			String mn = mills + DEF_FMT.SPACE.val()
					+ enumLanguageSupport.getMillUnit();
			result.append(mn);
		}

		final BigInteger thou = numAtIndex.get(DEF_FMT.THOUS);
		if (thou.compareTo(NUMBER_CONSTANT.ZERO.getVal()) > 0) {

			String appThous = thous + DEF_FMT.SPACE.val()
					+ enumLanguageSupport.getThouUnit();

			if (mills == DEF_FMT.EMPTY.val()) {
				result.append(appThous);
			} else if (thou.compareTo(NUMBER_CONSTANT.ONE_HUNDRED.getVal()) < 0) {
				result.append(enumLanguageSupport.getAnd()
						+ appThous.toLowerCase());

			} else {
				result.append(DEF_FMT.SPACE.val() + appThous.toLowerCase());
			}
		}
		if (numAtIndex.get(DEF_FMT.HUNS).compareTo(
				NUMBER_CONSTANT.ZERO.getVal()) > 0) {
			if (!parentsHoldValue(numAtIndex, DEF_FMT.MILLS, DEF_FMT.THOUS)) {
				result.append(huns);
			} else {
				if (numAtIndex.get(DEF_FMT.HUNS).compareTo(
						NUMBER_CONSTANT.ONE_HUNDRED.getVal()) < 0) {
					result.append(enumLanguageSupport.getAnd()
							+ huns.toLowerCase());
				} else {
					result.append(DEF_FMT.SPACE.val() + huns.toLowerCase());
				}
			}
		}
		
		//String res = result.toString().toLowerCase();
		
		result.replace(0, 1, String.valueOf(result.charAt(0)).toUpperCase());

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
					.compareTo(NUMBER_CONSTANT.ZERO.getVal()) < 0) {
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
	 * @throws IntRangeUpperExc
	 */
	private String getWordForInt(BigInteger num) throws IntRangeUpperExc {
		String numStr = String.valueOf(num);
		String indZero = null;
		String indOne = null;
		String indTwo = null;
		String result = null;
		// check if numStr is directly mapped
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

			if (num.compareTo(NUMBER_CONSTANT.NINE.getVal()) > 0
					& num.compareTo(NUMBER_CONSTANT.TWENTY.getVal()) < 0) {// teenager
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

			BigInteger rem = num.mod(NUMBER_CONSTANT.ONE_HUNDRED.getVal());
			result = enumLanguageSupport.getIntToWordMap().get(indZero)
					+ DEF_FMT.SPACE.val() + enumLanguageSupport.getHunUnit();
			if (rem.compareTo(NUMBER_CONSTANT.ZERO.getVal()) > 0) { // not
																		// whole
																		// hundredth
				String decs = getDecimalPart(indOne, indTwo);
				result += enumLanguageSupport.getAnd() + decs.toLowerCase();
			}
			break;

		default:
			setMessage(enumLanguageSupport.getUnkownErr());
			throw new IntRangeUpperExc(enumLanguageSupport.getUnkownErr());

		}
		//LOGGER.info("getWordForInt:" + result);
		return result;
	}

	/**
	 * Used to calculate the word for the decimal part of number >= 100
	 * 
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
					"Method requires that String arguments must have length of 1");
		}
		final BigInteger decs = new BigInteger(indZero + indOne);

		final String result;
		StringBuffer atZero = new StringBuffer(indZero.toString());

		if (decs.compareTo(BigInteger.ZERO) == 0
				| (decs.compareTo(NUMBER_CONSTANT.TEN.getVal()) > 0 & decs
						.compareTo(NUMBER_CONSTANT.TWENTY.getVal()) < 0)) {
			result = enumLanguageSupport.getIntToWordMap()
					.get(indZero + indOne);

		} else if (decs.compareTo(NUMBER_CONSTANT.TEN.getVal()) < 0) {// eg
																			// 09
			// LOGGER.info("[0-9]");
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
		return result.toLowerCase();
	}

}
