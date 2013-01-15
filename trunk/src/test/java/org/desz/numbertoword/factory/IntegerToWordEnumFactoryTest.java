package org.desz.numbertoword.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
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
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IntToWordEnumFactory.class)
public class IntegerToWordEnumFactoryTest {

	private final static Logger LOGGER = Logger
			.getLogger(IntegerToWordEnumFactoryTest.class.getName());

	private static final Object ARGS[] = new Object[] { new EnumLanguageSupport(
			PROV_LANG.UK) };

	@After
	public void clean() {
		try {
			IntToWordEnumFactory
					.removeNumberToWordEnumFactory(PROV_LANG.UK);
		} catch (FactoryMapperRemovalException e) {
			LOGGER.severe("FactoryMapperRemovalException");
		}
	}

	@Test
	public void testTypeOfMapper() {
		INumberToWordFactory<BigInteger> fac = ParallelIntToWordFactory.UK_FAC;

		INumberToWordMapper<BigInteger> mapper = null;
		try {
			mapper = fac.getIntegerToWordMapper(); // add mapper to factoryCache
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testFactoryCache exception." + e.getCause());
		}
		Assert.assertTrue(mapper instanceof INumberToWordMapper);
	}

	@Test
	public void testFactoryCache() {

		IntToWordEnumFactory fac = IntToWordEnumFactory.UK_FAC;

		INumberToWordMapper<BigInteger> mapper = null;
		try {
			mapper = fac.getIntegerToWordMapper(); // add mapper to factoryCache
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testFactoryCache exception." + e.getCause());
		}

		// Reset the factoryCache toempty state
		Whitebox.setInternalState(IntToWordEnumFactory.class,
				"mappingsCache",
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

	@Test
	public void testInstantiateInstance() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		INumberToWordMapper<BigInteger> mapper = null;
		try {
			mapper = tested.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("getIntegerToWordMapper failure" + e.getMessage());
		}

		try {
			// PowerMock.expectStrictNew(IntToWord.class, ARGS);
			PowerMock.expectPrivate(IntToWordEnumFactory.class,
					"instantiateInstance", ARGS).andReturn(mapper);
		} catch (Exception e1) {
			LOGGER.severe("testInstantiateInstance expectPrivate exception");
		}

		replay(IntToWordEnumFactory.class);

		// Note how we verify the class, not the instance!
		verify(IntToWordEnumFactory.class);

		assertNotNull(mapper);

	}

	@Test
	public void testIsCachedTrue() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		INumberToWordMapper<BigInteger> mapper = null;
		try {
			mapper = tested.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("getIntegerToWordMapper failure" + e.getMessage());
		}

		try {
			PowerMock.expectStrictNew(IntToWord.class, ARGS);
			PowerMock.expectPrivate(IntToWordEnumFactory.class,
					"instantiateInstance", ARGS).andReturn(mapper);
		} catch (Exception e1) {
			LOGGER.severe("testInstantiateInstance expectPrivate exception");
		}

		replay(IntToWordEnumFactory.class);

		// Note how we verify the class, not the instance!
		verify(IntToWordEnumFactory.class);

		assertNotNull(mapper);

	}

	// @Test TODO delete
	public void testIsCachedFalse() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		IntToWord mapper = null;

		PowerMock.mockStaticPartial(IntToWordEnumFactory.class, "isCached");
		PowerMock.mockStaticPartial(IntToWordEnumFactory.class,
				"getIntegerToWordMapper");

		try {
			mapper = (IntToWord) tested.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testLookUpPreInitialisedAssertTrueResult invocation failure");
		}
		try {
			PowerMock.expectStrictNew(IntToWord.class, ARGS)
					.andReturn(mapper);
			PowerMock.expectPrivate(tested, "lookUpPreInitialised", ARGS)
					.andReturn(false);
		} catch (Exception e1) {
			LOGGER.severe("testInstantiateInstance expectPrivate exception");
		}

		replay(IntToWordEnumFactory.class);

		assertNotNull(mapper);

		// Note how we verify the class, not the instance!
		verify(IntToWordEnumFactory.class);

		// Assert that lookUpPreInitialised returns expected value

	}

	@Test
	public void testGetIntegerToWord() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		INumberToWordMapper<BigInteger> mapper = null;

		PowerMock.mockStaticPartial(IntToWordEnumFactory.class,
				"getIntegerToWordMapper");

		try {
			PowerMock.expectStrictNew(IntToWord.class, ARGS);
		} catch (Exception e1) {
			LOGGER.severe("testGetIntegerToWord expectStrictNew exception");
		}

		replay(IntToWordEnumFactory.class);

		try {
			mapper = tested.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testGetNumberToWord invocation failure");
		}

		// Note how we verify the class, not the instance!
		verify(IntToWordEnumFactory.class);

		// Assert that lookUpPreInitialised returns expected value
		assertNotNull(mapper);
	}

	@Test
	public void testremoveNumberToWordEnumFactory() {
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;

		// You need to mock each method called on tested or null pointer thrown
		PowerMock.mockStaticPartial(IntToWordEnumFactory.class,
				"removeNumberToWordEnumFactory");

		PowerMock.mockStaticPartial(IntToWordEnumFactory.class,
				"getIntegerToWordMapper");

		try {
			PowerMock.expectStrictNew(IntToWord.class, ARGS);
		} catch (Exception e1) {
			LOGGER.severe("testremoveNumberToWordEnumFactory expectStrictNew exception");
		}

		replay(IntToWordEnumFactory.class);

		boolean cleared = false;
		try {
			try {
				tested.getIntegerToWordMapper();
			} catch (NumberToWordFactoryException e) {
				LOGGER.severe("testremoveNumberToWordEnumFactory getIntegerToWordMapper invocation failure");
			}
			cleared = IntToWordEnumFactory
					.removeNumberToWordEnumFactory(PROV_LANG.UK);
		} catch (FactoryMapperRemovalException e) {
			LOGGER.severe("testremoveNumberToWordEnumFactory invocation failure");
		}

		// Note how we verify the class, not the instance!
		verify(IntToWordEnumFactory.class);

		// Assert that lookUpPreInitialised returns expected value
		assertTrue(cleared);

	}

	/**
	 * test Singleton semantics
	 */
	@Test
	public void testSameIntegerToWordEnumFactory() {
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		INumberToWordFactory<BigInteger> tested2 = IntToWordEnumFactory.UK_FAC;

		INumberToWordFactory<BigInteger> tested3 = IntToWordEnumFactory.FR_FAC;

		PowerMock.mockStaticPartial(IntToWordEnumFactory.class,
				"getIntegerToWordMapper");

		replay(IntToWordEnumFactory.class);

		try {
			tested.getIntegerToWordMapper();
			tested2.getIntegerToWordMapper();
			tested3.getIntegerToWordMapper();
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testSameNumberToWordEnumFactory getIntegerToWordMapper invocation failure");
		}

		// Note how we verify the class, not the instance!
		verify(IntToWordEnumFactory.class);

		// Assert same
		assertSame(tested, tested2);
		// assert not same
		assertNotSame(tested, tested3);

	}

}
