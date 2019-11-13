package org.desz.inttoword.results;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.converters.ConversionDelegate;
import org.desz.inttoword.converters.LongToWordBuilder;
import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.ProvLang;
import org.junit.Before;
import org.junit.Test;

public class TestConversionDelegateUk {
	private static final String MAX_INT = "two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven";

	ConversionDelegate delegate;

	@Before
	public void init() {
		delegate = new ConversionDelegate(new LongToWordBuilder());

	}

	@Test(expected = NullPointerException.class)
	public void testNull() throws AppConversionException {
		delegate.convertIntToWord(null, null);

	}

	@Test
	public void test12123113() throws AppConversionException {
		String s = delegate.convertIntToWord(12123113L, ProvLang.UK);
		assertEquals("twelve million one hundred and twenty three thousand one hundred and thirteen", s);
	}

	@Test
	public final void testZero() throws AppConversionException {
		String s = delegate.convertIntToWord(0L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("zero", s);
	}

	@Test
	public final void test1() throws AppConversionException {
		String s = delegate.convertIntToWord(1L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one", s);
	}

	@Test
	public void testMax() throws AppConversionException {
		String s = delegate.convertIntToWord(2147483647L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals(MAX_INT, s);
	}

	@Test
	public final void test15() throws AppConversionException {
		String s = delegate.convertIntToWord(15L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("fifteen", s);
	}

	@Test
	public final void test23() throws AppConversionException {
		String s = delegate.convertIntToWord(23L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("twenty three", s);
	}

	@Test
	public final void test100() throws AppConversionException {
		String s = delegate.convertIntToWord(100L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred", s);
	}

	@Test
	public final void test101() throws AppConversionException {
		String s = delegate.convertIntToWord(101L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred and one", s);
	}

	@Test
	public final void test123() throws AppConversionException {
		String s = delegate.convertIntToWord(123L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred and twenty three", s);
	}

	@Test
	public final void test123456() throws AppConversionException {
		String s = delegate.convertIntToWord(123456L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred and twenty three thousand four hundred and fifty six", s);
	}

	@Test
	public final void test1000000() throws AppConversionException {
		String s = delegate.convertIntToWord(1000000L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one million", s);
	}

	@Test
	public final void test1000() throws AppConversionException {
		String s = delegate.convertIntToWord(1000L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one thousand", s);
	}

	@Test
	public final void test10000() throws AppConversionException {
		String s = delegate.convertIntToWord(10000L, ProvLang.UK);
		assertNotNull("null UNexpected", s);
		assertEquals("ten thousand", s);
	}

	@Test
	public final void test10099() throws AppConversionException {
		String s = delegate.convertIntToWord(10099L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand ninety nine", s);
	}

	@Test
	public final void test10090() throws AppConversionException {
		String s = delegate.convertIntToWord(10090L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand ninety", s);
	}

	@Test
	public final void test10001() throws AppConversionException {
		String s = delegate.convertIntToWord(10001L, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand one", s);
	}

	@Test
	public final void test10000000() throws AppConversionException {
		String s = delegate.convertIntToWord(10000000L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("ten million", s);
	}

	@Test
	public final void test10000001() throws AppConversionException {
		String s = delegate.convertIntToWord(10000001L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("ten million one", s);
	}

	@Test
	public final void test100000() throws AppConversionException {
		String s = delegate.convertIntToWord(100000L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one hundred thousand", s);
	}

	@Test
	public final void test100000000() throws AppConversionException {
		String s = delegate.convertIntToWord(100000000L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one hundred million", s);
	}

	@Test
	public final void test1100000() throws AppConversionException {
		String s = delegate.convertIntToWord(1100000L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one million one hundred thousand", s);
	}

	@Test
	public final void test1123456() throws AppConversionException {
		String s = delegate.convertIntToWord(1123456L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one million one hundred and twenty three thousand four hundred and fifty six", s);
	}

	@Test
	public final void test1000000000() throws AppConversionException {
		String s = delegate.convertIntToWord(1000000000L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one billion", s);
	}

	@Test
	public final void test1000000001() throws AppConversionException {
		String s = delegate.convertIntToWord(1000000001L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one billion one", s);
	}

	@Test
	public final void test1000000100() throws AppConversionException {
		String s = delegate.convertIntToWord(1000000100L, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one billion one hundred", s);
	}

}
