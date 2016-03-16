package org.desz.inttoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.language.LangContent.PROV_LANG;
import org.desz.inttoword.spring.config.IntToWordServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntToWordServiceConfig.class })
@ActiveProfiles({ "dev", "cloud" })
public class TestInt2StrConverter {
	@Autowired
	protected Int2StrConverter converterService;
	private static final String MAX_INT = "two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven";

	/**
	 * RecursiveConverter UK language output
	 */
	/*
	 * @BeforeClass public static void init() { converterService = new
	 * Int2StrConverter(); }
	 */

	@Test(expected = NullPointerException.class)
	public void testNull() {
		converterService.funcIntToString(null, null);

	}

	@Test(expected = NullPointerException.class)
	public void testNonInt() {
		converterService.funcIntToString(Integer.MAX_VALUE + 1, PROV_LANG.UK);
	}

	@Test
	public void test12123113() {
		String s = converterService.funcIntToString(12123113, PROV_LANG.UK);
		assertEquals("twelve million one hundred and twenty three thousand one hundred and thirteen", s);
	}

	@Test
	public final void testZero() {
		String s = converterService.funcIntToString(0, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		;
		assertEquals("zero", s);
	}

	@Test
	public final void test1() {
		String s = converterService.funcIntToString(1, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one", s);
	}

	@Test
	public void testIntMax() {
		String s = converterService.funcIntToString(Integer.MAX_VALUE, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals(MAX_INT, s);
	}

	@Test
	public final void test15() {
		String s = converterService.funcIntToString(15, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("fifteen", s);
	}

	@Test
	public final void test100() {
		String s = converterService.funcIntToString(100, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred", s);
	}

	@Test
	public final void test101() {
		String s = converterService.funcIntToString(101, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred and one", s);
	}

	@Test
	public final void test123() {
		String s = converterService.funcIntToString(123, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred and twenty three", s);
	}

	@Test
	public final void test123456() {
		String s = converterService.funcIntToString(123456, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred and twenty three thousand four hundred and fifty six", s);
	}

	@Test
	public final void test1000000() {
		String s = converterService.funcIntToString(1000000, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one million", s);
	}

	@Test
	public final void test1000() {
		String s = converterService.funcIntToString(1000, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one thousand", s);
	}

	@Test
	public final void test10000() {
		String s = converterService.funcIntToString(10000, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand", s);
	}

	@Test
	public final void test10099() {
		String s = converterService.funcIntToString(10099, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand and ninety nine", s);
	}

	@Test
	public final void test10090() {
		String s = converterService.funcIntToString(10090, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand and ninety", s);
	}

	@Test
	public final void test10001() {
		String s = converterService.funcIntToString(10001, PROV_LANG.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand and one", s);
	}

	@Test
	public final void test10000000() {
		String s = converterService.funcIntToString(10000000, PROV_LANG.UK);
		assertNotNull("null unexpected", s);

		assertEquals("ten million", s);
	}

	@Test
	public final void test10000001() {
		String s = converterService.funcIntToString(10000001, PROV_LANG.UK);
		assertNotNull("null unexpected", s);

		assertEquals("ten million and one", s);
	}

	@Test
	public final void test100000() {
		String s = converterService.funcIntToString(100000, PROV_LANG.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one hundred thousand", s);
	}

	@Test
	public final void test100000000() {
		String s = converterService.funcIntToString(100000000, PROV_LANG.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one hundred million", s);
	}

	@Test
	public final void test1100000() {
		String s = converterService.funcIntToString(1100000, PROV_LANG.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one million one hundred thousand", s);
	}

	@Test
	public final void test1123456() {
		String s = converterService.funcIntToString(1123456, PROV_LANG.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one million one hundred and twenty three thousand four hundred and fifty six", s);
	}

}
