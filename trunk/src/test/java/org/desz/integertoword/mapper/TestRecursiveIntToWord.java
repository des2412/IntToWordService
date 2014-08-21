package org.desz.integertoword.mapper;

import static org.junit.Assert.*;

import org.desz.integertoword.content.ContentContainer.PROV_LANG;
import org.desz.integertoword.mapper.RecursiveConverter;
import org.junit.Before;
import org.junit.Test;

public class TestRecursiveIntToWord {

	RecursiveConverter rsv;

	/**
	 * RecursiveConverter UK language output
	 */
	@Before
	public void init() {
		rsv = new RecursiveConverter(PROV_LANG.UK);
	}

	@Test
	public void test12123113() {
		String s = rsv.convert(new StringBuilder(), 12123113);
		assertEquals(
				"twelve million one hundred and twenty three thousand one hundred and thirteen",
				s);
	}

	@Test
	public final void testZero() {
		String s = rsv.convert(new StringBuilder(), 0);
		assertNotNull(s);
		assertEquals("zero", s);
	}

	@Test
	public final void testOne() {
		String s = rsv.convert(new StringBuilder(), 1);
		assertNotNull(s);
		assertEquals("one", s);
	}

	@Test
	public void testIntMax() {
		String s = rsv.convert(new StringBuilder(), Integer.MAX_VALUE);
		assertNotNull(s);
		assertEquals(
				"two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven",
				s);
	}

	@Test
	public final void test15() {
		String s = rsv.convert(new StringBuilder(), 15);
		assertNotNull(s);
		assertEquals("fifteen", s);
	}

	@Test
	public final void test23() {
		String s = rsv.convert(new StringBuilder(), 23);
		assertNotNull(s);
		assertEquals("twenty three", s);
	}

	@Test
	public final void test64() {
		String s = rsv.convert(new StringBuilder(), 64);
		assertNotNull(s);
		assertEquals("sixty four", s);
	}

	@Test
	public final void test90() {
		String s = rsv.convert(new StringBuilder(), 90);
		assertNotNull(s);
		assertEquals("ninety", s);
	}

	@Test
	public final void test100() {
		String s = rsv.convert(new StringBuilder(), 100);
		assertNotNull(s);
		assertEquals("one hundred", s);
	}

	@Test
	public final void test101() {
		String s = rsv.convert(new StringBuilder(), 101);
		assertNotNull(s);
		assertEquals("one hundred and one", s);
	}

	@Test
	public final void test123() {
		String s = rsv.convert(new StringBuilder(), 123);
		assertNotNull(s);
		assertEquals("one hundred and twenty three", s);
	}

	@Test
	public final void test123456() {
		String s = rsv.convert(new StringBuilder(), 123456);
		assertNotNull(s);
		assertEquals(
				"one hundred and twenty three thousand four hundred and fifty six",
				s);
	}

	@Test
	public final void test1000000() {
		String s = rsv.convert(new StringBuilder(), 1000000);
		assertNotNull(s);
		assertEquals("one million", s);
	}

	@Test
	public final void test1000() {
		String s = rsv.convert(new StringBuilder(), 1000);
		assertNotNull(s);
		assertEquals("one thousand", s);
	}

	@Test
	public final void test10000() {
		String s = rsv.convert(new StringBuilder(), 10000);
		assertNotNull(s);
		assertEquals("ten thousand", s);
	}

	@Test
	public final void test10099() {
		String s = rsv.convert(new StringBuilder(), 10099);
		assertNotNull(s);
		assertEquals("ten thousand and ninety nine", s);
	}

	@Test
	public final void test10090() {
		String s = rsv.convert(new StringBuilder(), 10090);
		assertNotNull(s);
		assertEquals("ten thousand and ninety", s);
	}

	@Test
	public final void test10001() {
		String s = rsv.convert(new StringBuilder(), 10001);
		assertNotNull(s);
		assertEquals("ten thousand and one", s);
	}

	@Test
	public final void test10000000() {
		String s = rsv.convert(new StringBuilder(), 10000000);
		assertNotNull(s);
		assertEquals("ten million", s);
	}

	@Test
	public final void test10000001() {
		String s = rsv.convert(new StringBuilder(), 10000001);
		assertNotNull(s);
		assertEquals("ten million and one", s);
	}

	@Test
	public final void test100000() {
		String s = rsv.convert(new StringBuilder(), 100000);
		assertNotNull(s);
		assertEquals("one hundred thousand", s);
	}

	@Test
	public final void test100000000() {
		String s = rsv.convert(new StringBuilder(), 100000000);
		assertNotNull(s);
		assertEquals("one hundred million", s);
	}

	@Test
	public final void test1100000() {
		String s = rsv.convert(new StringBuilder(), 1100000);
		assertNotNull(s);
		assertEquals("one million one hundred thousand", s);
	}

	@Test
	public final void test1123456() {
		String s = rsv.convert(new StringBuilder(), 1123456);
		assertNotNull(s);
		assertEquals(
				"one million one hundred and twenty three thousand four hundred and fifty six",
				s);
	}

}
