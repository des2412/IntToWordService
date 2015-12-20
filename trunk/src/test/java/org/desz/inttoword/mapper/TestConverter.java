package org.desz.inttoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.language.LangContent.PROV_LANG;
import org.desz.inttoword.mapper.Converter;
import org.junit.Before;
import org.junit.Test;

public class TestConverter {

	Converter rsv;

	/**
	 * RecursiveConverter UK language output
	 */
	@Before
	public void init() {
		rsv = new Converter(PROV_LANG.UK);
	}

	@Test(expected = NullPointerException.class)
	public void testNull() {
		rsv.funcIntToString(null);

	}

	@Test(expected = NullPointerException.class)
	public void testNonInt() {
		rsv.funcIntToString(Integer.MAX_VALUE + 1);
	}

	@Test
	public void test12123113() {
		String s = rsv.funcIntToString(12123113);
		assertEquals("twelve million one hundred and twenty three thousand one hundred and thirteen", s);
	}

	@Test
	public final void testZero() {
		String s = rsv.funcIntToString(0);
		assertNotNull(s);
		assertEquals("zero", s);
	}

	@Test
	public final void testOne() {
		String s = rsv.funcIntToString(1);
		assertNotNull(s);
		assertEquals("one", s);
	}

	@Test
	public void testIntMax() {
		String s = rsv.funcIntToString(Integer.MAX_VALUE);
		assertNotNull(s);
		assertEquals(
				"two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven",
				s);
	}

	@Test
	public final void test15() {
		String s = rsv.funcIntToString(15);
		assertNotNull(s);
		assertEquals("fifteen", s);
	}

	@Test
	public final void test100() {
		String s = rsv.funcIntToString(100);
		assertNotNull(s);
		assertEquals("one hundred", s);
	}

	@Test
	public final void test101() {
		String s = rsv.funcIntToString(101);
		assertNotNull(s);
		assertEquals("one hundred and one", s);
	}

	@Test
	public final void test123() {
		String s = rsv.funcIntToString(123);
		assertNotNull(s);
		assertEquals("one hundred and twenty three", s);
	}

	@Test
	public final void test123456() {
		String s = rsv.funcIntToString(123456);
		assertNotNull(s);
		assertEquals("one hundred and twenty three thousand four hundred and fifty six", s);
	}

	@Test
	public final void test1000000() {
		String s = rsv.funcIntToString(1000000);
		assertNotNull(s);
		assertEquals("one million", s);
	}

	@Test
	public final void test1000() {
		String s = rsv.funcIntToString(1000);
		assertNotNull(s);
		assertEquals("one thousand", s);
	}

	@Test
	public final void test10000() {
		String s = rsv.funcIntToString(10000);
		assertNotNull(s);
		assertEquals("ten thousand", s);
	}

	@Test
	public final void test10099() {
		String s = rsv.funcIntToString(10099);
		assertNotNull(s);
		assertEquals("ten thousand and ninety nine", s);
	}

	@Test
	public final void test10090() {
		String s = rsv.funcIntToString(10090);
		assertNotNull(s);
		assertEquals("ten thousand and ninety", s);
	}

	@Test
	public final void test10001() {
		String s = rsv.funcIntToString(10001);
		assertNotNull(s);
		assertEquals("ten thousand and one", s);
	}

	@Test
	public final void test10000000() {
		String s = rsv.funcIntToString(10000000);
		assertNotNull(s);
		assertEquals("ten million", s);
	}

	@Test
	public final void test10000001() {
		String s = rsv.funcIntToString(10000001);
		assertNotNull(s);
		assertEquals("ten million and one", s);
	}

	@Test
	public final void test100000() {
		String s = rsv.funcIntToString(100000);
		assertNotNull(s);
		assertEquals("one hundred thousand", s);
	}

	@Test
	public final void test100000000() {
		String s = rsv.funcIntToString(100000000);
		assertNotNull(s);
		assertEquals("one hundred million", s);
	}

	@Test
	public final void test1100000() {
		String s = rsv.funcIntToString(1100000);
		assertNotNull(s);
		assertEquals("one million one hundred thousand", s);
	}

	@Test
	public final void test1123456() {
		String s = rsv.funcIntToString(1123456);
		assertNotNull(s);
		assertEquals("one million one hundred and twenty three thousand four hundred and fifty six", s);
	}

}
