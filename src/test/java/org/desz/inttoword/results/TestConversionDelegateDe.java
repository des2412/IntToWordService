package org.desz.inttoword.results;

import static org.desz.inttoword.language.ProvLang.DE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.converters.ConversionDelegate;
import org.desz.inttoword.converters.HundredthConverter;
import org.desz.inttoword.converters.NumberFormatValidator;
import org.desz.inttoword.exceptions.AppConversionException;
import org.junit.Before;
import org.junit.Test;

public class TestConversionDelegateDe {

	ConversionDelegate delegate;

	@Before
	public void init() {
		delegate = new ConversionDelegate(new HundredthConverter());
		delegate.setNumberFormatValidator(NumberFormatValidator.getInstance());
	}

	@Test
	public final void testMaxInt() throws AppConversionException {
		String s = delegate.convertIntToWord(2147483647, DE);
		assertNotNull("null unexpected", s);
		assertEquals(
				"zwei Milliarden einhundertsiebenundvierzig Millionen vierhundertdreiundachtzigtausendsechshundertsiebenundvierzig",
				s);
	}

	@Test
	public final void test23873636() throws AppConversionException {
		String s = delegate.convertIntToWord(23873636, DE);
		assertNotNull("null unexpected", s);
		assertEquals("dreiundzwanzig Millionen achthundertdreiundsiebzigtausendsechshundertsechsunddreißig", s);
	}

	@Test
	public final void test1000() throws AppConversionException {
		String s = delegate.convertIntToWord(1000, DE);
		assertNotNull("null unexpected", s);
		assertEquals("eintausend", s);
	}

	@Test
	public final void test10000() throws AppConversionException {
		String s = delegate.convertIntToWord(10000, DE);
		assertNotNull("null unexpected", s);
		assertEquals("zehntausend", s);
	}

	@Test
	public final void test100001() throws AppConversionException {
		String s = delegate.convertIntToWord(100001, DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhunderttausendeins", s);
	}

	@Test
	public final void test2387() throws AppConversionException {
		String s = delegate.convertIntToWord(2387, DE);
		assertNotNull("null unexpected", s);
		assertEquals("zweitausenddreihundertsiebenundachtzig", s);
	}

	@Test
	public final void test238() throws AppConversionException {
		String s = delegate.convertIntToWord(238, DE);
		assertNotNull("null unexpected", s);
		assertEquals("zweihundertachtunddreißig", s);
	}

	@Test
	public final void test99() throws AppConversionException {
		String s = delegate.convertIntToWord(99, DE);
		assertNotNull("null unexpected", s);
		assertEquals("neunundneunzig", s);
	}

	@Test
	public final void test19() throws AppConversionException {
		String s = delegate.convertIntToWord(19, DE);
		assertNotNull("null unexpected", s);
		assertEquals("neunzehn", s);
	}

	@Test
	public final void test9() throws AppConversionException {
		String s = delegate.convertIntToWord(9, DE);
		assertNotNull("null unexpected", s);
		assertEquals("neun", s);
	}

	@Test
	public final void test0() throws AppConversionException {
		String s = delegate.convertIntToWord(0, DE);
		assertNotNull("null unexpected", s);
		assertEquals("null", s);
	}

}
