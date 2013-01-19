package org.desz.numbertoword.mapper;

import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.language.ILanguageSupport;
import org.desz.mapper.helper.MappingHelper;
import org.desz.numbertoword.enums.EnumHolder.DEF_FMT;
import org.desz.numbertoword.enums.EnumHolder.NUMBER_CONSTANT;
import org.desz.numbertoword.exceptions.IntRangeLowerExc;
import org.desz.numbertoword.exceptions.IntRangeUpperExc;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.desz.numbertoword.service.validator.IFormatter;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

/**
 * Class is strict Singleton and configured by INumberToWordFactory
 * 
 * Converts an Integer to the word representation in the target language.
 * 
 * @author des: des_williams_2000@yahoo.com
 * 
 */
public final class IntToWord implements INumberToWordMapper<BigInteger> {

	private static final Range<Integer> DEC_RANGE = Ranges.closed(1, 99);
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
	 * 
	 * @param message
	 */
	private void setErrorMessage(String message) {
		this.message = message;
	}

	/**
	 * Constructor is private to enforce Singleton semantics
	 * 
	 * @see IntToWordEnumFactory which 'injects' the correct enumLanguageSupport
	 *      and chosen IValAndFormatValidator
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
	public String formatBigInteger(BigInteger num) throws IntRangeUpperExc,
			IntRangeLowerExc {
		return validator.validateAndFormat(num);
	}

	/**
	 * 
	 * @param num
	 * @return
	 * @throws IntToWordExc
	 */
	@Override
	public String getWord(BigInteger num) throws IntToWordExc {

		final Map<DEF_FMT, BigInteger> numAtIndex = new EnumMap<DEF_FMT, BigInteger>(
				DEF_FMT.class);

		numAtIndex.put(DEF_FMT.HUNS, NUMBER_CONSTANT.ZERO.getVal());
		numAtIndex.put(DEF_FMT.THOUS, NUMBER_CONSTANT.ZERO.getVal());
		numAtIndex.put(DEF_FMT.MILLS, NUMBER_CONSTANT.ZERO.getVal());

		final class WordForInt {
			private String mill;
			private String thou;
			private String hun;

			public void setMill(String mill) {
				this.mill = mill;
			}

			public void setThou(String thou) {
				this.thou = thou;
			}

			public void setHun(String hun) {
				this.hun = hun;
			}

			/**
			 * 
			 * @return
			 */
			public String getWordResult() {

				StringBuilder sb = new StringBuilder();

				if (numAtIndex.get(DEF_FMT.MILLS).intValue() > 0) {
					sb.append(mill.toLowerCase() + DEF_FMT.SPACE.val()
							+ enumLanguageSupport.getMillUnit());
				}
				if (numAtIndex.get(DEF_FMT.THOUS).intValue() > 0) {
					if (DEC_RANGE.contains(Integer.valueOf(numAtIndex.get(
							DEF_FMT.THOUS).intValue()))
							&& mill != null) {
						sb.append(enumLanguageSupport.getAnd()
								+ thou.toLowerCase() + DEF_FMT.SPACE.val()
								+ enumLanguageSupport.getThouUnit());
					} else {
						if (mill != null) {
							sb.append(DEF_FMT.SPACE.val() + thou.toLowerCase()
									+ DEF_FMT.SPACE.val()
									+ enumLanguageSupport.getThouUnit());
						} else
							sb.append(thou.toLowerCase() + DEF_FMT.SPACE.val()
									+ enumLanguageSupport.getThouUnit());
					}
				}

				if (numAtIndex.get(DEF_FMT.HUNS).intValue() > 0) {
					if (DEC_RANGE.contains(Integer.valueOf(numAtIndex.get(
							DEF_FMT.HUNS).intValue()))
							&& (mill != null || thou != null)) {
						sb.append(enumLanguageSupport.getAnd()
								+ hun.toLowerCase());
					} else {
						if (this.mill != null || this.thou != null) {
							sb.append(DEF_FMT.SPACE.val() + hun.toLowerCase());
						} else
							sb.append(hun.toLowerCase());
					}
				}
				// capitalise the first character
				sb.replace(0, 1, String.valueOf(sb.charAt(0)).toUpperCase());
				return sb.toString();
			}
		}
		String formattedNumber = null;
		try {
			formattedNumber = validator.validateAndFormat(num);

		} catch (IntRangeUpperExc e) {
			LOGGER.info(e.getMessage());
			setErrorMessage(enumLanguageSupport.getNumberFormatErr());
			throw new IntToWordExc(e);
		} catch (IntRangeLowerExc e) {
			setErrorMessage(enumLanguageSupport.getInvalidInput());
			throw new IntToWordExc(e.getMessage());
		}

		if (formattedNumber.equals("0")) {
			return enumLanguageSupport.getIntToWordMap().get("0");
		}

		String[] components = formattedNumber.split(DEF_FMT.NUM_SEP.val());
		final int nComps = components.length;

		WordForInt holder = new WordForInt();

		String mills = DEF_FMT.EMPTY.val();
		String thous = DEF_FMT.EMPTY.val();
		String huns = DEF_FMT.EMPTY.val();

		BigInteger val = null;
		switch (nComps) {

		case 3:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			mills = getWordForPart(val);
			numAtIndex.put(DEF_FMT.MILLS, val);
			holder.setMill(mills);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			thous = getWordForPart(val);
			numAtIndex.put(DEF_FMT.THOUS, val);
			holder.setThou(thous);

			val = BigInteger.valueOf(Long.valueOf(components[2]));
			huns = getWordForPart(val);
			numAtIndex.put(DEF_FMT.HUNS, val);
			holder.setHun(huns);
			break;

		case 2:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			thous = getWordForPart(val);
			numAtIndex.put(DEF_FMT.THOUS, val);
			holder.setThou(thous);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			huns = getWordForPart(val);
			numAtIndex.put(DEF_FMT.HUNS, val);
			holder.setHun(huns);
			break;

		case 1:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			huns = getWordForPart(val);
			numAtIndex.put(DEF_FMT.HUNS, val);
			holder.setHun(huns);
			break;
		default:
			// LOGGER.info(enumLanguageSupport.getInvalidInput() + num);
			setErrorMessage(enumLanguageSupport.getInvalidInput() + num);
			throw new IntToWordExc(enumLanguageSupport.getInvalidInput() + num);

		}

		// Concatenate units of the number

		return holder.getWordResult();
	}

