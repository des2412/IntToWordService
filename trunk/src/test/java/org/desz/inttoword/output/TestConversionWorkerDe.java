package org.desz.inttoword.output;

import static org.junit.Assert.*;

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
public class TestConversionWorkerDe {

	private static final String MAX_INT = "zwei milliarden einhundertsiebenundvierzig millionen vierhundertdreiundachtzigtausendsechshundertsiebenundvierzig";
	@Autowired
	protected ConversionWorker converterService;

	@Test
	public void testMaxGerman() throws AppConversionException {
		String s = converterService.convertIntToWord(Integer.MAX_VALUE,
				ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals(MAX_INT, s);
	}

	@Test
	public final void test100() throws AppConversionException {
		String s = converterService.convertIntToWord(100, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhundert", s);
	}

	@Test
	public final void test1000000() throws AppConversionException {
		String s = converterService.convertIntToWord(1000000, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("ein million", s);
	}

	@Test
	public final void test2000001() throws AppConversionException {
		String s = converterService.convertIntToWord(2000001, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("zwei millionen eins", s);
	}

	@Test
	public final void test101() throws AppConversionException {
		String s = converterService.convertIntToWord(101, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("hunderteins", s);
	}

	// einhunderteinunddreißig
	@Test
	public final void test131() throws AppConversionException {
		String s = converterService.convertIntToWord(131, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhunderteinunddreißig", s);
	}

	// einhundertdreiundzwanzigtausendvierhundertsechsundfünfzig

	@Test
	public final void test123456() throws AppConversionException {
		String s = converterService.convertIntToWord(123456, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals(
				"einhundertdreiundzwanzigtausendvierhundertsechsundfünfzig", s);
	}
	// neunhundertfϋnf
	@Test
	public final void test905() throws AppConversionException {
		String s = converterService.convertIntToWord(905, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("neunhundertfϋnf", s);
	}

	@Test
	public final void test327() throws AppConversionException {
		String s = converterService.convertIntToWord(327, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("dreihundertsiebenundzwanzig", s);
	}

}
