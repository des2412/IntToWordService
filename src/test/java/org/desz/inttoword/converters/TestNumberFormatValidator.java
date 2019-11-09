package org.desz.inttoword.converters;

import static org.junit.Assert.*;

import java.util.Objects;

import static java.util.Arrays.asList;

import org.junit.Test;

public class TestNumberFormatValidator {

	NumberFormatValidator numberFormatValidator = NumberFormatValidator.getInstance();

	@Test
	public void test_notNull() {
		assertFalse(Objects.isNull(NumberFormatValidator.getInstance()));
	}

	@Test
	public void test_ok() {
		assertTrue(numberFormatValidator.isValidAndInRange(asList("123", "234")));
	}

	@Test
	public void test_invalid_number() {
		assertFalse(numberFormatValidator.isValidAndInRange(asList("12C", "234")));
	}

	@Test
	public void test_in_range_ok() {
		assertTrue(numberFormatValidator.isValidAndInRange(asList("999", "0")));
	}

	@Test
	public void test_in_range_fail() {
		assertFalse(numberFormatValidator.isValidAndInRange(asList("1239", "234")));
	}

}
