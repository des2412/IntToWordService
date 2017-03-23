package org.desz.inttoword.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.mapper.ConversionWorker;
import org.desz.inttoword.spring.config.IntToWordServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntToWordServiceConfig.class})
@ActiveProfiles({"dev", "cloud"})
public class TestConversionWorkerUk {
	@Autowired
	protected ConversionWorker conversionWorker;
	private static final String MAX_INT = "two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven";

	@Test(expected = NullPointerException.class)
	public void testNull() throws AppConversionException {
		conversionWorker.convertIntToWord(null, null);

	}

	@Test(expected = NullPointerException.class)
	public void testNonInt() throws AppConversionException {
		conversionWorker.convertIntToWord(Integer.MAX_VALUE + 1, ProvLang.UK);
	}

	@Test
	public void test12123113() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(12123113, ProvLang.UK);
		assertEquals(
				"twelve million one hundred and twenty three thousand one hundred and thirteen",
				s);
	}

	@Test
	public final void testZero() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(0, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("zero", s);
	}

	@Test
	public final void test1() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(1, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one", s);
	}

	@Test
	public void testMax() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(Integer.MAX_VALUE,
				ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals(MAX_INT, s);
	}

	@Test
	public final void test15() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(15, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("fifteen", s);
	}

	@Test
	public final void test100() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(100, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred", s);
	}

	@Test
	public final void test101() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(101, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred and one", s);
	}

	@Test
	public final void test123() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(123, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one hundred and twenty three", s);
	}

	@Test
	public final void test123456() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(123456, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals(
				"one hundred and twenty three thousand four hundred and fifty six",
				s);
	}

	@Test
	public final void test1000000() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(1000000, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one million", s);
	}

	@Test
	public final void test1000() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(1000, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("one thousand", s);
	}

	@Test
	public final void test10000() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(10000, ProvLang.UK);
		assertNotNull("null UNexpected", s);
		assertEquals("ten thousand", s);
	}

	@Test
	public final void test10099() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(10099, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand and ninety nine", s);
	}

	@Test
	public final void test10090() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(10090, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand and ninety", s);
	}

	@Test
	public final void test10001() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(10001, ProvLang.UK);
		assertNotNull("null unexpected", s);
		assertEquals("ten thousand and one", s);
	}

	@Test
	public final void test10000000() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(10000000, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("ten million", s);
	}

	@Test
	public final void test10000001() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(10000001, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("ten million and one", s);
	}

	@Test
	public final void test100000() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(100000, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one hundred thousand", s);
	}

	@Test
	public final void test100000000() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(100000000, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one hundred million", s);
	}

	@Test
	public final void test1100000() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(1100000, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals("one million one hundred thousand", s);
	}

	@Test
	public final void test1123456() throws AppConversionException {
		String s = conversionWorker.convertIntToWord(1123456, ProvLang.UK);
		assertNotNull("null unexpected", s);

		assertEquals(
				"one million one hundred and twenty three thousand four hundred and fifty six",
				s);
	}

}
