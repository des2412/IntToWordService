package org.desz.numbertoword;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.desz.numbertoword.NumberToWordMapper;
import org.desz.numbertoword.enums.EnumHolder.ERRORS;
import org.desz.numbertoword.factory.NumberToWordFactory;
import org.junit.Before;
import org.junit.Test;

public class UkNumberToWordMapperTest {
	final private static String[] decimals = { "One", "Two", "Three", "Four",
			"Five", "Six", "Seven", "Eight", "Nine" };
	static List<String> l = Arrays.asList(decimals);

	NumberToWordMapper numberToWordMapper = null;

	@Before
	public void init() throws Exception {
		numberToWordMapper = (NumberToWordMapper) NumberToWordFactory.UK_MAPPER
				.getNumberToWordMapper();
		
		NumberToWordMapper.setLoggingLevel(Level.ALL);

	}

	@Test
	public void testNotNull() {
		assertNotNull(numberToWordMapper);
	}

	@Test
	public void testIsSingleton() throws Exception {
		NumberToWordMapper.setLoggingLevel(Level.INFO);
		assertSame(numberToWordMapper,
				NumberToWordFactory.UK_MAPPER.getNumberToWordMapper());
	}

	@Test(expected = Exception.class)
	public void testNegativeInputMessage() throws Exception {
		numberToWordMapper.validateAndFormat(-100);
		assertEquals(ERRORS.NEGATIVE_INPUT, numberToWordMapper.getMessage());
	}

	@Test(expected = Exception.class)
	public void testNumberFormatMessage() throws Exception {
		numberToWordMapper.validateAndFormat(1.234);
		assertEquals(ERRORS.NUMBERFORMAT, numberToWordMapper.getMessage());
	}

	@Test(expected = Exception.class)
	public void testNullInputMessage() throws Exception {
		numberToWordMapper.validateAndFormat(null);
		assertEquals(ERRORS.NULL_INPUT, numberToWordMapper.getMessage());
	}

	@Test
	public void testZero() throws Exception {
		assertEquals("Zero", numberToWordMapper.getWord(0));
	}

	@Test
	public void testDecimals() throws Exception {

		assertEquals("One", numberToWordMapper.getWord(1));
		assertEquals("Ten", numberToWordMapper.getWord(10));

	}

	@Test
	public void testTeens() throws Exception {
		assertEquals("Eleven", numberToWordMapper.getWord(11));
		assertEquals("Fourteen", numberToWordMapper.getWord(14));
	}

	@Test
	public void testTens() throws Exception {
		assertEquals("Forty", numberToWordMapper.getWord(40));
		int num = 41;
		for (String s : l) {

			assertEquals("Forty" + " " + s,
					numberToWordMapper.getWord(Integer.valueOf(num)));
			num++;

		}

	}

	@Test
	public void testGetWords() throws Exception {

		assertEquals(
				"Eleven million one hundred and eleven thousand one hundred and eleven",
				numberToWordMapper.getWord(11111111));

		assertEquals("One thousand and nine", numberToWordMapper.getWord(1009));
		assertEquals("One thousand and ninety nine",
				numberToWordMapper.getWord(1099));

		assertEquals("One thousand and one", numberToWordMapper.getWord(1001));
		assertEquals("One thousand and eleven",
				numberToWordMapper.getWord(1011));
		assertEquals("Ten thousand", numberToWordMapper.getWord(10000));
		assertEquals("Ten thousand and one", numberToWordMapper.getWord(10001));
		assertEquals("Ten thousand one hundred",
				numberToWordMapper.getWord(10100));

		assertEquals("One hundred thousand", numberToWordMapper.getWord(100000));

		assertEquals("One million", numberToWordMapper.getWord(1000000));

		assertEquals("One million one hundred thousand",
				numberToWordMapper.getWord(1100000));

	}

	// @Test
	public void testError() throws Exception {
		assertEquals(
				"Nine hundred and ninety nine million nine hundred and nine thousand",
				numberToWordMapper.getWord(999909000));
	}

	@Test
	public void testLatest() throws Exception {
		assertEquals(
				"Nine hundred and ninety nine million nine hundred and nine thousand",
				numberToWordMapper.getWord(999909000));

		assertEquals("One hundred million one hundred thousand",
				numberToWordMapper.getWord(100100000));
		assertEquals("One hundred million and seventy seven thousand",
				numberToWordMapper.getWord(100077000));

		assertEquals("One million and seventy seven thousand",
				numberToWordMapper.getWord(1077000));

		assertEquals("Nine hundred million nine hundred thousand",
				numberToWordMapper.getWord(900900000));
		assertEquals("Nine hundred million and one",
				numberToWordMapper.getWord(900000001));

		assertEquals(
				"Nine hundred and ninety nine million nine hundred and ninety nine thousand nine hundred and ninety nine",
				numberToWordMapper.getWord(999999999));
	}

}
