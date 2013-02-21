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
import org.desz.language.ILanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.INumberToWordMapper;
import org.desz.numbertoword.mapper.IntToWord;
import org.desz.numbertoword.service.validator.UkIntValidator;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ IntToWordEnumFactory.class, IntToWord.class,
		EnumLanguageSupport.class, UkIntValidator.class })
public class IntToWordEnumFactoryTest {

	EnumLanguageSupport sup;

	UkIntValidator val;

	Object[] ARGS = new Object[2];

	private final static Logger LOGGER = Logger
			.getLogger(IntToWordEnumFactoryTest.class.getName());

	ILanguageSupport langSupport = new EnumLanguageSupport(PROV_LANG.UK);

	// private static final Object ARGS[] = new Object[] { langSupport, new
	// UkIntValidator() };

	@Before
	public void init() {

		sup = PowerMock.createMock(EnumLanguageSupport.class);
		val = PowerMock.createMock(UkIntValidator.class);

		ARGS[0] = sup;
		ARGS[1] = val;
	}

	@After
	public void clean() {
		try {
			IntToWordEnumFactory.removeNumberToWordEnumFactory(PROV_LANG.UK);
		} catch (FactoryMapperRemovalException e) {
			LOGGER.severe("FactoryMapperRemovalException");
		}
	}

	/**
	 * Test that the caching mechanism returns the same IntToWord or not
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

	@Test
	public void mockStatic() throws NumberToWordFactoryException {

		IntToWord mapper = PowerMock.createMock(IntToWord.class);

		PowerMockito.mockStatic(IntToWordEnumFactory.class);

		PowerMockito.when(IntToWordEnumFactory.getMapper(PROV_LANG.UK))
				.thenReturn(mapper);

	}

	/**
	 * Interaction test -
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetIntegerToWordMapper() throws Exception {

		// Create a new instance of test class under test as usually.
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		IntToWord mapper = PowerMock.createMock(IntToWord.class);

		tested.getIntegerToWordMapper();
		PowerMock.expectNew(IntToWord.class, ARGS).andReturn(mapper);

		replay(IntToWordEnumFactory.class);
		replay(mapper);

		// Note how we verify the class, not the instance!
		verify(IntToWordEnumFactory.class);
		verify(mapper);

		assertNotNull(mapper);

	}
	
	
	@Test(expected=NumberToWordFactoryException.class)
	public void testFail() throws Exception{
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		//IntToWord mapper = PowerMock.createMock(IntToWord.class);

		
		ARGS[0] = null;
		tested.getIntegerToWordMapper();
		PowerMock.expectNew(IntToWord.class, ARGS);

		replay(IntToWordEnumFactory.class);
		//replay(mapper);

		// Note how we verify the class, not the instance!
		verify(IntToWordEnumFactory.class);
		//verify(mapper);

	}

	@Test
	public void testPrivateConstructorInvoked() throws Exception {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory<BigInteger> tested = IntToWordEnumFactory.UK_FAC;
		INumberToWordMapper<BigInteger> mapper = null;

		PowerMock.mockStaticPartial(IntToWordEnumFactory.class,
				"getIntegerToWordMapper");

		PowerMock.expectStrictNew(IntToWord.class, ARGS);

		mapper = tested.getIntegerToWordMapper();
		assertNotNull(mapper);

		replay(IntToWordEnumFactory.class);

		// replay(mapper);
		verify(IntToWordEnumFactory.class);

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
			// PowerMock.expectStrictNew(IntToWord.class, ARGS);
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
