package org.desz.numbertoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.FR_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LN;
import org.desz.numbertoword.factory.IntegerToWordEnumFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FrIntegerToWordMapperTest extends IntegerToWordMapperTest{


	@Before
	public void init() throws Exception {
		intToWordMapper = (IntegerToWordMapper) IntegerToWordEnumFactory.FR_FAC
				.getIntegerToWordMapper();
		assertNotNull(intToWordMapper);
	}

	@After
	public void clean() throws Exception {
		IntegerToWordEnumFactory
				.removeNumberToWordEnumFactory(PROVISIONED_LN.FR);
	}

	@Test
	public void testIsSingleton() throws Exception {

		assertNotSame(intToWordMapper,
				IntegerToWordEnumFactory.UK_FAC.getIntegerToWordMapper());

		assertSame(intToWordMapper,
				IntegerToWordEnumFactory.FR_FAC.getIntegerToWordMapper());
	}

	@Test(expected = Exception.class)
	public void testNegativeInputMessage() throws Exception {

		((IntegerToWordMapper) intToWordMapper).validateAndFormat(new BigInteger("-100"));
		assertEquals(FR_ERRORS.NEGATIVE_INPUT,
				((IntegerToWordMapper) intToWordMapper).getMessage());
	}

	@Test(expected = Exception.class)
	public void testNumberFormatMessage() throws Exception {

		((IntegerToWordMapper) intToWordMapper).validateAndFormat(new BigInteger("1.234"));
		assertEquals(FR_ERRORS.NUMBERFORMAT,
				((IntegerToWordMapper) intToWordMapper).getMessage());
	}

	@Test(expected = Exception.class)
	public void testNullInputMessage() throws Exception {

		((IntegerToWordMapper) intToWordMapper).validateAndFormat(null);
		assertEquals(FR_ERRORS.NULL_INPUT,
				((IntegerToWordMapper) intToWordMapper).getMessage());
	}

	@Test
	public void testZero() throws Exception {

		assertEquals("Zéro", intToWordMapper.getWord(BigInteger.ZERO));
	}

	@Test
	public void testDecimals() throws Exception {

		assertEquals("Un", intToWordMapper.getWord(BigInteger.ONE));
		assertEquals("Dix", intToWordMapper.getWord(BigInteger.TEN));

	}

	@Test
	public void testTeens() throws Exception {
		assertEquals("Onze", intToWordMapper.getWord(new BigInteger("11")));
		assertEquals("Quatorze", intToWordMapper.getWord(new BigInteger("14")));
	}

	@Test
	public void testGetWords() throws Exception {

		assertEquals("Onze million un cent et onze mille un cent et onze",
				intToWordMapper.getWord(new BigInteger("11111111")));

		/*
		 * assertEquals("One thousand and nine",
		 * numberToWordMapper.getWord(1009));
		 * assertEquals("One thousand and ninety nine",
		 * numberToWordMapper.getWord(1099));
		 * 
		 * assertEquals("One thousand and one",
		 * numberToWordMapper.getWord(1001));
		 * assertEquals("One thousand and eleven",
		 * numberToWordMapper.getWord(1011)); assertEquals("Ten thousand",
		 * numberToWordMapper.getWord(10000));
		 * assertEquals("Ten thousand and one",
		 * numberToWordMapper.getWord(10001));
		 * assertEquals("Ten thousand one hundred",
		 * numberToWordMapper.getWord(10100));
		 * 
		 * assertEquals("One hundred thousand",
		 * numberToWordMapper.getWord(100000));
		 * 
		 * assertEquals("One million", numberToWordMapper.getWord(1000000));
		 * 
		 * assertEquals("One million one hundred thousand",
		 * numberToWordMapper.getWord(1100000));
		 */
	}

}
