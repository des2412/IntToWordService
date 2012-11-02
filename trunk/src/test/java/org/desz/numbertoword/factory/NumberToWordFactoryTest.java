package org.desz.numbertoword.factory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import java.util.logging.Logger;

import org.desz.numbertoword.IFNumberToWordMapper;
import org.desz.numbertoword.IntegerToWordMapper;
import org.desz.numbertoword.LanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest(IntegerToWordEnumFactory.class)
public class NumberToWordFactoryTest {

	private final static Logger LOGGER = Logger
			.getLogger(NumberToWordFactoryTest.class.getName());

	static final Object args[] = new Object[] { new LanguageSupport(
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
	public void testLookUpPreInitialisedAssertFalseResult() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;

		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"lookUpPreInitialised");

		replay(IntegerToWordEnumFactory.class);

		boolean isListed = ((IntegerToWordEnumFactory) tested)
				.lookUpPreInitialised(PROVISIONED_LANGUAGE.FR);

		// Note how we verify the class, not the instance!
		verify(IntegerToWordEnumFactory.class);

		// Assert
		assertFalse(isListed);

	}

	@Test
	public void testLookUpPreInitialisedAssertTrueResult() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;
		IFNumberToWordMapper mapper = null;

		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"lookUpPreInitialised");

		replay(IntegerToWordEnumFactory.class);

		try {
			mapper = tested.getIntegerToWordMapper(PROVISIONED_LANGUAGE.UK);
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe("testLookUpPreInitialisedAssertTrueResult invocation failure");
		}

		boolean isListed = ((IntegerToWordEnumFactory) tested)
				.lookUpPreInitialised(PROVISIONED_LANGUAGE.UK);

		// Note how we verify the class, not the instance!
		verify(IntegerToWordEnumFactory.class);

		// Assert that lookUpPreInitialised returns expected value
		assertTrue(isListed);
		assertNotNull(mapper);

	}

	@Test
	public void testGetIntegerToWord() {

		// We create a new instance of test class under test as usually.
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;
		IFNumberToWordMapper mapper = null;

		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"getIntegerToWordMapper");

		try {
			PowerMock.expectStrictNew(IntegerToWordMapper.class, args);
		} catch (Exception e1) {
			LOGGER.severe("testGetIntegerToWord expectStrictNew exception");
		}

		replay(IntegerToWordEnumFactory.class);

		try {
			mapper = tested.getIntegerToWordMapper(PROVISIONED_LANGUAGE.UK);
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
			PowerMock.expectStrictNew(IntegerToWordMapper.class, args);
		} catch (Exception e1) {
			LOGGER.severe("testremoveNumberToWordEnumFactory expectStrictNew exception");
		}

		replay(IntegerToWordEnumFactory.class);

		boolean cleared = false;
		try {
			try {
				tested.getIntegerToWordMapper(PROVISIONED_LANGUAGE.UK);
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
	public void testSameNumberToWordEnumFactory() {
		INumberToWordFactory tested = IntegerToWordEnumFactory.UK_MAPPER;
		INumberToWordFactory tested2 = IntegerToWordEnumFactory.UK_MAPPER;

		INumberToWordFactory tested3 = IntegerToWordEnumFactory.FR_MAPPER;
		PowerMock.mockStaticPartial(IntegerToWordEnumFactory.class,
				"getIntegerToWordMapper");

		replay(IntegerToWordEnumFactory.class);

		try {
			tested.getIntegerToWordMapper(PROVISIONED_LANGUAGE.UK);
			tested2.getIntegerToWordMapper(PROVISIONED_LANGUAGE.UK);
			tested3.getIntegerToWordMapper(PROVISIONED_LANGUAGE.UK);
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
