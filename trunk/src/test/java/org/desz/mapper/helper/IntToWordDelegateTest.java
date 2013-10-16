/**
 * 
 */
package org.desz.mapper.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.language.EnumLanguageSupport;
import org.desz.numbertoword.delegate.IntToWordDelegate;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordException;
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
	
	@Test(expected = IntToWordException.class)
	public void testExceedNumRange() throws IntToWordException{
		IntToWordDelegate.calcWord(sup, new BigInteger("1000"));
	}
	
	@Test(expected = IntToWordException.class)
	public void testBelowNumRange() throws IntToWordException{
		IntToWordDelegate.calcWord(sup, new BigInteger("-1"));
	}
	
	@Test
	public void test999() throws IntToWordException {
		assertEquals("Nine hundred and Ninety Nine", IntToWordDelegate.calcWord(sup, new BigInteger("999")));
	}

	@Test
	public void test99() throws IntToWordException {
		assertEquals("Ninety Nine", IntToWordDelegate.calcWord(sup, new BigInteger("99")));
	}
	
	@Test
	public void test90() throws IntToWordException {
		assertEquals("Ninety", IntToWordDelegate.calcWord(sup, new BigInteger("90")));
	}
	
	@Test
	public void test190() throws IntToWordException {
		assertEquals("One hundred and Ninety", IntToWordDelegate.calcWord(sup, new BigInteger("190")));
	}


	@Test(expected = IntToWordException.class)
	public void testNullNumInput() throws IntToWordException {

		IntToWordDelegate.calcWord(sup, null);

	}
	
	@Test(expected = IntToWordException.class)
	public void testNullLangSupportInput() throws IntToWordException {

		IntToWordDelegate.calcWord(null, BigInteger.TEN);

	}

	
}
