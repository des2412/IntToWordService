package org.desz.numbertoword.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.logging.Logger;

import org.desz.numbertoword.INumberToWordMapper;
import org.desz.numbertoword.IntegerToWordMapper;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NumberToWordFactoryTest {

	INumberToWordMapper factory = null;

	@Before
	public void init() throws Exception {
		factory = NumberToWordFactory.UK_MAPPER.getNumberToWordMapper();
	}

	@After
	public void clean() throws Exception {
		NumberToWordFactory.removeNumberToWordMapper(PROVISIONED_LANGUAGE.UK);
	}

	@Test
	public void testClassEquality() {
		INumberToWordMapper mapper = null;
		try {
			mapper = NumberToWordFactory.UK_MAPPER
					.getNumberToWordMapper();
		} catch (NumberToWordFactoryException e) {
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
		assertEquals("testUkClassEquality",IntegerToWordMapper.class, mapper.getClass());
	}

	@Test(expected=FactoryMapperRemovalException.class)
	public void testRemoveNumberToWordMapper() throws Exception {
		NumberToWordFactory.UK_MAPPER
				.getNumberToWordMapper();

		NumberToWordFactory
						.removeNumberToWordMapper(PROVISIONED_LANGUAGE.FR);

	}

}
