package org.desz.numbertoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.NL_ERRORS;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LN;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.factory.IntegerToWordEnumFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NlIntegerToWordTest extends IntegerToWordMapperTest {

	@Before
	public void init() throws Exception {
		intToWordMapper = (IntegerToWordMapper) IntegerToWordEnumFactory.NL_FAC
				.getIntegerToWordMapper();
		assertNotNull(intToWordMapper);
	}

	@After
	public void clean() throws Exception {
		IntegerToWordEnumFactory
				.removeNumberToWordEnumFactory(PROVISIONED_LN.NL);
	}

	@Test
	public void test() throws IntegerToWordException {
		assertEquals("Eenentwintig",
				intToWordMapper.getWord(new BigInteger("21")));
	}

	@Test(expected = IntegerToWordException.class)
	public void checkErrorMessage() throws IntegerToWordException {
		assertEquals(NL_ERRORS.NEGATIVE_INPUT,
				intToWordMapper.getWord(new BigInteger("-21")));
	}

}
