package org.desz.numbertoword.mapper;

import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.delegate.IntToWordDelegate;
import org.desz.numbertoword.enums.EnumHolder.DEF;
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
	 * @return
	 * @throws IntToWordExc
	 */
	@Override
	public String getWord(BigInteger num) throws IntToWordExc {

		
		if(enumLanguageSupport.getWord(String.valueOf(num)) != null){
			return enumLanguageSupport.getWord(String.valueOf(num));
		}
		
		final Map<DEF, BigInteger> numAtIndex = new EnumMap<DEF, BigInteger>(
				DEF.class);

		numAtIndex.put(DEF.HUNS, NUMBER_CONSTANT.ZERO.getVal());
		numAtIndex.put(DEF.THOUS, NUMBER_CONSTANT.ZERO.getVal());
		numAtIndex.put(DEF.MILLS, NUMBER_CONSTANT.ZERO.getVal());

		/**
		 * local inner class that encapsulates the formatted number structure
		 * 
		 * @author des
		 * 
		 */
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

				if (numAtIndex.get(DEF.MILLS).intValue() > 0) {
					sb.append(mill.toLowerCase()
							+ enumLanguageSupport.getMillUnit());
				}
				if (numAtIndex.get(DEF.THOUS).intValue() > 0) {
					if (DEC_RANGE.contains(Integer.valueOf(numAtIndex.get(
							DEF.THOUS).intValue()))
							&& mill != null) {
						sb.append(enumLanguageSupport.getAnd()
								+ thou.toLowerCase()
								+ enumLanguageSupport.getThouUnit());
					} else {
						sb.append(thou.toLowerCase()
								+ enumLanguageSupport.getThouUnit());
					}
				}

				if (numAtIndex.get(DEF.HUNS).intValue() > 0) {
					if (DEC_RANGE.contains(Integer.valueOf(numAtIndex.get(
							DEF.HUNS).intValue()))
							&& (mill != null || thou != null)) {
						sb.append(enumLanguageSupport.getAnd()
								+ hun.toLowerCase());
					} else {
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

	
		String[] components = formattedNumber.split(DEF.NUM_SEP.val());
		final int nComps = components.length;

		WordForInt holder = new WordForInt();

		String mills = DEF.EMPTY.val();
		String thous = DEF.EMPTY.val();
		String huns = DEF.EMPTY.val();

		BigInteger val = null;
		switch (nComps) {

		case 3:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			mills = IntToWordDelegate.calcWord(enumLanguageSupport, val);
			// LOGGER.info("Millions" + mills);
			numAtIndex.put(DEF.MILLS, val);
			holder.setMill(mills);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			thous = IntToWordDelegate.calcWord(enumLanguageSupport, val);
			numAtIndex.put(DEF.THOUS, val);
			if (val.intValue() >= 100) {
				holder.setThou(DEF.SPACE.val() + thous);
			} else {
				holder.setThou(thous);
			}

			val = BigInteger.valueOf(Long.valueOf(components[2]));
			huns = IntToWordDelegate.calcWord(enumLanguageSupport, val);
			numAtIndex.put(DEF.HUNS, val);
			if (val.intValue() >= 100) {
				holder.setHun(DEF.SPACE.val() + huns);
			} else {
				holder.setHun(huns);
			}
			break;

		case 2:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			thous = IntToWordDelegate.calcWord(enumLanguageSupport, val);
			numAtIndex.put(DEF.THOUS, val);
			holder.setThou(thous);

			val = BigInteger.valueOf(Long.valueOf(components[1]));
			huns = IntToWordDelegate.calcWord(enumLanguageSupport, val);
			numAtIndex.put(DEF.HUNS, val);
			if (val.intValue() >= 100) {
				holder.setHun(DEF.SPACE.val() + huns);
			} else {
				holder.setHun(huns);
			}
			break;

		case 1:
			val = BigInteger.valueOf(Long.valueOf(components[0]));
			huns = IntToWordDelegate.calcWord(enumLanguageSupport, val);
			numAtIndex.put(DEF.HUNS, val);
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

}
