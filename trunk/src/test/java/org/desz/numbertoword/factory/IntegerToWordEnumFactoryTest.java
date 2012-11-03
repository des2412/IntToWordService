package org.desz.numbertoword.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import java.util.HashMap;
import java.util.logging.Logger;

import org.desz.language.LanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;
import org.desz.numbertoword.mapper.IntegerToWordMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IntegerToWordEnumFactory.class)
public class IntegerToWordEnumFactoryTest {

	private final static Logger LOGGER = Logger
			.getLogger(IntegerToWordEnumFactoryTest.class.getName());

	private static final Object ARGS[] = new Object[] { new LanguageSupport(
			PROVISIONED_LANGUAGE.UK) };

	@After
	public void clean() {
		try {
			IntegerToWordEnumFactory
					.removeNumberToWordEnumFactory(PROVISIONED_LANGUAGE.UK);
		} catch (FactoryMapperRemovalException e) {
			LOGGER.severe("FactoryMapperRemovalException");
		}
	}

	@Test
	public void testFactoryCache() {

		IntegerToWordEnumFactory fac = IntegerToWordEnumFactory.UK_MAPPER;

		IFNumberToWordMapper mapper = null;
		try {
			mapper = fac.getIntegerToWordMapper(); // add mapper to cache
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testFactoryCache exception." + e.getCause());
		}

		// Reset the cache toempty state
		Whitebox.setInternalState(IntegerToWordEnumFactory.class, "cache",
				new HashMap<PROVISIONED_LANGUAGE, IntegerToWordEnumFactory>());

		// Call getIntegerToWord again -> mapper2

		IFNumberToWordMapper mapper2 = null;
		try {
			mapper2 = fac.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testFactoryCache exception." + e.getCause());
		}

		// assert different instances [mapper, mapper2]
		assertNotSame(mapper, mapper2);

		IFNumberToWordMapper mapper3 = null;
		try {
			mapper3 = fac.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testFactoryCache exception." + e.getCause());
		}

		// assert same [mapper2, mapper3]
		assertSame(mapper2, mapper3);

	}

	@Test
	public void testInstantiateInstance() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;
		IFNumberToWordMapper mapper = null;
		try {
			mapper = tested.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("getIntegerToWordMapper failure" + e.getMessage());
		}

		try {
			// PowerMock.expectStrictNew(IntegerToWordMapper.class, ARGS);
			PowerMock.expectPrivate(IntegerToWordEnumFactory.class,
					"instantiateInstance", ARGS).andReturn(mapper);
		} catch (Exception e1) {
			LOGGER.severe("testInstantiateInstance expectPrivate exception");
		}

		replay(IntegerToWordEnumFactory.class);

		// Note how we verify the class, not the instance!
		verify(IntegerToWordEnumFactory.class);

		assertNotNull(mapper);

	}

	@Test
	public void testIsCachedTrue() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;
		IFNumberToWordMapper mapper = null;
		try {
			mapper = tested.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("getIntegerToWordMapper failure" + e.getMessage());
		}

		try {
			PowerMock.expectStrictNew(IntegerToWordMapper.class, ARGS);
			PowerMock.expectPrivate(IntegerToWordEnumFactory.class,
					"instantiateInstance", ARGS).andReturn(mapper);
		} catch (Exception e1) {
			LOGGER.severe("testInstantiateInstance expectPrivate exception");
		}

		replay(IntegerToWordEnumFactory.class);

		// Note how we verify the class, not the instance!
		verify(IntegerToWordEnumFactory.class);

		assertNotNull(mapper);

	}

	@Test
	public void testIsCachedFalse() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;
		IntegerToWordMapper mapper = null;

		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class, "isCached");
		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"getIntegerToWordMapper");

		try {
			mapper = (IntegerToWordMapper) tested.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testLookUpPreInitialisedAssertTrueResult invocation failure");
		}
		try {
			PowerMock.expectStrictNew(IntegerToWordMapper.class, ARGS)
					.andReturn(mapper);
			PowerMock.expectPrivate(tested, "lookUpPreInitialised", ARGS)
					.andReturn(false);
		} catch (Exception e1) {
			LOGGER.severe("testInstantiateInstance expectPrivate exception");
		}

		replay(IntegerToWordEnumFactory.class);

		assertNotNull(mapper);

		// Note how we verify the class, not the instance!
		verify(IntegerToWordEnumFactory.class);

		// Assert that lookUpPreInitialised returns expected value

	}

	@Test
	public void testGetIntegerToWord() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;
		IFNumberToWordMapper mapper = null;

		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"getIntegerToWordMapper");

		try {
			PowerMock.expectStrictNew(IntegerToWordMapper.class, ARGS);
		} catch (Exception e1) {
			LOGGER.severe("testGetIntegerToWord expectStrictNew exception");
		}

		replay(IntegerToWordEnumFactory.class);

		try {
			mapper = tested.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testGetNumberToWord invocation failure");
		}

		// Note how we verify the class, not the instance!
		verify(IntegerToWordEnumFactory.class);

		// Assert that lookUpPreInitialised returns expected value
		assertNotNull(mapper);
	}

	@Test
	public void testremoveNumberToWordEnumFactory() {
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;

		// You need to mock each method called on tested or null pointer thrown
		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"removeNumberToWordEnumFactory");

		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"getIntegerToWordMapper");

		try {
			PowerMock.expectStrictNew(IntegerToWordMapper.class, ARGS);
		} catch (Exception e1) {
			LOGGER.severe("testremoveNumberToWordEnumFactory expectStrictNew exception");
		}

		replay(IntegerToWordEnumFactory.class);

		boolean cleared = false;
		try {
			try {
				tested.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe("testremoveNumberToWordEnumFactory getIntegerToWordMapper invocation failure");
			}
			cleared = IntegerToWordEnumFactory
					.removeNumberToWordEnumFactory(PROVISIONED_LANGUAGE.UK);
		} catch (FactoryMapperRemovalException e) {
			LOGGER.severe("testremoveNumberToWordEnumFactory invocation failure");
		}

		// Note how we verify the class, not the instance!
		verify(IntegerToWordEnumFactory.class);

		// Assert that lookUpPreInitialised returns expected value
		assertTrue(cleared);

	}

	/**
	 * test Singleton semantics
	 */
	@Test
	public void testSameIntegerToWordEnumFactory() {
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;
		INumberToWordFactory tested2 = IntegerToWordEnumFactory.UK_MAPPER;

		INumberToWordFactory tested3 = IntegerToWordEnumFactory.FR_MAPPER;

		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"getIntegerToWordMapper");

		replay(IntegerToWordEnumFactory.class);

		try {
			tested.getIntegerToWordMapper();
			tested2.getIntegerToWordMapper();
			tested3.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testSameNumberToWordEnumFactory getIntegerToWordMapper invocation failure");
		}

		// Note how we verify the class, not the instance!
		verify(IntegerToWordEnumFactory.class);

		// Assert same
		assertSame(tested, tested2);
		// assert not same
		assertNotSame(tested, tested3);

	}

}
