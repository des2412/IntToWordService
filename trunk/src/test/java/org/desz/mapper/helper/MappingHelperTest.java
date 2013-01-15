/**
 * 
 */
package org.desz.mapper.helper;

import static org.junit.Assert.*;

import org.desz.language.EnumLanguageSupport;
import org.desz.mapper.helper.MappingHelper;
import org.desz.numbertoword.enums.EnumHolder.NUMBER_CONSTANT;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.junit.Before;
import org.junit.Test;

/**
 * @author des
 * 
 */
public class MappingHelperTest {

	EnumLanguageSupport sup;

	@Before
	public void init() {
		sup = new EnumLanguageSupport(PROV_LANG.UK);
		assertNotNull(sup);
	}

	@Test(expected = IntToWordExc.class)
	public void testNullNumInput() throws IntToWordExc {

		MappingHelper.getDecimalString(sup, null);

	}
	
	@Test(expected = NullPointerException.class)
	public void testNullLangSupportInput() throws IntToWordExc {

		MappingHelper.getDecimalString(null, NUMBER_CONSTANT.ONE_HUNDRED.getVal().intValue());

	}

	@Test
	public void testSingleDigit() throws IntToWordExc {
		Integer num = 9;
		String s = MappingHelper.getDecimalString(sup, num);

		assertEquals("Nine", s);
		// fail("Not yet implemented");
	}

	@Test
	public void testModulusRemZero() throws IntToWordExc {
		Integer num = 90;
		String s = MappingHelper.getDecimalString(sup, num);

		assertEquals("Ninety", s);
		// fail("Not yet implemented");
	}

	@Test
	public void testModulusRemNotZero() throws IntToWordExc {
		Integer num = 98;
		String s = MappingHelper.getDecimalString(sup, num);

		assertEquals("Ninety eight", s);

	}

}
