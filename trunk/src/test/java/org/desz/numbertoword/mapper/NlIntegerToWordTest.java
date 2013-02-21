package org.desz.numbertoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.NL_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NlIntegerToWordTest extends IntegerToWordMapperTest {

	@Before
	public void init() throws Exception {
		intToWordMapper = IntToWordEnumFactory.NL_FAC.getIntegerToWordMapper();
		assertNotNull(intToWordMapper);
	}

	@After
	public void clean() throws Exception {
		IntToWordEnumFactory.unCacheFactory(PROV_LANG.NL);
	}

	@Test
	public void testTwentyOne() throws IntToWordExc {
		assertEquals("Eenentwintig",
				intToWordMapper.getWord(new BigInteger("21")));
	}

	@Test
	public void testOneHundredNinety() throws IntToWordExc {
		assertEquals("Een honderd en negentig",
				intToWordMapper.getWord(new BigInteger("190")));
	}

	// FIXME @Test(expected = IntRangeUpperExc.class)
	public void checkErrorMessage() throws IntToWordExc {

		String s = null;
		try {
			assertEquals(NL_ERRORS.NEGATIVE_INPUT,
					intToWordMapper.getWord(new BigInteger("-21")));
		} catch (IntToWordExc e) {
			s = e.getMessage();
		}

		assertEquals(NL_ERRORS.NUMBERFORMAT.getError(), s);
	}

}
