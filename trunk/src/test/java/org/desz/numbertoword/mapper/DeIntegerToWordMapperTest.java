/**
 * 
 */
package org.desz.numbertoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LN;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.factory.IntegerToWordEnumFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author des
 * 
 */
public class DeIntegerToWordMapperTest extends IntegerToWordMapperTest {

	@Before
	public void init() throws Exception {
		intToWordMapper = (IntegerToWordMapper) IntegerToWordEnumFactory.DE_FAC
				.getIntegerToWordMapper();
		assertNotNull(intToWordMapper);
	}

	@After
	public void clean() throws Exception {
		IntegerToWordEnumFactory
				.removeNumberToWordEnumFactory(PROVISIONED_LN.DE);
	}

	@Test
	public void test() throws IntegerToWordException {
		assertEquals("einundzwanzig", intToWordMapper.getWord(new BigInteger("21")));
	}

}
