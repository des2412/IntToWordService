/**
 * 
 */
package org.desz.numbertoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
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
		intToWordMapper = IntToWordEnumFactory.DE_FAC
				.getIntegerToWordMapper();
		assertNotNull(intToWordMapper);
	}

	@After
	public void clean() throws Exception {
		IntToWordEnumFactory.unCacheFactory(PROV_LANG.DE);
	}

	@Test
	public void test() throws IntToWordExc{
		assertEquals("Einundzwanzig",
				intToWordMapper.getWord(new BigInteger("21")));
	}

}
