package org.desz.numbertoword.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.INumberToWordMapper;
import org.desz.numbertoword.mapper.IntToWord;
import org.desz.numbertoword.mapper.ParallelIntToWord;

public enum ParallelIntToWordFactory implements
		INumberToWordFactory<BigInteger> {

	UK_FAC();

	private final static Logger LOGGER = Logger
			.getLogger(ParallelIntToWordFactory.class.getName());

	@Override
	public INumberToWordMapper<BigInteger> getIntegerToWordMapper()
			throws NumberToWordFactoryException {

		INumberToWordMapper<BigInteger> integerToWordMapper = null;

		switch (this) {
		case UK_FAC:

			IntToWord mapper = (IntToWord) IntToWordEnumFactory.UK_FAC
					.getIntegerToWordMapper();
			integerToWordMapper = newMtIntegerToWordMapper(mapper);

			break;

		default:
			break;

		}
		return integerToWordMapper;
	}

	/**
	 * 
	 * @param mapper
	 * @return
	 * @throws NumberToWordFactoryException
	 */
	private INumberToWordMapper<BigInteger> newMtIntegerToWordMapper(
			INumberToWordMapper<BigInteger> mapper)
			throws NumberToWordFactoryException {
		// access private Constructor using reflection
		Constructor<?>[] constructors = ParallelIntToWord.class
				.getDeclaredConstructors();

		if (constructors.length == 1)
			constructors[0].setAccessible(true);
		else
			throw new NumberToWordFactoryException(
					"unexpected number of constructors");

		try {
			mapper = (INumberToWordMapper<BigInteger>) constructors[0]
					.newInstance(new Object[] { mapper });
		} catch (InstantiationException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (IllegalAccessException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (IllegalArgumentException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (InvocationTargetException e1) {
			LOGGER.severe(e1.getMessage());
		}
		if (mapper == null) {
			throw new NumberToWordFactoryException(
					"Did not initialise MultiThreadNumberToWordMapper");
		}

		return mapper;
	}

}