	/**
	 * 
	 * @param num
	 * @return language specific word for num
	 */
	private String getWordForPart(final BigInteger num) {
		String numStr = String.valueOf(num);
		String result = null;
		// check if numStr is directly mapped
		if (enumLanguageSupport.getIntToWordMap().containsKey(numStr)) {
			return enumLanguageSupport.getIntToWordMap().get(numStr);
		}
		if (MappingHelper.DEC_RANGE.contains(num.intValue())) {
			return this.getDecimalPart(num);
		}

		else {
			final BigInteger rem = num
					.mod(NUMBER_CONSTANT.ONE_HUNDRED.getVal());
			result = enumLanguageSupport.getIntToWordMap().get(
					String.valueOf(numStr.charAt(0)))
					+ DEF_FMT.SPACE.val() + enumLanguageSupport.getHunUnit();
			if (rem.compareTo(NUMBER_CONSTANT.ZERO.getVal()) > 0) { // not
																	// whole
																	// hundredth
				String decs = getDecimalPart(new BigInteger(
						String.valueOf(numStr.charAt(1))
								+ String.valueOf(numStr.charAt(2))));
				return result + enumLanguageSupport.getAnd()
						+ decs.toLowerCase();
			}
		}

		return result;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	private String getDecimalPart(final BigInteger val) {

		String result = null;
		try {
			result = MappingHelper.getDecimalString(enumLanguageSupport,
					val.intValue());
		} catch (IntToWordExc e) {
			LOGGER.severe(e.getMessage());
		}
		return result.toLowerCase();
	}

}
