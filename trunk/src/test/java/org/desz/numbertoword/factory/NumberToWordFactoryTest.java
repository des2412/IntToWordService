package org.desz.numbertoword.factory;

import static org.junit.Assert.assertEquals;

import org.desz.numbertoword.INumberToWordMapper;
import org.desz.numbertoword.NumberToWordMapper;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.junit.Before;
import org.junit.Test;

public class NumberToWordFactoryTest {

	INumberToWordMapper factory = null;

	@Before
	public void init() throws Exception {
		factory = NumberToWordFactory.UK_MAPPER.getNumberToWordMapper();
	}

	@Test
	public void testUkClassEquality() throws Exception {
		INumberToWordMapper mapper = NumberToWordFactory.UK_MAPPER
				.getNumberToWordMapper();
		assertEquals(NumberToWordMapper.class, mapper.getClass());
	}

	@Test
	public void testRemoveNumberToWordMapper() throws Exception {
		INumberToWordMapper mapper = NumberToWordFactory.UK_MAPPER
				.getNumberToWordMapper();

		assertEquals(PROVISIONED_LANGUAGE.UK.name(),
				NumberToWordFactory
						.removeNumberToWordMapper(PROVISIONED_LANGUAGE.UK));

	}

	/*
	 * @Test public void testUkSingleton() throws Exception {
	 * assertSame(factory,
	 * NumberToWordFactory.UK_MAPPER.getNumberToWordMapper());
	 * Assert.assertNotSame(factory, new NumberToWordMapper()); }
	 */

}
