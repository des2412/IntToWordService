/**
 * 
 */
package org.desz.numbertoword.service.validator;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;

import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.exceptions.IntRangeUpperExc;
import org.desz.numbertoword.exceptions.IntRangeLowerExc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

/**
 * @author des
 * 
 *         Uses Google common collect methods to validate an Integer
 *         [0-999999999]
 */
public final class UkIntValidator implements IFormatter {

	protected final static Logger LOGGER = Logger
			.getLogger(UkIntValidator.class.getName());

	// UK format suffices for all languages

	private transient static NumberFormat ukFmtr = NumberFormat
			.getIntegerInstance(Locale.UK);

	private static final Range<Integer> range = Ranges.closed(0, 999999999);

	private ILanguageSupport enumLanguageSupport;

	/**
	 * 
	 * @param enumLanguageSupport
	 */
	public UkIntValidator(ILanguageSupport enumLanguageSupport) {
		super();
		this.enumLanguageSupport = enumLanguageSupport;
	}

	/**
	 * TODO test
	 * @param num
	 * @return UK formatted String
	 * @throws IntRangeUpperExc
	 * @throws IntRangeLowerExc
	 */
	@Override
	public String validateAndFormat(BigInteger num) throws IntRangeUpperExc,
			IntRangeLowerExc {
		try {
			Preconditions.checkNotNull(num);
		} catch (NullPointerException e) {
			// LOGGER.info(enumLanguageSupport.getNullInput());
			throw new IllegalArgumentException(enumLanguageSupport.getNullInput());
		}

		if (!range.contains(num.intValue())) {
			if (range.lowerEndpoint().compareTo(num.intValue()) > 0) {
				throw new IntRangeLowerExc(
						enumLanguageSupport.getNegativeInput());
			}
			if (range.upperEndpoint().compareTo(num.intValue()) < 0) {
				throw new IntRangeUpperExc(
						enumLanguageSupport.getInvalidInput());
			}

		}

		try {

			return ukFmtr.format(num.longValue());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(enumLanguageSupport.getNumberFormatErr());
		}

	}

}
