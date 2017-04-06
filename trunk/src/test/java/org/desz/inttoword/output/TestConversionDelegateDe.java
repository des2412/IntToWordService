package org.desz.inttoword.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.config.IntToWordServiceConfig;
import org.desz.inttoword.conv.ConversionDelegate;
import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.ProvLang;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntToWordServiceConfig.class})
@ActiveProfiles({"dev", "cloud"})
public class TestConversionDelegateDe {

	private static final String MAX_INT = "zwei milliarden einhundertsiebenundvierzig millionen vierhundertdreiundachtzigtausendsechshundertsiebenundvierzig";
	@Autowired
	protected ConversionDelegate conversionDelg;

	@Test
	public void testMaxGerman() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(Integer.MAX_VALUE,
				ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals(MAX_INT, s);
	}

	@Test
	public final void test100() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(100, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhundert", s);
	}

	@Test
	public final void test1000000() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(1000000, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("ein million", s);
	}

	@Test
	public final void test21012301() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(21012301, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("einundzwanzig millionen zwölftausenddreihunderteins", s);
	}

	@Test
	public final void test21021301() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(21021301, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals(
				"einundzwanzig millionen einundzwanzigtausenddreihunderteins",
				s);
	}

	@Test
	public final void test2000001() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(2000001, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("zwei millionen eins", s);
	}

	// zwei Millionen einundzwanzig
	@Test
	public final void test2000021() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(2000021, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("zwei millionen einundzwanzig", s);
	}

	// zwei Millionen zweihunderteinundzwanzig
	@Test
	public final void test2000221() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(2000221, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("zwei millionen zweihunderteinundzwanzig", s);
	}

	@Test
	public final void test30() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(30, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("dreißig", s);
	}

	@Test
	public final void test21() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(21, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("einundzwanzig", s);
	}

	@Test
	public final void test101() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(101, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhunderteins", s);
	}
	@Test
	public final void test102() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(102, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhundertzwei", s);
	}
	// einhunderteinunddreißig
	@Test
	public final void test131() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(131, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("einhunderteinunddreißig", s);
	}

	// einhundertdreiundzwanzigtausendvierhundertsechsundfünfzig

	@Test
	public final void test123456() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(123456, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals(
				"einhundertdreiundzwanzigtausendvierhundertsechsundfünfzig", s);
	}
	// neunhundertfϋnf FIXME issue with umlaut match.
	// @Test
	public final void test905() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(905, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("neunhundertfϋnf", s);
	}

	@Test
	public final void test327() throws AppConversionException {
		String s = conversionDelg.convertIntToWord(327, ProvLang.DE);
		assertNotNull("null unexpected", s);
		assertEquals("dreihundertsiebenundzwanzig", s);
	}

}
