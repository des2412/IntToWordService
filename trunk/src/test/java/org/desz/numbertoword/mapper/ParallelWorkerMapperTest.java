package org.desz.numbertoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.IntRangeUpperExc;
import org.desz.numbertoword.exceptions.IntRangeLowerExc;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.desz.numbertoword.factory.ParallelIntToWordFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParallelWorkerMapperTest {

	protected final static Logger LOGGER = Logger
			.getLogger(ParallelWorkerMapperTest.class.getName());

	private INumberToWordMapper<BigInteger> mapper;

	@Before
	public void init() throws IntRangeUpperExc,
			IntRangeLowerExc {

		try {
			mapper = ParallelIntToWordFactory.UK_FAC.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("mapper creation issue");
		}

		// fail("Not yet implemented");
	}

	@After
	public void clean() {
		try {
			IntToWordEnumFactory
					.removeNumberToWordEnumFactory(PROV_LANG.UK);
		} catch (FactoryMapperRemovalException e) {
			LOGGER.severe(e.getMessage());
		}
	}

	@Test
	public void testZero() throws NumberToWordFactoryException,IntToWordExc {
		BigInteger bi = new BigInteger("0");
		mapper = ParallelIntToWordFactory.UK_FAC.getIntegerToWordMapper();
		assertEquals("Zero", mapper.getWord(bi));
	}

	@Test
	public void comparePerformance() throws IntToWordExc
			, NumberToWordFactoryException {

		BigInteger bi = new BigInteger("199999999");

		String s = null;

		assertNotNull(mapper);

		long begTest = System.nanoTime();

		for (int i = 0; i < 10; i++) {
			s = mapper.getWord(bi.add(new BigInteger(String.valueOf(i))));
		}

		long endTest = System.nanoTime();
		double res = (endTest - begTest) * 0.001;

		mapper = IntToWordEnumFactory.UK_FAC.getIntegerToWordMapper();

		long begTestN = System.nanoTime();

		for (int i = 0; i < 10; i++) {
			s = mapper.getWord(bi.add(new BigInteger(String.valueOf(i))));
		}

		long endTestN = System.nanoTime();

		double resN = (endTestN - begTestN) * 0.001;

		LOGGER.info("Ratio MultiThreaded : Non-MultiThreaded result: " + res
				/ resN);
	}

	@Test
	public void testMultiThread() throws IntToWordExc {

		String s = mapper.getWord(new BigInteger("1000000"));
		LOGGER.info(s);
		assertEquals("One million", s);

		s = mapper.getWord(new BigInteger("1000100"));
		LOGGER.info(s);
		assertEquals("One million one hundred", s);

		s = mapper.getWord(new BigInteger("199999"));
		LOGGER.info(s);
		assertEquals(
				"One hundred and ninety nine thousand nine hundred and ninety nine",
				s);
	}

}
