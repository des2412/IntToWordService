package org.desz.numbertoword.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.logging.Logger;

import org.desz.language.EnumLanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.INumberToWordMapper;
import org.desz.numbertoword.mapper.IntToWord;
import org.desz.numbertoword.service.validator.UkIntValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ IntToWordEnumFactory.class, IntToWord.class,
		EnumLanguageSupport.class, UkIntValidator.class, HashMap.class })
public class IntToWordEnumFactoryTest {

	Object[] ARGS = new Object[2];

	private final static Logger LOGGER = Logger
			.getLogger(IntToWordEnumFactoryTest.class.getName());

	// ILanguageSupport langSupport = new EnumLanguageSupport(PROV_LANG.UK);

	@Before
	public void init() {

		EnumLanguageSupport sup = PowerMock
				.createMock(EnumLanguageSupport.class);
		UkIntValidator val = PowerMock.createMock(UkIntValidator.class);

		ARGS[0] = sup;
		ARGS[1] = val;
	}

	@After
	public void clean() throws FactoryMapperRemovalException {

		IntToWordEnumFactory.unCacheFactory(PROV_LANG.UK);

	}

	/**
	 * Test that the caching mechanism returns the same IntToWord
	 */
	@Test
	public void testFactoryCache() {

		IntToWordEnumFactory fac = IntToWordEnumFactory.UK_FAC;

		INumberToWordMapper<BigInteger> mapper = null;
		try {
			mapper = fac.getIntegerToWordMapper(); // adds mapper to cache
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testFactoryCache exception." + e.getCause());
		}

		// Reset cache to empty state
		Whitebox.setInternalState(IntToWordEnumFactory.class, "mappingsCache",
				new HashMap<PROV_LANG, IntToWordEnumFactory>());

		// Call getIntegerToWord again -> mapper2

		INumberToWordMapper<BigInteger> mapper2 = null;
		try {
			mapper2 = fac.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testFactoryCache exception." + e.getCause());
		}

		// assert different instances [mapper, mapper2]
		assertNotSame(mapper, mapper2);

		INumberToWordMapper<BigInteger> mapper3 = null;
		try {
			mapper3 = fac.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testFactoryCache exception." + e.getCause());
		}

		// assert same [mapper2, mapper3]
		assertSame(mapper2, mapper3);

	}

	/**
	 * Interaction test
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetIntegerToWordMapper() throws Exception {

		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		IntToWord mapper = PowerMock.createMock(IntToWord.class);

		tested.getIntegerToWordMapper();
		PowerMock.expectNew(IntToWord.class, ARGS).andReturn(mapper);

		replay(IntToWordEnumFactory.class);
		replay(mapper);

		verify(IntToWordEnumFactory.class);
		verify(mapper);

	}

	@Test
	public void testPrivateConstructorInvoked() throws Exception {

		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		INumberToWordMapper<BigInteger> mapper = null;

		PowerMock.mockStaticPartial(IntToWordEnumFactory.class,
				"getIntegerToWordMapper");

		PowerMock.expectNew(IntToWord.class, ARGS);

		mapper = tested.getIntegerToWordMapper();
		assertNotNull(mapper);

		replay(IntToWordEnumFactory.class);
		verify(IntToWordEnumFactory.class);

	}

	/**
	 * test Singleton semantics
	 * 
	 * @throws NumberToWordFactoryException
	 */
	@Test
	public void testSameIntegerToWordEnumFactory()
			throws NumberToWordFactoryException {
		INumberToWordFactory<BigInteger> ukFac = IntToWordEnumFactory.UK_FAC;
		INumberToWordFactory<BigInteger> ukFac2 = IntToWordEnumFactory.UK_FAC;

		INumberToWordFactory<BigInteger> frFac = IntToWordEnumFactory.FR_FAC;

		PowerMock.mockStaticPartial(IntToWordEnumFactory.class,
				"getIntegerToWordMapper");

		replay(IntToWordEnumFactory.class);

		try {
			ukFac.getIntegerToWordMapper();
			ukFac2.getIntegerToWordMapper();
			frFac.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testSameNumberToWordEnumFactory getIntegerToWordMapper invocation failure");
			throw (e);
		}

		verify(IntToWordEnumFactory.class);

		// Assert same
		assertSame(ukFac, ukFac2);
		// assert not same
		assertNotSame(ukFac, frFac);

	}

}
