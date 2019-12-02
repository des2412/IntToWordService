package org.desz.inttoword.results;

import static org.desz.inttoword.language.ProvLang.DE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.converters.ConversionDelegate;
import org.desz.inttoword.converters.LongToWordBuilder;
import org.desz.inttoword.exceptions.AppConversionException;
import org.junit.Before;
import org.junit.Test;

public class TestConversionDelegateDe {

	private static final Long MAX_LONG = 9223372036854775807L;
	private static final Long MAX_INT = 2147483647L;
	ConversionDelegate delegate;

	@Before
	public void init() {
		delegate = new ConversionDelegate(new LongToWordBuilder());
	}

	@Test
	public final void test9223372036854775807() throws AppConversionException {
		String s = delegate.convertIntToWord(MAX_LONG, DE);
		assertNotNull("null unexpected", s);
		assertEquals(
				"neun Trillionen zweihundertdreiundzwanzig Billiarden dreihundertzweiundsiebzig Billionen sechsunddreißig Milliarden achthundertvierundfünfzig Millionen siebenhundertfünfundsiebzigtausendachthundertsieben",
				s);
	}

	@Test
	public final void testMaxInt() throws AppConversionException {
		String s = delegate.convertIntToWord(MAX_INT, DE);
		assertNotNull("null unexpected", s);
		assertEquals(
				"zwei Milliarden einhundertsiebenundvierzig Millionen vierhundertdreiundachtzigtausendsechshundertsiebenundvierzig",
				s);
	}

	@Test
	public final void test3147483647() throws AppConversionException {
		String s = delegate.convertIntToWord(3147483647L, DE);
		assertNotNull("null unexpected", s);
		assertEquals(
				"drei Milliarden einhundertsiebenundvierzig Millionen vierhundertdreiundachtzigtausendsechshundertsiebenundvierzig",
				s);
	}

	@Test
	public final void test23873636() throws AppConversionException {
		String s = delegate.convertIntToWord(23873636L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("dreiundzwanzig Millionen achthundertdreiundsiebzigtausendsechshundertsechsunddreißig", s);
	}

	@Test
	public final void test1000() throws AppConversionException {
		String s = delegate.convertIntToWord(1000L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("eintausend", s);
	}

	@Test
	public final void test1020() throws AppConversionException {
		String s = delegate.convertIntToWord(1020L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("eintausendzwanzig", s);
	}

	@Test
	public final void test807() throws AppConversionException {
		String s = delegate.convertIntToWord(807L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("achthundertsieben", s);
	}

	@Test
	public final void test817() throws AppConversionException {
		String s = delegate.convertIntToWord(817L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("achthundertsiebzehn", s);
	}

	@Test
	public final void test827() throws AppConversionException {
		String s = delegate.convertIntToWord(827L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("achthundertsiebenundzwanzig", s);
	}

	@Test
	public final void test10000() throws AppConversionException {
		String s = delegate.convertIntToWord(10000L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("zehntausend", s);
	}

	@Test
	public final void test100001() throws AppConversionException {
		String s = delegate.convertIntToWord(100001L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhunderttausendeins", s);
	}

	@Test
	public final void test1000001() throws AppConversionException {
		String s = delegate.convertIntToWord(1000001L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("ein Million eins", s);
	}

	@Test
	public final void test100000007() throws AppConversionException {
		String s = delegate.convertIntToWord(100000007L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhundert Millionen sieben", s);
	}

	@Test
	public final void test100000017() throws AppConversionException {
		String s = delegate.convertIntToWord(100000017L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhundert Millionen siebzehn", s);
	}

	@Test
	public final void test100000031() throws AppConversionException {
		String s = delegate.convertIntToWord(100000031L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhundert Millionen einunddreißig", s);
	}

	@Test
	public final void test2387() throws AppConversionException {
		String s = delegate.convertIntToWord(2387L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("zweitausenddreihundertsiebenundachtzig", s);
	}

	@Test
	public final void test238() throws AppConversionException {
		String s = delegate.convertIntToWord(238L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("zweihundertachtunddreißig", s);
	}

	@Test
	public final void test99() throws AppConversionException {
		String s = delegate.convertIntToWord(99L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("neunundneunzig", s);
	}

	@Test
	public final void test19() throws AppConversionException {
		String s = delegate.convertIntToWord(19L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("neunzehn", s);
	}

	@Test
	public final void test9() throws AppConversionException {
		String s = delegate.convertIntToWord(9L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("neun", s);
	}

	@Test
	public final void test0() throws AppConversionException {
		String s = delegate.convertIntToWord(0L, DE);
		assertNotNull("null unexpected", s);
		assertEquals("null", s);
	}

}
