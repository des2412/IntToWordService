/**
 * 
 */
package org.desz.mapper.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.language.EnumLanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.junit.Before;
import org.junit.Test;

/**
 * @author des
 * 
 */
public class IntToWordDelegateTest {

	EnumLanguageSupport sup;

	@Before
	public void init() {
		sup = new EnumLanguageSupport(PROV_LANG.UK);
		assertNotNull(sup);
	}
	
	@Test
	public void test999() throws IntToWordExc {
		assertEquals("Nine hundred and Ninety Nine", IntToWordDelegate.calcWord(sup, new BigInteger("999")));
	}

	@Test
	public void test99() throws IntToWordExc {
		assertEquals("Ninety Nine", IntToWordDelegate.calcWord(sup, new BigInteger("99")));
	}
	
	@Test
	public void test90() throws IntToWordExc {
		assertEquals("Ninety", IntToWordDelegate.calcWord(sup, new BigInteger("90")));
	}
	
	@Test
	public void test190() throws IntToWordExc {
		assertEquals("One hundred and Ninety", IntToWordDelegate.calcWord(sup, new BigInteger("190")));
	}

	/**
	 * Delegate only acts on <100
	 * @throws IntToWordExc
	 */
	//@Test(expected = IntToWordExc.class)
	public void testInvalidInput() throws IntToWordExc {

		IntToWordDelegate.calcWord(sup, new BigInteger("100"));

	}

	@Test(expected = IntToWordExc.class)
	public void testNullNumInput() throws IntToWordExc {

		IntToWordDelegate.calcWord(sup, null);

	}
	
	@Test(expected = IntToWordExc.class)
	public void testNullLangSupportInput() throws IntToWordExc {

		IntToWordDelegate.calcWord(null, BigInteger.TEN);

	}

	
}
