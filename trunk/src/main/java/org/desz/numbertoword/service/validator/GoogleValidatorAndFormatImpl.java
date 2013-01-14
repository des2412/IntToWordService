/**
 * 
 */
package org.desz.numbertoword.service.validator;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.exceptions.IntegerToWordNegativeException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

/**
 * @author des
 * 
 */
public class GoogleValidatorAndFormatImpl implements IValAndFormatInt {

	protected final static Logger LOGGER = Logger
			.getLogger(GoogleValidatorAndFormatImpl.class.getName());

	// UK format suffices for all languages

	private transient static NumberFormat integerFormatter = NumberFormat
			.getIntegerInstance(Locale.UK);

	private static final Range<Integer> range = Ranges.closed(0, 999999999);

	private ILanguageSupport enumLanguageSupport;

	public GoogleValidatorAndFormatImpl(ILanguageSupport enumLanguageSupport) {
		super();
		this.enumLanguageSupport = enumLanguageSupport;
	}

	public ILanguageSupport getEnumLanguageSupport() {
		return enumLanguageSupport;
	}

	/**
	 * 
	 * @param num
	 * @return UK formatted String
	 * @throws IntegerToWordException
	 * @throws IntegerToWordNegativeException
	 */
	@Override
	public String validateAndFormat(BigInteger num)
			throws IntegerToWordException, IntegerToWordNegativeException {
		try {
			Preconditions.checkNotNull(num);
		} catch (NullPointerException e) {
			// LOGGER.info(enumLanguageSupport.getNullInput());
			throw new IntegerToWordException(enumLanguageSupport.getNullInput());
		}

		if (!range.contains(num.intValue())) {
			if (range.lowerEndpoint().compareTo(num.intValue()) > 0) {
				throw new IntegerToWordNegativeException(
						enumLanguageSupport.getNegativeInput());
			}
			if (range.upperEndpoint().compareTo(num.intValue()) < 0) {
				throw new IntegerToWordException(
						enumLanguageSupport.getInvalidInput());
			}

		}

		String formattedNumber = null;

		try {
			formattedNumber = convertToNumberFormat(Long.valueOf(String
					.valueOf(num)));
		} catch (NumberFormatException nfe) {
			// setMessage(enumLanguageSupport.getNumberFormatErr());
			throw new IntegerToWordException(
					enumLanguageSupport.getNumberFormatErr());
		}
		return formattedNumber;
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
