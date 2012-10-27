package org.desz.numbertoword;

/**
 * @author des
 */
import java.text.NumberFormat;
import java.util.Locale;

public final class UkNumberFormatHelper {

	private static UkNumberFormatHelper helper;

	private static final NumberFormat ukNumberFormat = NumberFormat
			.getIntegerInstance(Locale.UK);

	/**
	 * private constructor
	 */
	private UkNumberFormatHelper() {

	}

	/**
	 * This class can be extended to act as a Factory for languages other than
	 * UK English Locale through Enum eg.
	 * 
	 * static singleton
	 * 
	 * @return helper
	 */
	public static synchronized UkNumberFormatHelper getInstance() {
		if (helper == null) {
			helper = new UkNumberFormatHelper();
		}
		return helper;
	}

	/**
	 * The formatted String that results has 1 to 3 parts.
	 * 
	 * @param num
	 * @return formatted String representation of num
	 */
	public String convertToNumberFormat(Long num)
			throws IllegalArgumentException {
		String s = null;
		try {
			s = ukNumberFormat.format(num);
		} catch (IllegalArgumentException e) {
			throw (e);
		}
		return s;
	}

	/**
	 * This method will not deal with
	 * 
	 * @param s
	 * @param separator
	 * @return number of UK formatted number separators
	 */
	public int countUkNumberFormatSeparators(String s, String separator) {
		if (s.matches("\\d{1,3}")) {
			return 0;
		} else if (s.matches("\\d{1,3}" + separator + "{1}\\d{1,3}")) {
			return 1;
		} else if (s.matches("\\d{1,3}" + separator + "{1}\\d{1,3}\\,\\d{1,3}")) {
			return 2;
		} else {
			// Integer formatted has > 2 separators for UKIntegerToWord to
			// handle
			return -1;
		}

	}
}
