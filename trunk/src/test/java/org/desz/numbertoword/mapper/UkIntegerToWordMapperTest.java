package org.desz.numbertoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UkIntegerToWordMapperTest extends IntegerToWordMapperTest {

	INumberToWordMapper<BigInteger> numberToWordMapper = null;

	@Before
	public void init() {
		try {
			numberToWordMapper = IntToWordEnumFactory.UK_FAC
					.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			Logger.getAnonymousLogger().severe("init exc.");
		}

	}

	@After
	public void clean() {
		try {
			IntToWordEnumFactory.unCacheFactory(PROV_LANG.UK);
		} catch (FactoryMapperRemovalException e) {
			LOGGER.severe(e.getMessage());
		}
	}

	
	/*@Test
	public void testSmartCalc(){
		String s = numberToWordMapper.smartCalc(new BigInteger("100"));
		assertNotNull(s);
		assertEquals("One hundred", s);
		
		
		 s = numberToWordMapper.smartCalc(new BigInteger("101"));
			assertNotNull(s);
			assertEquals("One hundred and one", s);
			
			s = numberToWordMapper.smartCalc(new BigInteger("99"));
			assertNotNull(s);
			assertEquals("Ninety nine", s);
			
			
	}*/
	@Test
	public void testNotNull() {
		assertNotNull(numberToWordMapper);
	}

	@Test(expected = NullPointerException.class)
	public void expectNullPointerException() throws IntToWordExc {
		numberToWordMapper.getWord(null);
	}

	@Test
	public void testNoMinBoundRangeViolation() throws IntToWordExc {
		numberToWordMapper.getWord(new BigInteger("0"));
	}

	@Test
	public void testNotLowerCase() throws IntToWordExc {
		String s = numberToWordMapper.getWord(new BigInteger("98989866"));
		assertEquals(
				"Ninety eight million nine hundred and eighty nine thousand eight hundred and sixty six",
				s);
		
		assertEquals(
				"Nine hundred and ninety nine million nine hundred and ninety nine thousand nine hundred and ninety nine",
				numberToWordMapper.getWord(new BigInteger("999999999")));
	}

	@Test(expected = IntToWordExc.class)
	public void testMinBoundRangeViolation() throws IntToWordExc {
		numberToWordMapper.getWord(new BigInteger("-1"));
	}

	@Test
	public void testNoMaxBoundRangeViolation() throws IntToWordExc {
		numberToWordMapper.getWord(new BigInteger("999999999"));
	}

	@Test(expected = IntToWordExc.class)
	public void testRangeViolation() throws IntToWordExc {
		numberToWordMapper.getWord(new BigInteger("1000000000"));
	}

	@Test
	public void testZero() throws IntToWordExc {
		assertEquals("Zero", numberToWordMapper.getWord(BigInteger.ZERO));
	}

	@Test
	public void testDecimals() throws IntToWordExc {

		assertEquals("One", numberToWordMapper.getWord(BigInteger.ONE));
		assertEquals("Ten", numberToWordMapper.getWord(new BigInteger("10")));

	}

	@Test
	public void testTeens() throws IntToWordExc {
		try {
			assertEquals("Eleven",
					numberToWordMapper.getWord(new BigInteger("11")));
			assertEquals("Fourteen",
					numberToWordMapper.getWord(new BigInteger("14")));
		} catch (IntToWordExc e) {
			LOGGER.severe(e.getMessage());
		}

	}

	@Test
	public void testTens() throws IntToWordExc {

		assertEquals("Forty", numberToWordMapper.getWord(new BigInteger("40")));
		int num = 41;
		for (String s : l) {

			assertEquals("Forty" + " " + s.toLowerCase(),
					numberToWordMapper.getWord(new BigInteger(String
							.valueOf(num))));
			num++;

		}

	}

	@Test
	public void testFails() throws IntToWordExc {
		
		assertEquals(
				"Nine hundred and ninety nine million nine hundred and nine thousand",
				numberToWordMapper.getWord(new BigInteger("999909000")));
		
		assertEquals("One thousand and nine",
				numberToWordMapper.getWord(new BigInteger("1009")));
		
		
		assertEquals("One million",
				numberToWordMapper.getWord(new BigInteger("1000000")));

		assertEquals("Ten thousand",
				numberToWordMapper.getWord(new BigInteger("10000")));
		assertEquals("One million",
				numberToWordMapper.getWord(new BigInteger("1000000")));
		assertEquals(
				"Eleven million one hundred and eleven thousand one hundred and eleven",
				numberToWordMapper.getWord(new BigInteger("11111111")));
	}

	@Test
	public void testGetWords() throws IntToWordExc {

		assertEquals(
				"Eleven million one hundred and eleven thousand one hundred and eleven",
				numberToWordMapper.getWord(new BigInteger("11111111")));

		
		assertEquals("One thousand and ninety nine",
				numberToWordMapper.getWord(new BigInteger("1099")));

		assertEquals("One thousand and one",
				numberToWordMapper.getWord(new BigInteger("1001")));
		assertEquals("One thousand and eleven",
				numberToWordMapper.getWord(new BigInteger("1011")));
		assertEquals("Ten thousand",
				numberToWordMapper.getWord(new BigInteger("10000")));
		assertEquals("Ten thousand and one",
				numberToWordMapper.getWord(new BigInteger("10001")));
		assertEquals("Ten thousand one hundred",
				numberToWordMapper.getWord(new BigInteger("10100")));

		assertEquals("One hundred thousand",
				numberToWordMapper.getWord(new BigInteger("100000")));

		assertEquals("One million",
				numberToWordMapper.getWord(new BigInteger("1000000")));

		assertEquals("One million one hundred thousand",
				numberToWordMapper.getWord(new BigInteger("1100000")));
		

		assertEquals("One hundred million one hundred thousand",
				numberToWordMapper.getWord(new BigInteger("100100000")));
		assertEquals("One hundred million and seventy seven thousand",
				numberToWordMapper.getWord(new BigInteger("100077000")));

		assertEquals("One million and seventy seven thousand",
				numberToWordMapper.getWord(new BigInteger("1077000")));

		assertEquals("Nine hundred million nine hundred thousand",
				numberToWordMapper.getWord(new BigInteger("900900000")));
		assertEquals("Nine hundred million and one",
				numberToWordMapper.getWord(new BigInteger("900000001")));

		assertEquals(
				"Nine hundred and ninety nine million nine hundred and ninety nine thousand nine hundred and ninety nine",
				numberToWordMapper.getWord(new BigInteger("999999999")));

	}

}
