package org.desz.inttoword.converters;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.apache.commons.validator.routines.IntegerValidator;

public class NumberFormatValidator {
	private static class Holder {
		private static final NumberFormatValidator singleton = new NumberFormatValidator(
				IntegerValidator.getInstance());

	}

	/**
	 * 
	 * @return singleton instance.
	 */
	public static NumberFormatValidator getInstance() {
		return Holder.singleton;
	}

	private IntegerValidator validator;

	private NumberFormatValidator(IntegerValidator validator) {
		this.validator = validator;
	}

	public boolean isValidAndInRange(List<String> numUnits) {
		final List<String> l = numUnits.stream().filter(val -> validator.isValid(val))
				.filter(val -> validator.isInRange(Integer.parseInt(val), 0, 999)).collect(toList());

		return l.size() == numUnits.size();
	}
}
