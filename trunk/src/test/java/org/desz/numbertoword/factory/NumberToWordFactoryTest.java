package org.desz.numbertoword.factory;

import static org.junit.Assert.*;

import org.desz.numbertoword.INumberToWordMapper;
import org.desz.numbertoword.UkNumberToWordMapper;
import org.junit.Before;
import org.junit.Test;

public class NumberToWordFactoryTest {

	INumberToWordMapper factory = null;

	@Before
	public void init() throws Exception {
		factory = NumberToWordFactory.UK_SINGLETON.getNumberToWordMapper();
	}

	@Test
	public void testUk() throws Exception {
		INumberToWordMapper mapper = NumberToWordFactory.UK_SINGLETON
				.getNumberToWordMapper();
		assertEquals(UkNumberToWordMapper.class, mapper.getClass());
	}

	@Test
	public void testSingleton() throws Exception {
		assertSame(factory,
				NumberToWordFactory.UK_SINGLETON.getNumberToWordMapper());
	}

}
